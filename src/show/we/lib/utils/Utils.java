package show.we.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import show.we.sdk.request.Request;
import show.we.sdk.util.ConstantUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.BaseApplication;
import show.we.lib.cloudapi.ErrorCode;
import show.we.lib.cloudapi.PublicAPI;
import show.we.lib.config.*;
import show.we.lib.model.*;
import show.we.lib.widget.dialog.StandardDialog;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 工具类
 *
 * @author ll
 * @version 1.0.0
 */
public class Utils {

    private static final long MIN_ENTER_ROOM_INTERVAL = 500; //防止快速点击打开多个直播间
    private static long mLastEnterRoomTime;

    /**
     * 程序是否在前台运行
     *
     * @return true表示前台
     */
    public static boolean isForeground() {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) BaseApplication.getApplication()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = BaseApplication.getApplication().getPackageName();

        List<android.app.ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }

        for (android.app.ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (StringUtils.equal(appProcess.processName, packageName)) {
                return appProcess.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }

        return false;
    }

    /**
     * 查看是否被冻结
     *
     * @param context context
     * @param code    错误码
     * @return 状态
     */
    public static boolean checkErrorCode(Context context, int code) {
        if (code == ErrorCode.FREEZE_USER) {
            PromptUtils.showToast(R.string.exit_user, Toast.LENGTH_LONG);
            LoginUtils.logout();
            return true;
        } else if (code == ErrorCode.FREEZE_DEVICE) {
            PromptUtils.showToast(context.getString(R.string.exit_device, Config.EXIT_COUNT_DOWN / ConstantUtils.THOUSAND)
                    , Toast.LENGTH_LONG);
            BaseApplication.exitInMillSeconds(Config.EXIT_COUNT_DOWN);
            return true;
        } 
        else if (code == ErrorCode.ACCESS_TOKEN_EXPIRED) {
            StorageUtils.getSharedPreferences().edit().remove(SharedPreferenceKey.ACCESS_TOKEN_KEY).commit();
            CacheUtils.getObjectCache().delete(CacheObjectKey.ACCESS_TOKEN);
            CacheUtils.getObjectCache().delete(CacheObjectKey.USER_INFO_KEY);
            PromptUtils.showToast(R.string.code_error_token_invalid, Toast.LENGTH_LONG);
            return true;
        }
        return false;
    }

    /**
     * html to String
     *
     * @param str 原字符串
     * @return 转换后的字符串
     */
    public static String html2String(String str) {
        return StringUtils.isEmpty(str) ? str : Html.fromHtml(str).toString();
    }

    /**
     * 设置ImageView的图片资源，捕获内存溢出Error，防止应用崩溃
     *
     * @param imageView imageView
     * @param resId     图片资源
     */
    public static void setImageResourceSafely(ImageView imageView, int resId) {
        try {
            imageView.setImageResource(resId);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断Emptyview是否应该显示，有HeaderView和FooterView的时候依然显示EmptyView
     *
     * @param listView listView
     */
    public static void updateEmptyStatus(ListView listView) {
        if (listView != null) {
            View emptyView = listView.getEmptyView();
            if (emptyView != null) {
                boolean isEmpty = listView.getAdapter() == null
                        || listView.getAdapter().getCount() - listView.getFooterViewsCount() - listView.getHeaderViewsCount() <= 0;
                emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
                listView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
            }
        }
    }

    /**
     * 更新最近观看主播列表开播状态
     *
     * @param roomListResult 最新的房间列表信息
     */
    public static void updateRecentlyViewStarList(RoomListResult roomListResult) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY) && roomListResult != null) {
            for (RoomListResult.Data data : roomListResult.getDataList()) {
                for (RecentlyViewStarListResult.User user : ((RecentlyViewStarListResult) CacheUtils.getObjectCache()
                        .get(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY)).getUsers()) {
                    if (user.getStarId() == data.getId()) {
                        user.setIsLive(data.getIsLive());
                        user.setVisitorCount(data.getVisitorCount());
                    }
                }
            }
        }
    }

    /**
     * 自动选择进入直播间或者主播空间
     *
     * @param context     context
     * @param starInfo    starInfo
     * @param liveCls     liveCls
     * @param starZoneCls starZoneCls
     */
    public static void autoEnterLiveOrZone(Context context, StarRoomInfo starInfo, Class<?> liveCls, Class<?> starZoneCls) {
        if (starInfo.getIsLive()) {
            if (System.currentTimeMillis() - mLastEnterRoomTime > MIN_ENTER_ROOM_INTERVAL) {
                enterRoom(context, starInfo, liveCls);
            }
            mLastEnterRoomTime = System.currentTimeMillis();
        } else {
            enterStarZone(context, starInfo, starZoneCls, false);
        }
    }

    /**
     * 自动选择进入直播间或者主播空间
     *
     * @param context     context
     * @param roomData    roomData
     * @param liveCls     liveCls
     * @param starZoneCls starZoneCls
     */
    public static void autoEnterLiveOrZone(Context context, RoomListResult.Data roomData, Class<?> liveCls, Class<?> starZoneCls) {
        autoEnterLiveOrZone(context, new StarRoomInfo(roomData.getIsLive(), roomData.getId(), roomData.getXyStarId()
                , roomData.getPicUrl(), roomData.getCoverUrl(), roomData.getNickName(), 0, 0, "", roomData.getVisitorCount()
                , roomData.getL(), roomData.getFollowers()), liveCls, starZoneCls);
    }

    /**
     * 自动选择从Family进入直播间或者主播空间
     *
     * @param context     context
     * @param roomData    roomData
     * @param liveCls     liveCls
     * @param starZoneCls starZoneCls
     */
    public static void autoEnterLiveOrZone(Context context, FamilyStarData roomData, Class<?> liveCls, Class<?> starZoneCls) {
        autoEnterLiveOrZone(context, new StarRoomInfo(roomData.getStar().isLive(), roomData.getStar().getId(), roomData.getStar().getXyStarId()
                , roomData.getStar().getPicUrl(), roomData.getStar().getCoverUrl(), roomData.getStar().getNickName(), 0, 0, ""
                , roomData.getStar().getVisitorCount(), roomData.getStar().getL(), roomData.getStar().getFollowers()), liveCls, starZoneCls);
    }

    /**
     * 自动选择进入直播间或者主播空间
     *
     * @param context     context
     * @param intent      intent
     * @param liveCls     liveCls
     * @param starZoneCls starZoneCls
     */
    public static void autoEnterLiveOrZone(Context context, Intent intent, Class<?> liveCls, Class<?> starZoneCls) {
        autoEnterLiveOrZone(context, new StarRoomInfo(intent.getBooleanExtra(StarRoomKey.IS_LIVE_KEY, false)
                , intent.getLongExtra(StarRoomKey.ROOM_ID_KEY, 0), intent.getLongExtra(StarRoomKey.STAR_ID_KEY, 0)
                , intent.getStringExtra(StarRoomKey.STAR_PIC_URL_KEY), intent.getStringExtra(StarRoomKey.ROOM_COVER)
                , intent.getStringExtra(StarRoomKey.STAR_NICK_NAME_KEY), intent.getIntExtra(StarRoomKey.STAR_CONSTELLATION_KEY, 0)
                , intent.getIntExtra(StarRoomKey.STAR_SEX_KEY, 0), intent.getStringExtra(StarRoomKey.STAR_LOCATION_KEY)
                , intent.getIntExtra(StarRoomKey.VISITOR_COUNT_KEY, 0), intent.getIntExtra(StarRoomKey.STAR_LEVEL_KEY, 0)
                , intent.getIntExtra(StarRoomKey.STAR_FOLLOWERS_KEY, 0)), liveCls, starZoneCls);
    }

    /**
     * 进入主播空间
     *
     * @param context        context
     * @param starInfo       starInfo
     * @param cls            cls
     * @param isFromLiveRoom isFromLiveRoom
     */
    public static void enterStarZone(Context context, StarRoomInfo starInfo, Class<?> cls, boolean isFromLiveRoom) {
        Intent intent = new Intent(context, cls);
        LiveIntentBuilder builder = new LiveIntentBuilder(intent);
        builder.putStarZoneExtras(starInfo.getIsLive(), starInfo.getRoomId(), starInfo.getStarId(), starInfo.getNickName()
                , starInfo.getStarLevel(), starInfo.getPicUrl(), starInfo.getCoverUrl(), starInfo.getVisiterCount(), starInfo.getConstellation()
                , starInfo.getSex(), starInfo.getLocation(), isFromLiveRoom);
        context.startActivity(intent);
    }

    /**
     * 进入直播间，如果没有直播到主播中心
     *
     * @param context  上下文
     * @param starInfo 主播信息
     * @param activity LiveActivity.class
     */
    public static void enterRoom(Context context, StarRoomInfo starInfo, Class activity) {
        Intent intent = new Intent(context, activity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        LiveIntentBuilder builder = new LiveIntentBuilder(intent);
        builder.putLiveExtras(starInfo.getIsLive(), starInfo.getRoomId(), starInfo.getStarId(), starInfo.getNickName()
                , starInfo.getStarLevel(), starInfo.getPicUrl(), starInfo.getCoverUrl(), starInfo.getVisiterCount(), starInfo.getmFollowers());
        context.startActivity(intent);
    }

    /**
     * 进入直播间，如果没有直播到主播中心
     *
     * @param context   上下文
     * @param intent    intent
     * @param liveClass LiveActivity.class
     */
    public static void enterRoom(Context context, Intent intent, Class liveClass) {
        StarRoomInfo starInfo = new StarRoomInfo(intent.getBooleanExtra(StarRoomKey.IS_LIVE_KEY, false)
                , intent.getLongExtra(StarRoomKey.ROOM_ID_KEY, 0), intent.getLongExtra(StarRoomKey.STAR_ID_KEY, 0)
                , intent.getStringExtra(StarRoomKey.STAR_PIC_URL_KEY), intent.getStringExtra(StarRoomKey.ROOM_COVER)
                , intent.getStringExtra(StarRoomKey.STAR_NICK_NAME_KEY), intent.getIntExtra(StarRoomKey.STAR_CONSTELLATION_KEY, 0)
                , intent.getIntExtra(StarRoomKey.STAR_SEX_KEY, 0), intent.getStringExtra(StarRoomKey.STAR_LOCATION_KEY)
                , intent.getIntExtra(StarRoomKey.VISITOR_COUNT_KEY, 0), intent.getIntExtra(StarRoomKey.STAR_LEVEL_KEY, 0)
                , intent.getIntExtra(StarRoomKey.STAR_FOLLOWERS_KEY, 0));
        enterRoom(context, starInfo, liveClass);
    }

    /**
     * createContainer
     *
     * @param childView childView
     * @return frameLayout
     */
    public static FrameLayout createContainer(View childView) {
        FrameLayout frameLayout = new FrameLayout(childView.getContext());
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        frameLayout.addView(childView);
        return frameLayout;
    }

    /**
     * createContainer
     *
     * @param childView childView
     * @param color     color
     * @return frameLayout
     */
    public static FrameLayout createContainer(View childView, int color) {
        FrameLayout frameLayout = createContainer(childView);
        frameLayout.setBackgroundColor(color);
        return frameLayout;
    }

    /**
     * （分钟）:（秒）  例如 ： 10:09
     *
     * @param millisecond millisecond
     * @return formatTime
     */
    public static String formatTimeByMilliseconds(long millisecond) {
        long seconds = millisecond / ConstantUtils.THOUSAND;
        long minutes = seconds / ConstantUtils.SECONDS_PER_MINUTE;
        seconds = seconds % ConstantUtils.SECONDS_PER_MINUTE;
        StringBuilder sb = new StringBuilder();
        sb.append(minutes).append(":");
        if (seconds < ConstantUtils.TEN) {
            sb.append(0);
        }
        sb.append(seconds);
        return sb.toString();
    }

    private static final int HOUR_PER_DAY = 24;

    /**
     * ××天××小时××分钟   3天10小时20分钟
     * ××小时××分钟   1小时20分钟
     * ××分钟    30分钟
     *
     * @param millisecond millisecond
     * @return formatTime
     */
    public static String formatTime2ByMilliseconds(long millisecond) {
        long seconds = millisecond / ConstantUtils.THOUSAND;
        long minutes = seconds / ConstantUtils.SECONDS_PER_MINUTE;
        long hour = minutes / ConstantUtils.SECONDS_PER_MINUTE;
        long day = hour / HOUR_PER_DAY;
        seconds %= ConstantUtils.SECONDS_PER_MINUTE;
        minutes %= ConstantUtils.SECONDS_PER_MINUTE;
        hour %= HOUR_PER_DAY;
        if (seconds > 0) {
            minutes++;
        }
        if (minutes == ConstantUtils.SECONDS_PER_MINUTE) {
            minutes = 0;
            hour++;
        }
        if (hour == HOUR_PER_DAY && day > 0) {
            hour = 0;
            day++;
        }
        StringBuilder builder = new StringBuilder();
        if (day > 0) {
            builder.append(day).append("天");
        }
        if (hour > 0) {
            builder.append(hour).append("小时");
        }
        if (minutes > 0) {
            builder.append(minutes).append("分钟");
        }
        return builder.toString();
    }

    /**
     * 下一分钟距离现在还有多少毫秒
     *
     * @return 下一分钟距离现在还有多少毫秒
     */
    public static long nextMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + ConstantUtils.MILLS_PER_MIN);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() - System.currentTimeMillis();
    }

    /**
     * numberToString
     *
     * @param value  value
     * @param length length
     * @return str
     */
    public static String numberToString(long value, int length) {
        final int ten = 10;
        int up = 1;
        for (int i = 0; i < length; i++) {
            up *= ten;
        }
        value += up;
        String str = String.valueOf(value);
        int end = str.length();
        int start = end - length;
        return str.substring(start, end);
    }

    /**
     * isListHeaderOrFooter
     *
     * @param listView listView
     * @param position position
     * @return true - 是Header或者Footer
     */
    public static boolean isListHeaderOrFooter(ListView listView, int position) {
        int headerCount = listView.getHeaderViewsCount();
        int footerCount = listView.getFooterViewsCount();
        return position < headerCount || position >= listView.getAdapter().getCount() - footerCount;
    }

    /**
     * 根据包名和类的全名称（包名+类名），获取类类型对象
     *
     * @param fillName   手机版的全名称
     * @param fillNameHD 平板版的全名称
     * @return 类类型对象
     */
    public static Class getClass(String fillName, String fillNameHD) {
        String packageName = BaseApplication.getApplication().getPackageName();
        Class cls = null;
        try {
            if (packageName.equals("com.rednovo.weibo_hd")) {
                cls = Class.forName(fillNameHD);
            } else if (packageName.equals("com.rednovo.weibo")) {
                cls = Class.forName(fillName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    /**
     * entryRechargeActivity
     *
     * @param context context
     */
    public static void entryRechargeActivity(Context context) {
        String fillName = "com.rednovo.weibo.pay.PayActivity";
        String fillNameHD = "com.rednovo.weibo_hd.ui.RechargeActivity";
        Class cls = Utils.getClass(fillName, fillNameHD);
        if (cls != null) {
            context.startActivity(new Intent(context, cls));
        }
    }

    /**
     * entryLoginActivity
     *
     * @param context context
     */
    public static void entryLoginActivity(Context context) {
        String fillName = "com.rednovo.weibo.activity.LoginActivity";
        String fillNameHD = "com.rednovo.weibo_hd.ui.LoginActivity";
        Class cls = Utils.getClass(fillName, fillNameHD);
        if (cls != null) {
            context.startActivity(new Intent(context, cls));
        }
    }

    /**
     * showLoginAlertDialog
     *
     * @param context context
     */
    public static void showLoginAlertDialog(final Context context) {
        StandardDialog dialog = new StandardDialog(context);
        dialog.setPositiveButtonText(R.string.login_immediately);
        dialog.setNegativeButtonText(R.string.later);
        dialog.setContentText(R.string.login_dialog_content);
        dialog.setPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.entryLoginActivity(context);
            }
        });
        dialog.show();
    }

    /**
     * showDifferentLoginAlertDialog
     *
     * @param context           context
     * @param confirmButtonText 确认按钮文案
     * @param cancelButtonText  取消按钮文案
     * @param contentText       提示文案
     */
    public static void showDifferentLoginAlertDialog(final Context context
            , final String confirmButtonText
            , final String cancelButtonText
            , final String contentText) {
        StandardDialog dialog = new StandardDialog(context);
        dialog.setPositiveButtonText(confirmButtonText);
        dialog.setNegativeButtonText(cancelButtonText);
        dialog.setContentText(contentText);
        dialog.setPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.entryLoginActivity(context);
            }
        });
        dialog.show();
    }

    /**
     * getRoomListRequest
     *
     * @param type type
     * @return Request
     */
    public static Request<RoomListResult> getRoomListRequest(Enums.RoomListType type) {
        return getRoomListRequest(type, 1, PublicAPI.ROOM_LIST_PAGE_SIZE);
    }

    /**
     * getRoomListRequest
     *
     * @param type type
     * @param page page
     * @param size size
     * @return Request
     */
    public static Request<RoomListResult> getRoomListRequest(Enums.RoomListType type, int page, int size) {
    	int sbean = 0;
    	int ebean = 0;
        Request<RoomListResult> roomListRequest;
        switch (type) {
            default:
            case ALL:
                roomListRequest = PublicAPI.allStarRoomList(page, size, sbean, ebean);
                break;
            case RECOMMEND:
                roomListRequest = PublicAPI.recommendRoomList(page, size);
                break;
            case NEW:
                roomListRequest = PublicAPI.newRoomList( size);
                break;
            case LATEST:
                roomListRequest = PublicAPI.latestRoomList(page, size);
                break;
            case ALL_RANK_STAR:
                roomListRequest = PublicAPI.allRankStarRoomList(page, size);
                break;
            case SUPER_STAR:
                roomListRequest = PublicAPI.superStarRoomList(page, size);
                break;
            case GIANT_STAR:
                roomListRequest = PublicAPI.giantStarRoomList(page, size);
                break;
            case BRIGHT_STAR:
                roomListRequest = PublicAPI.brightStarRoomList(page, size);
                break;
            case RED_STAR:
                roomListRequest = PublicAPI.redStarRoomList(page, size);
                break;
        }
        return roomListRequest;
    }

    /**
     * listToArray
     *
     * @param list list
     * @return String []
     */
    public static String[] listToArray(List<String> list) {
        String[] ret = new String[list.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    /**
     * putFavStarIdList
     *
     * @param favStarListResult favStarListResult
     */
    public static void putFavStarIdList(FavStarListResult favStarListResult) {
        FavStarListResult.Data data = favStarListResult.getData();
        if (data != null) {
            List<FavStarListResult.StarInfo> starInfoList = data.getStarInfoList();
            if (starInfoList != null && starInfoList.size() > 0) {
                HashSet<Long> starIdList = new HashSet<Long>();
                for (FavStarListResult.StarInfo starInfo : starInfoList) {
                    starIdList.add(starInfo.getUser().getId());
                }
                CacheUtils.getObjectCache().add(CacheObjectKey.FAV_STAR_ID_LIST, starIdList);
            }
        }
    }

    /**
     * getFavStarIdList
     *
     * @return starIdList
     */
    public static Set<Long> getFavStarIdList() {
        Set<Long> starIdList = (Set<Long>) CacheUtils.getObjectCache().get(CacheObjectKey.FAV_STAR_ID_LIST);
        if (starIdList == null) {
            starIdList = new HashSet<Long>();
        }
        return starIdList;
    }

    /**
     * getHeadPortraitWidth
     *
     * @param view view
     * @return width
     */
    public static int getHeadPortraitWidth(View view) {
        final int def = 42;
        return getHeadPortraitWidth(view, def);
    }

    /**
     * getHeadPortraitWidth
     *
     * @param view view
     * @param def  def
     * @return width
     */
    public static int getHeadPortraitWidth(View view, int def) {
        int width = DisplayUtils.dp2px(def);
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.width > 0) {
            width = layoutParams.width;
        }
        return width;
    }

    /**
     * getHeadPortraitHeight
     *
     * @param view view
     * @return height
     */
    public static int getHeadPortraitHeight(View view) {
        final int def = 42;
        return getHeadPortraitHeight(view, def);
    }

    /**
     * getHeadPortraitHeight
     *
     * @param view view
     * @param def  def
     * @return height
     */
    public static int getHeadPortraitHeight(View view, int def) {
        int height = DisplayUtils.dp2px(def);
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            height = layoutParams.height;
        }
        return height;
    }
    
    /**检查是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) { 
    	if (context != null) { 
    		ConnectivityManager mConnectivityManager = (ConnectivityManager) context 
    				.getSystemService(Context.CONNECTIVITY_SERVICE); 
    		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo(); 
    		if (mNetworkInfo != null) { 
    			return mNetworkInfo.isAvailable(); 
    		} 
    	} 
    	return false; 
    }
    
    /**
     * 判断是否是魅族手机（是否有smartBar）
     *
     */
    public static boolean isFlyme() {
		try {
			// Invoke Build.hasSmartBar()
			final Method method = Build.class.getMethod("hasSmartBar");
			return method != null;
		} catch (final Exception e) {
			return false;
		}
	}
}
