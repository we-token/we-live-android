package show.we.lib.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import show.we.sdk.util.StringUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import show.we.lib.utils.StorageUtils;

/**
 * 友盟统计的key值
 *
 * @author ll
 * @version 1.0.0
 */
public class UMengConfig {
    /**
     * 记录公聊界面异常时聊天内容
     */
    public final static String KEY_CHATWINDOW_ERROR_STRING = "key_chatwindow_error_string";
    /**
     * websocket连接出错log
     */
    public static final String KEY_WEB_SOCKET_CONNECT_ERROR_LOG = "WebScoket Connect Error";
    /**
     * 视频缓冲的事件开销
     */
    public static final String KEY_VIDEO_BUFF_TIME_CONSUMING = "key_video_buff_time_consuming";
    /**
     * 获取时间戳的开销
     */
    public static final String KEY_GET_TIME_STAMP_CONSUMING = "key_get_sync_time_time_consuming";
    /**
     * 播放视频出错
     */
    public static final String KEY_PLAY_VIDEO_ERROR = "key_play_video_error";
    /**
     * websocket链接
     */
    public static final String KEY_WEBSOCKET_CONNECT = "key_websocket_connect";

    /**
     * 注册数量的统计
     */
    public static final String KEY_REGISTER_COUNT = "key_register_count";
    /**
     * 显示礼物动画的数量
     */
    public static final String KEY_GIFT_ANIM_COUNT = "key_gift_animation_count";

    /**
     * 成功
     */
    public static final String VALUE_SUCCEED = "value_succeed";
    /**
     * 失败
     */
    public static final String VALUE_FAIL = "value_fail";


    /** 3.7版本事件统计添加参数 **/

    /**
     * 侧边滑动抽屉各项点击统计事件Key
     */
    public static final String KEY_SLIDING_DRAWER_ITEM_CLICK = "key_sliding_drawer_item_click";
    /** 侧边栏Item项 enum **/
    public enum SlidingDrawerItem {
        /**
         * item 1
         * 用户简要信息
         */
        USER_SIMPLE_INFO("用户简要信息"),

        /**
         * item 2
         * 金币中心
         */
        STAR_COIN_CENTER("金币中心"),

        /**
         * item 3
         * 直播广场
         */
        LIVE_PLAZA("直播广场"),

        /**
         * item 4
         * 排行榜
         */
        RANK_LIST("排行榜"),

        /**
         *item 5
         * 家族
         */
        FAMILY_PAGE("家族"),

        /**
         * item 6
         * 官方话题
         */
        OFFICIAL_TOPIC("官方话题"),

        /**
         * item 7
         * 设置
         */
        SETTINGS("设置");

        private String mValue;

        private SlidingDrawerItem(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 直播间弹窗选项事件统计
     */
    /** 底部弹窗统计Key **/
    public static final String KEY_LIVE_ROOM_BOTTOM_POPMENU_ITEM_CLICK = "key_liveroom_bottom_popmenu_item_click";
    /** 顶部弹窗统计Key **/
    public static final String KEY_LIVE_ROOM_TOP_POP_MENU_ITEM_CLICK = "key_liveroom_top_popmenu_item_click";
    /** 直播间弹出菜单 **/
    public enum LiveRoomPopMenuItem {
        /**
         * top item1
         * 关注主播
         */
        FOCUS_STAR("关注主播"),

        /**
         * top item2
         * 主播档案
         */
        STAR_ZONE("主播档案"),

        /**
         * top item3
         * 分享给朋友
         */
        SHARE_LIVE("分享给朋友"),

        /**
         * top item4
         * 添加到桌面
         */
        ADD_SHORTCUT("添加到桌面"),

        /**
         * bottom item1
         * 礼物面板
         */
        GIFT_PANEL("礼物面板"),

        /**
         * bottom item2
         * 送羽毛
         */
        SEND_FEATHER("送羽毛"),

        /**
         * bottom item3
         * 充值
         */
        RECHARGE("充值"),

        /**
         * bottom item4
         * 金币中心
         */
        STAR_COIN_CENTER("金币中心"),

        /**
         * bottom item6
         * 修改昵称
         */
        MODIFY_NICKNAME("修改昵称"),

        /**
         * bottom item7
         * 广播
         */
        BROADCAST("广播"),

        /**
         * bottom item8
         * 点歌
         */
        SONG_ORDER("点歌"),

        /**
         * bottom item9
         * 奇迹礼物
         */
        WONDER_GIFT("奇迹礼物"),

        /**
         * bottom item10
         * 设置
         */
        SETTINGS("设置"),

        /**
         * bottom item11
         * 摇奖游戏
         */
        SLOT_MACHINE_GAME("摇奖游戏"),

        /**
         * bottom item12
         * 意见反馈
         */
        ADVICE_ME("意见反馈");


        private String mValue;

        private LiveRoomPopMenuItem(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 直播大厅各个主播页加载的状态和时长统计
     */
    /** 直播大厅状态统计KEY **/
    public final static String KEY_LIVE_PLAZA_STATUS = "key_live_plaza_status";
    /** **/
    public enum LivePlazaPage {

        /**
         * page 1
         * 全部主播加载
         */
        ALL_STAR_PAGE("全部主播"),

        /**
         * page 1 load more
         * 全部主播页加载更多
         */
        ALL_STAR_PAGE_MORE("全部主播More"),

        /**
         * page 2
         * 热门主播加载
         */
        RECOMMEND_STAR_PAGE("热门主播"),

        /**
         * page 2 load more
         * 热门主播加载更多
         */
        RECOMMED_STAR_PAGE_MORE("热门主播More"),

        /**
         * page 3
         * 明星主播加载
         */
        ALL_RANK_STAR_PAGE("明星主播"),

        /**
         * page 3 load more
         * 明星主播加载更多
         */
        ALL_RANK_STAR_PAGE_MORE("明星主播More");

        private String mValue;

        private LivePlazaPage(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 金币中心各个Item项的点击事件统计
     */
    public static final String KEY_STAR_COIN_CENTER_ITEM_CLICK = "key_star_coin_center_item_click";

    /**
     * 金币中心各个Item项的enum
     */
    public enum StarCoinCenterItem {
        /**
         * item 1
         * 有奖任务
         */
        AWARD_MISSION("新手任务"),

        /**
         * item 2
         * 每日签到
         */
        DAILY_ATTENDANCE("每日签到"),

        /**
         * item 3
         * 特权VIP
         */
        UPGRAD_VIP("特权VIP"),

        /**
         * item 4
         * 酷炫座驾
         */
        MOUNT_MALL("酷炫座驾"),

        /**
         * item 5
         * 充值记录
         */
        RECHARGE_RECORD("充值记录"),

        /**
         * item 6
         * 充值
         */
        RECHARGE("充值"),
        /**
         * item 7
         * 消费记录
         */
        COST_RECORD("消费记录"),
        /**
         * item 8
         * 消费记录
         */
        RECEIVE_GIFT_RECORD("收礼记录");

        private String mValue;

        private StarCoinCenterItem(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 排行榜统计数据
     */
    /** 排行榜各个页面点击情况统计的KEY **/
    public final static String KEY_RANK_PAGE_CLICK = "key_rank_page_click";

    /**
     * 排行榜各个页面的enum
     */
    public enum RankPage {
        /**
         * page 1
         * 明星榜
         */
        RANK_STAR("明星榜"),

        /**
         * page 2
         * 富豪榜
         */
        WEALTH_RANK("富豪榜"),

        /**
         * page 3
         * 礼物之星
         */
        GIFT_RANK("礼物之星"),

        /**
         * page 4
         * 奇迹礼物
         */
        WONDER_GIFT("奇迹之星");

        private String mValue;

        private RankPage(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 搜索事件统计
     */
    /** 搜索事件统计 **/
    public final static String KEY_SEARCH_STATISTIC = "key_search_statistic";
    /** 搜索类型统计 **/
    public final static String KEY_SEARCH_TYPE = "搜索类型";
    /** 搜索时长统计 **/
    public final static String KEY_SEARCH_DURATION = "搜索时长";
    /** 搜索状态 **/
    public final static String KEY_SEARCH_STATUS = "搜索结果";

    /**
     * 搜索类型 enum
     */
    public enum SearchType {
        /**
         * type 1
         * 关键字类型搜索
         */
        KEY_WORD_TYPE("关键字类型"),

        /**
         * type 2
         * ID类型搜索
         */
        KEY_ID_TYPE("ID类型搜索");

        private String mValue;

        private SearchType(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 设置页事件统计
     */
    public final static String KEY_SETTINGS_PAGE_ITEM_CLICK = "key_settings_page_item_click";

    /**
     * 设置页各个Item项的enum
     */
    public enum SettingsPageItem {
        /**
         * item 1
         * 修改密码
         */
        MODIFY_PASSWORD("修改密码"),

        /**
         * item 2
         * 开播提醒
         */
        STAR_NOTIFY("开播提醒"),

        /**
         * item 3
         * 广播跑道
         */
        BROADCAST_TRACK("广播跑道"),

        /**
         * item 4
         * 礼物跑道
         */
        GIFT_TRACK("礼物跑道"),

        /**
         * item 5
         * 进场信息
         */
        ENTERY_MESSAGE("进场信息"),

        /**
         * item 6
         * 美女闹铃
         */
        BEAUTY_CLOCK("美女闹铃"),

        /**
         * item 7
         * 应用推荐
         */
        APP_RECOMMEND("应用推荐"),

        /**
         * item 8
         * 关于微播
         */
        ABOUT_TTXIU("关于微播"),

        /**
         * item 9
         * 退出登录
         */
        LOGOUT("退出登录");

        private String mValue;

        private SettingsPageItem(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }


    private static final String UPGRADE_CHANNEL_NAME = "upgrade_channel";
    private static final String UPGRADE_VERSION_CODE = "upgrade_version";
    private static final String UPGRADE_TRIGGER = "upgrade_trigger";
    private static final String UMENG_CHANNEL = "UMENG_CHANNEL";
    private static final String UPGRADE_ALL_CHANNEL = "All";
    /**
     * 获取需强制更新的渠道号
     * @param context context
     * @return channel
     */
    public static String getUpgradeChannelName(Context context) {
        return MobclickAgent.getConfigParams(context, UPGRADE_CHANNEL_NAME);
    }

    /**
     * 获取需强制跟新的版本号
     * @param context context
     * @return version code
     */
    public static int getUpgradeVersionCode(Context context) {
        String versionCode = MobclickAgent.getConfigParams(context, UPGRADE_VERSION_CODE);
        if (StringUtils.isEmpty(versionCode)) {
            return 0;
        }
        return Integer.parseInt(versionCode);
    }

    /**
     * 获取强制更新开关
     * @param context context
     * @return boolean
     */
    public static boolean getUpgraderTrigger(Context context) {
        String trigger = MobclickAgent.getConfigParams(context, UPGRADE_TRIGGER);

        if ("true".equals(trigger)) {
            return true;
        }
        return false;
    }

    /**
     * 获取Umeng channel
     * @param  context context
     * @return channel
     */
    public static String getUmengChannel(Context context) {
        String umengChannel = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            umengChannel = appInfo.metaData.getString(UMENG_CHANNEL);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return umengChannel;
    }

    /**
     * 判断应用渠道号是否与强制更新渠道号相符合
     * @param context context
     * @return boolean
     */
    public static boolean checkUpgradChannelValid(Context context) {
        String onlineChannel = getUpgradeChannelName(context);
        String umengChannel = getUmengChannel(context);

        if (UPGRADE_ALL_CHANNEL.equals(onlineChannel)) {
            return true;
        }

        if (StringUtils.isEmpty(onlineChannel) || StringUtils.isEmpty(umengChannel)) {
            return false;
        }
        if (onlineChannel.equals(umengChannel)) {
            return true;
        }
        return false;
    }

    private static int getPackageInfoVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断是否version code相等
     * @param context context
     * @return versioncode
     */
    public static boolean checkVersionCodeValid(Context context) {
        int onlineVersionCode = getUpgradeVersionCode(context);
        int appVersionCode = getPackageInfoVersionCode(context);
        if (onlineVersionCode == 0 || appVersionCode == 0) {
            return false;
        }

        if (onlineVersionCode == appVersionCode) {
            return true;
        }
        return false;
    }
    /**
     * 直播间发送按钮统计
     */
    public final static String  KEY_LIVEROOM_SEND_MESSAGE_CLICK = "key_liveroom_send_message_click";
    /**
     * 发送次数
     */
    public final static String  SEND_COUNT_STR = "发送次数";

    /**
     * 音视频切换功能使用率统计
     */
    public final static String KEY_SWITCH_AUDIO_VIDEO = "key_switch_audio_video";

    /**
     * 直播间流切换
     */
    public enum LiveRoomSteamType {
        /**
         * 音频流
         */
        AUDIO("audio"),

        /**
         * 视频流
         */
        VIDEO("video");

        private String mValue;

        private LiveRoomSteamType(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 用户区别TAG
     */
    public enum UserTagType {
        /**
         * 未注册用户Tag
         */
        UN_REGISTER_USER("未注册用户"),

        /**
         * 注册用户Tag
         */
        REGISTER_USER("注册用户"),

        /**
         * 未付费用户Tag
         */
        UN_EXPENSE_USER("未付费用户"),

        /**
         * 付费用户Tag
         */
        EXPENSE_USER("付费用户");

        private String mValue;

        private UserTagType(String value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }

    }

    /**
     * 标记该用户是否需要重置用户标签的String
     */
    public static final String MARK_TAG_FOR_USER_STATUS = "markUserTagStatus";
    /**
     * 添加Umeng统计标签，方便识别用户类型，方便精确推送
     * @param deleteTagList deleteTagList
     * @param addTagList addTagList
     * @param saveTagMills boolean
     * @param context context
     */
    public static void setUserTagForUmengStatistic(final UserTagType[] deleteTagList, final UserTagType[] addTagList
            , final boolean saveTagMills, final Context context) {
        new AsyncTask<String, String, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    PushAgent pushAgent = PushAgent.getInstance(context);
                    for (UserTagType userTagType: deleteTagList) {
                        pushAgent.getTagManager().delete(userTagType.getValue());
                    }
                    for (UserTagType userTagType: addTagList) {
                        pushAgent.getTagManager().add(userTagType.getValue());
                    }
                    if (saveTagMills) {
                        StorageUtils.getSharedPreferences().edit().putBoolean(MARK_TAG_FOR_USER_STATUS, false).apply();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return true;
            }
        } .execute();
    }
}
