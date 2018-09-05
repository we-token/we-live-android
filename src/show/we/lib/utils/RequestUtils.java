package show.we.lib.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.format.DateUtils;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import show.we.lib.ActivityManager;
import show.we.lib.BaseApplication;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.ErrorCode;
import show.we.lib.cloudapi.FamilyAPI;
import show.we.lib.cloudapi.GiftAPI;
import show.we.lib.cloudapi.PublicAPI;
import show.we.lib.cloudapi.ShopAPI;
import show.we.lib.cloudapi.UserSystemAPI;
import show.we.lib.cloudapi.WonderGiftAPI;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Config;
import show.we.lib.config.SharedPreferenceKey;
import show.we.lib.config.UMengConfig;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.BagGift;
import show.we.lib.model.BagGiftResult;
import show.we.lib.model.Family;
import show.we.lib.model.FamilyInfoResult;
import show.we.lib.model.FamilyMemberData;
import show.we.lib.model.FavStarListResult;
import show.we.lib.model.Finance;
import show.we.lib.model.GiftListResult;
import show.we.lib.model.KeyWordResult;
import show.we.lib.model.MissionNumResult;
import show.we.lib.model.MountListResult;
import show.we.lib.model.PropertiesListResult;
import show.we.lib.model.PublicInformResult;
import show.we.lib.model.RechargeRecordListResult;
import show.we.lib.model.SensitiveWordResult;
import show.we.lib.model.SignListResult;
import show.we.lib.model.TaskListResult;
import show.we.lib.model.UserInfoResult;
import show.we.lib.model.WonderGiftResult;
import show.we.lib.service.FavStarOnlinePush;
import show.we.lib.ui.OnDrawBonusListener;
import show.we.lib.ui.OnFavStarsChangedListener;
import show.we.lib.ui.OnLoginFinishListener;
import show.we.lib.ui.OnMissionChangeListener;
import show.we.lib.ui.OnMountMallChangeListener;
import show.we.lib.widget.abc_pull_to_refresh.RefreshLoadLayout;
import show.we.sdk.cache.ImageCache;
import show.we.sdk.request.BaseResult;
import show.we.sdk.usersystem.Authorize;
import show.we.sdk.usersystem.UserResult;
import show.we.sdk.util.ConstantUtils;
import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.SecurityUtils;
import show.we.sdk.util.StringUtils;

/**
 * Created by CG on 13-11-5.
 *
 * @author ll
 * @version 3.0.0
 */
public class RequestUtils {

    private static final String TAG = "RequestUtils";
    private static RefreshLoadLayout mRefreshLayout;

    /**
     * 由于添加、取消关注以后服务器有一段时间延迟，一直找不到原因，所以添加、取消关注操作成功以后30秒之内请求关注列表就直接返回 *
     */
    private static final long REQUEST_FAV_STAR_DELAY = 30 * 1000;
    private static long mLastAddOrDelFavTime;

    /**
     * checkIsUpdateMission
     *
     * @param missionId missionId
     * @param context   context
     */
    public static void checkIsUpdateMission(String missionId, Context context) {
       // MissionListResult missionListResult = (MissionListResult) CacheUtils.getObjectCache().get(CacheObjectKey.MISSION_LIST_KEY);
        //if (missionListResult == null || missionListResult.getData().getCompletedMission().get(missionId) == null) {
         //   requestMission(context, false);
        //}
    }

    /**
     * 请求座驾信息
     */
    public static void requestMountMall() {
        ShopAPI.mountMall().execute(new BaseRequestCallback<MountListResult>() {

            @Override
            public void onRequestSucceed(MountListResult mountListResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.MOUNT_MALL_KEY, mountListResult);
                if (ActivityManager.instance().getCurrentActivity() instanceof OnMountMallChangeListener) {
                    ((OnMountMallChangeListener) ActivityManager.instance().getCurrentActivity()).onMountMallChanged();
                }
            }

            @Override
            public void onRequestFailed(MountListResult mountListResult) {
            }
        });
    }

    /**
     * 登录后请求任务奖励列表
     *
     * @param context context
     */
    public static void requestMission(final Context context) {
        if (UserInfoUtils.getAccessToken() == null) {
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_MISSION_FAIL);
            return;
        }
        UserSystemAPI.mission(UserInfoUtils.getAccessToken()).execute(new BaseRequestCallback<TaskListResult>() {
            @Override
            public void onRequestSucceed(TaskListResult taskListResult) {
            		CacheUtils.getObjectCache().add(CacheObjectKey.MISSION_LIST_KEY, taskListResult.getData().getTask());
                if (ActivityManager.instance().getCurrentActivity() instanceof OnMissionChangeListener) {
                    ((OnMissionChangeListener) ActivityManager.instance().getCurrentActivity()).onMissionListChanged();
                }
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MISSION_CHANGE);
            }

            @Override
            public void onRequestFailed(TaskListResult taskListResult) {
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_MISSION_FAIL);
                Utils.checkErrorCode(context, taskListResult.getCode());
            }
        });
    }
    
    /**
     * 未登录请求任务列表
     *
     * @param context context
     */
    public static void requestUnLoginMission(final Context context) {
    	
    	int via = 1;
        UserSystemAPI.unLoginMission(via).execute(new BaseRequestCallback<TaskListResult>() {
            @Override
            public void onRequestSucceed(TaskListResult unLoginTaskListResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.MISSION_LIST_KEY, unLoginTaskListResult.getData().getTask());

                if (ActivityManager.instance().getCurrentActivity() instanceof OnMissionChangeListener) {
                    ((OnMissionChangeListener) ActivityManager.instance().getCurrentActivity()).onMissionListChanged();
                }
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MISSION_CHANGE);
            }

            @Override
            public void onRequestFailed(TaskListResult unLoginTaskListResult) {
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_MISSION_FAIL);
                Utils.checkErrorCode(context, unLoginTaskListResult.getCode());
            }
        });
    }

    /**
     * 请求添加一位关注的主播
     *
     * @param starId   主播id
     * @param nickName 昵称
     * @param starPic  主播头像
     * @param coverPic 房间封面
     * @param isLive   是否直播
     * @param context  context
     */
    public static void requestAddFavoriteStar(final long starId, final String nickName, final String starPic
            , final String coverPic, final boolean isLive, final Context context) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            UserSystemAPI.addFollow((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN), starId)
                    .execute(new BaseRequestCallback<BaseResult>() {

                        @Override
                        public void onRequestSucceed(BaseResult result) {
                            mLastAddOrDelFavTime = System.currentTimeMillis();
                            FavStarListResult favStarListResult = (FavStarListResult) CacheUtils.getObjectCache()
                                    .get(CacheObjectKey.FAV_STAR_LIST_KEY);
                            if (favStarListResult == null) {
                                favStarListResult = new FavStarListResult();
                                favStarListResult.setData(new FavStarListResult.Data());
                            }

                            FavStarListResult.User user = new FavStarListResult.User();
                            FavStarListResult.Room room = new FavStarListResult.Room();
                            user.setId(starId);
                            user.setNickName(nickName);
                            user.setPicUrl(starPic);

                            room.setIsLive(isLive);
                            room.setXyStarId(starId);
                            room.setId(starId);
                            room.setPicUrl(coverPic);
                            FavStarListResult.StarInfo starInfo = new FavStarListResult.StarInfo();
                            starInfo.setUser(user);
                            starInfo.setRoom(room);

                            favStarListResult.getData().getStarInfoList().add(starInfo);
							CacheUtils.getObjectCache().add(CacheObjectKey.FAV_STAR_LIST_KEY, favStarListResult);
							Utils.putFavStarIdList(favStarListResult);

                            FavStarOnlinePush.getInstance(context).checkFavStarOnline(context, favStarListResult);
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ADD_FOLLOWING_SUCCESS);
                            Activity curActivity = ActivityManager.instance().getCurrentActivity();
                            if (curActivity instanceof OnFavStarsChangedListener) {
                                ((OnFavStarsChangedListener) curActivity).onFavStarsChanged(true, true, starId);
                            }
                            PromptUtils.showToast(R.string.focus_star_success, Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onRequestFailed(BaseResult result) {
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ADD_FOLLOWING_FAIL);
                            Activity curActivity = ActivityManager.instance().getCurrentActivity();
                            if (curActivity instanceof OnFavStarsChangedListener) {
                                ((OnFavStarsChangedListener) curActivity).onFavStarsChanged(false, true, starId);
                            }
                            PromptUtils.showToast(R.string.focus_star_fail, Toast.LENGTH_SHORT);
                            if (!Utils.checkErrorCode(context, result.getCode())) {
                                PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
                            }
                        }
                    });
        }
    }

    /**
     * 请求移除一位关注的主播
     *
     * @param starId  主播id
     * @param context context
     */
    public static void requestDelFavoriteStar(final long starId, final Context context) {
        if (UserInfoUtils.getAccessToken() == null) {
            return;
        }
        UserSystemAPI.delFollow(UserInfoUtils.getAccessToken(), starId).execute(new BaseRequestCallback<BaseResult>() {
            @Override
            public void onRequestSucceed(BaseResult result) {
                mLastAddOrDelFavTime = System.currentTimeMillis();
                if (CacheUtils.getObjectCache().contain(CacheObjectKey.FAV_STAR_LIST_KEY)) {
                    FavStarListResult favStarListResult = (FavStarListResult) CacheUtils.getObjectCache()
                            .get(CacheObjectKey.FAV_STAR_LIST_KEY);
                    for (FavStarListResult.StarInfo starInfo : favStarListResult.getData().getStarInfoList()) {
                        if (starInfo.getUser().getId() == starId) {
                            favStarListResult.getData().getStarInfoList().remove(starInfo);
                            break;
                        }
                    }
                    CacheUtils.getObjectCache().add(CacheObjectKey.FAV_STAR_LIST_KEY, favStarListResult);
					Utils.putFavStarIdList(favStarListResult);
                }

                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CANCEL_FOCUS_STAR_SUCCESS);
                Activity curActivity = ActivityManager.instance().getCurrentActivity();
                if (curActivity instanceof OnFavStarsChangedListener) {
                    ((OnFavStarsChangedListener) curActivity).onFavStarsChanged(true, false, starId);
                }
                PromptUtils.showToast(R.string.unfocus_star_success, Toast.LENGTH_SHORT);
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.DEL_FAVORITE_STAR);
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.CANCEL_FOCUS_STAR_FAIL);
                Activity curActivity = ActivityManager.instance().getCurrentActivity();
                if (curActivity instanceof OnFavStarsChangedListener) {
                    ((OnFavStarsChangedListener) curActivity).onFavStarsChanged(false, false, starId);
                }
                PromptUtils.showToast(R.string.unfocus_star_fail, Toast.LENGTH_SHORT);
                if (!Utils.checkErrorCode(context, result.getCode()) && result.isUnableConnectServer()) {
                    PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 请求收藏的主播
     *
     * @param context context
     */
    public static void requestFavStar(final Context context) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            /** 由于添加、取消关注以后服务器有一段时间延迟，一直找不到原因，所以添加、取消关注操作成功以后30秒之内请求关注列表就直接返回 **/
            if (System.currentTimeMillis() - mLastAddOrDelFavTime > REQUEST_FAV_STAR_DELAY) {
                UserSystemAPI.favoriteStar((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN))
                        .execute(new BaseRequestCallback<FavStarListResult>() {
                            @Override
                            public void onRequestSucceed(FavStarListResult favStarListResult) {
                                for (FavStarListResult.Room room : favStarListResult.getData().getRoomList()) {
                                    room.setVisitorCount(RoomResultUtils.fakeCount(room.getVisitorCount()));
                                }
                                postFavStarOnlineResult(favStarListResult, context);
                            }

                            @Override
                            public void onRequestFailed(FavStarListResult favStarListResult) {
                                Utils.checkErrorCode(context, favStarListResult.getCode());
                                FavStarOnlinePush.getInstance(context).updateFavStarOnlineDelay();
                                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_FAV_STAR_FAILED);
                            }
                        });
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_FAV_STAR_SUCCESS);
                    }
                }, ConstantUtils.MILLIS_PER_SECOND);
            }
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_FAV_STAR_FAILED);
                }
            }, ConstantUtils.MILLIS_PER_SECOND);
        }
    }

    private static void postFavStarOnlineResult(FavStarListResult favStarListResult, final Context context) {
        new AsyncTask<FavStarListResult, Intent, FavStarListResult>() {
            @Override
            protected FavStarListResult doInBackground(FavStarListResult... params) {
                PerformanceCheckUtils.printDuration("request fav star finish");
                params[0].getData().sort();
                PerformanceCheckUtils.printDuration("request fav star finish, and sort finish, count: "
                        + params[0].getData().getStarInfoList().size());
                return params[0];
            }

            @Override
            protected void onPostExecute(FavStarListResult favStarListResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.FAV_STAR_LIST_KEY, favStarListResult);
				Utils.putFavStarIdList(favStarListResult);
                FavStarOnlinePush.getInstance(context).checkFavStarOnline(context, favStarListResult);
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_FAV_STAR_SUCCESS);
            }
        } .execute(favStarListResult);
    }

    /**
     * 发送用户赠送背包礼物的消息
     * @param bagGift   背包礼物
     * @param roomId    房间id
     * @param giftId    礼物id
     * @param dstUserId 目标用户id
     * @param count     礼物数量
     * @param context   context
     */
    public static void requestSendBagGift(final BagGift bagGift, long roomId, final long giftId, long dstUserId, final int count, final Context context) {
        GiftAPI.sendBagGift((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN), roomId, giftId, dstUserId, count)
                .execute(new BaseRequestCallback<BaseResult>() {

                    @Override
                    public void onRequestSucceed(BaseResult result) {
                        LiveUtils.requestStarInfo();
                        StatisticUtils.addSingleEvent(StatisticUtils.MODULE_LIVE, StatisticUtils.TYPE_ISSUE,
                                StatisticUtils.ORIGIN_GIFT, StatisticUtils.VALUE_SUCCESS);
                    }

                    @Override
                    public void onRequestFailed(BaseResult result) {
                        if (!Utils.checkErrorCode(context, result.getCode())) {
                            if (CacheUtils.getObjectCache().contain(CacheObjectKey.BAG_GIFT_LIST_KEY)) {
                                BagGiftResult bagGiftResult = (BagGiftResult) CacheUtils.getObjectCache()
                                        .get(CacheObjectKey.BAG_GIFT_LIST_KEY);

                                Map<Object, Object> bagMap = bagGiftResult.getData().getBagMap();
                                if (bagMap != null && bagMap.get(giftId + "") != null) {
                                    long value = Long.parseLong(bagMap.get(giftId + "").toString()) + count;
                                    bagMap.put(giftId + "", value);
                                }
                                long cost = count * bagGift.getCoinPrice();
                                Finance finance = UserInfoUtils.getUserInfo().getData().getFinance();
                                finance.setCoinSpendTotal(finance.getCoinSpendTotal() - cost, true);
                            }

                            if (result.isUnableConnectServer()) {
                                PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
                            } else {
                                if (ErrorCode.ERROR_NEED_VIP == result.getCode()) {
                                    PromptUtils.showToast(R.string.need_vip, Toast.LENGTH_SHORT);
                                } else {
                                    PromptUtils.showToast(R.string.send_gift_failure, Toast.LENGTH_SHORT);
                                }
                                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ISSUE_GIFT_LIST_DIALOG_NOTIFY);
                            }

                            StatisticUtils.addSingleEvent(StatisticUtils.MODULE_LIVE, StatisticUtils.TYPE_ISSUE,
                                    StatisticUtils.ORIGIN_GIFT, StatisticUtils.VALUE_FAIL);
                        }
                    }
                });
    }

    /**
     * 送财神
     * @param context context
     * @param roomId roomId
     * @param count count
     */
    public static void requestSendFortune(final Context context, long roomId, int count) {
        String accessToken = UserInfoUtils.getAccessToken();
        if (accessToken != null) {
            GiftAPI.sendFortune(accessToken, roomId, count).execute(new BaseRequestCallback<BaseResult>() {
                @Override
                public void onRequestSucceed(BaseResult baseResult) {
                    processSendGiftSuccess(context);
                }

                @Override
                public void onRequestFailed(BaseResult result) {
                    processSendGiftFailed(context, result);
                }
            });
        }
    }

    /**
     * 发送用户赠送礼物的消息
     *
     * @param roomId          房间id
     * @param giftId          礼物id
     * @param dstUserId       目标用户的id
     * @param count           礼物数量
     * @param context         context
     */
    public static void requestSendGift(long roomId, long giftId, long dstUserId, int count, final Context context) {
        GiftAPI.sendGift((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN)
                , roomId, giftId, dstUserId, count, !Config.isSendGiftMarquee())
                .execute(new BaseRequestCallback<BaseResult>() {

                    @Override
                    public void onRequestSucceed(BaseResult result) {
                        processSendGiftSuccess(context);
                    }

                    @Override
                    public void onRequestFailed(BaseResult result) {
                        processSendGiftFailed(context, result);
                    }
                });
    }

    private static void processSendGiftSuccess(Context context) {
        LiveUtils.requestStarInfo();
        requestUpdateUserInfo(context, true, false, true, false, true, false);
        StatisticUtils.addSingleEvent(StatisticUtils.MODULE_LIVE, StatisticUtils.TYPE_ISSUE,
                StatisticUtils.ORIGIN_GIFT, StatisticUtils.VALUE_SUCCESS);
    }

    private static void processSendGiftFailed(Context context, BaseResult result) {
        if (!Utils.checkErrorCode(context, result.getCode())) {
            if (result.isUnableConnectServer()) {
                PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
            } else {
                if (ErrorCode.ERROR_NEED_VIP == result.getCode()) {
                    PromptUtils.showToast(R.string.need_vip, Toast.LENGTH_SHORT);
                } else {
                    PromptUtils.showToast(R.string.send_gift_failure, Toast.LENGTH_SHORT);
                }
            }

            StatisticUtils.addSingleEvent(StatisticUtils.MODULE_LIVE, StatisticUtils.TYPE_ISSUE,
                    StatisticUtils.ORIGIN_GIFT, StatisticUtils.VALUE_SUCCESS);
        }

        requestUpdateUserInfo(context, true, false, true, false, true, false);
    }

    /**
     * 异步请求任务奖励
     *
     * @param missionId 任务id
     * @param authCode  用户输入的验证码（授权码）
     * @param context   context
     */
    public static void requestDrawBonus(int taskId, /*String authCode, */final Context context) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
        	UserSystemAPI.drawBonus((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN), taskId/*, authCode*/)
                    .execute(new BaseRequestCallback<BaseResult>() {

                        @Override
                        public void onRequestSucceed(BaseResult result) {
                            requestUpdateUserInfo(context, true, true, true, true, true, true);
                            
                            PromptUtils.dismissProgressDialog();
                            if (ActivityManager.instance().getCurrentActivity() instanceof OnDrawBonusListener) {
                                ((OnDrawBonusListener) ActivityManager.instance().getCurrentActivity()).onDrawBonusSuccess();
                            }
                        }

                        @Override
                        public void onRequestFailed(BaseResult result) {
                            PromptUtils.dismissProgressDialog();
                            if (!Utils.checkErrorCode(context, result.getCode())) {
                                if (result.isUnableConnectServer()) {
                                    PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
                                } else {
                                    if (ActivityManager.instance().getCurrentActivity() instanceof OnDrawBonusListener) {
                                        ((OnDrawBonusListener) ActivityManager.instance().getCurrentActivity())
                                                .onDrawBonusError(result.getCode());
                                    }
                                }
                            }
                        }
                    });
        }
    }

    /**
     * 请求礼物列表
     */
    public static void requestBagGifts() {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            GiftAPI.bagGifts((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN))
                    .execute(new BaseRequestCallback<BagGiftResult>() {

                        @Override
                        public void onRequestSucceed(BagGiftResult bagGiftResult) {
                            CacheUtils.getObjectCache().add(CacheObjectKey.BAG_GIFT_LIST_KEY, bagGiftResult
                                    , ConstantUtils.MILLS_PER_HOUR);
                        }

                        @Override
                        public void onRequestFailed(BagGiftResult bagGiftResult) {
                        }
                    });
        }
    }

    /**
     * 请求礼物列表
     */
    public static void requestGiftList() {
        GiftAPI.giftList().execute(new BaseRequestCallback<GiftListResult>() {

            @Override
            public void onRequestSucceed(GiftListResult giftListResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.GIFT_LIST_KEY, giftListResult);
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ISSUE_GIFT_LIST_DIALOG_NOTIFY);
            }

            @Override
            public void onRequestFailed(GiftListResult giftListResult) {
            }
        });
    }

    /**
     * 请求礼物列表，并将图片下载下来
     */
    public static void requestGiftListWithImage() {
        GiftAPI.giftList().execute(new BaseRequestCallback<GiftListResult>() {
            @Override
            public void onRequestSucceed(GiftListResult giftListResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.GIFT_LIST_KEY, giftListResult);
                List<GiftListResult.Gift> gifts = giftListResult.getData().getGiftList();
                if (gifts != null) {
                    for (GiftListResult.Gift gift : gifts) {
                        String url = gift.getPicUrl();
                        if (!StringUtils.isEmpty(url)) {
                            Bitmap picture = CacheUtils.getImageCache().getBitmapFromMemCache(url, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
                            if (picture == null) {
                                CacheUtils.getImageCache().loadImageAsync(url, Integer.MAX_VALUE, Integer.MAX_VALUE, new ImageCache.Callback() {
                                    @Override
                                    public void imageLoaded(String s, int i, int i2, Bitmap bitmap) {
                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onRequestFailed(GiftListResult giftListResult) {
            }
        });
    }

    /**
     * 上传用户信息
     *
     * @param userInfoResult userInfoResult
     * @param context context
     * @param tag tag
     */
    public static void requestUploadUserInfo(UserInfoResult userInfoResult, final Context context, final int tag) {
        if (EnvironmentUtils.Network.isNetWorkAvailable()) {
            if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN) && LoginUtils.isAlreadyLogin()) {
                UserSystemAPI.uploadUserInfo((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN), userInfoResult)
                        .execute(new BaseRequestCallback<BaseResult>() {
                            @Override
                            public void onRequestSucceed(BaseResult result) {
                                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UPLOAD_USER_INFO_SUCCESS, tag);
                            }

                            @Override
                            public void onRequestFailed(BaseResult result) {
                                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.UPLOAD_USER_INFO_FAIL, tag);
                            }
                        });
            }
        } else {
            PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 上传用户经纬度
     * @param longitude longitude
     * @param latitude latitude
     * @param address address
     */
    public static void uploadUserLocation(double longitude, double latitude, String address) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            UserSystemAPI.uploadUserLocation((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN), longitude, latitude, address)
                    .execute(new BaseRequestCallback<BaseResult>() {
                        @Override
                        public void onRequestSucceed(BaseResult result) {
                        }

                        @Override
                        public void onRequestFailed(BaseResult result) {
                        }
                    });
        }
    }

    private static void checkUserUpgrade(UserInfoResult oldResult, UserInfoResult newResult) {
        if (oldResult != null) {
            long oldCoin = oldResult.getData().getFinance().getCoinSpendTotal();
            long newCoin = newResult.getData().getFinance().getCoinSpendTotal();
            UserInfoUtils.checkUserUpgrade(oldCoin, newCoin);
        }
    }

    /**
     * 家族相关操作后请求更新用户信息
     * @param context context
     */
    public static void requestUserInfoAboutFamily(final Context context) {
        requestUpdateUserInfo(context, false, false, false, false, false, true);
    }

    /**
     * 程序启动或登录完成后请求更新用户信息
     * @param context context
     */
    public static void requestUserInfoAfterLogin(final Context context) {
        requestUpdateUserInfo(context, true, true, true, true, true, true);
    } 

    /**
     * 支付完成后请求更新用户信息
     * @param context context
     */
    public static void requestUserInfoAfterRecharge(final Context context) {
        requestUpdateUserInfo(context, false, false, true, false, true, false);
    }

    /**
     * 请求更新用户信息
     * @param context context
     * @param needCheckUpgrade 是否需要检查升级
     * @param needRequestFeather 是否需要请求羽毛
     * @param needRequestBagGift 是否需要请求背包
     * @param needRequestFavStar 是否需要请求轮循关注主播（用于提醒用户主播开播）
     * @param needRequestMission 是否需要请求任务
     * @param needRequestMyFamily 是否需要请求家族
     */
    public static void requestUpdateUserInfo(final Context context, final boolean needCheckUpgrade
            , final boolean needRequestFeather, final boolean needRequestBagGift
            , final boolean needRequestFavStar, final boolean needRequestMission
            , final boolean needRequestMyFamily) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            UserSystemAPI.userInfo((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN))
                    .execute(new BaseRequestCallback<UserInfoResult>() {
                        @Override
                        public void onRequestSucceed(UserInfoResult userInfoResult) {
                            UserInfoResult oldResult = UserInfoUtils.getUserInfo();
                            CacheUtils.getObjectCache().add(CacheObjectKey.USER_INFO_KEY, userInfoResult);
                            if (needCheckUpgrade) {
                                checkUserUpgrade(oldResult, userInfoResult);
                            }
                            if (needRequestFeather) {
                                UserFeatherUtils.trigger();
                            }
                            if (needRequestBagGift) {
                                requestBagGifts();
                            }
                            if (needRequestFavStar) {
                                requestFavStar(context);
                            }
                            if (needRequestMission) {
                                requestMission(context);
                            }
                            if (needRequestMyFamily) {
                                requestMyFamily();
                            }
                            EnvironmentUtils.GeneralParameters.setUserId(userInfoResult.getData().getId());

                            doLoginStatistics(userInfoResult, context);

                            if (ActivityManager.instance().getCurrentActivity() instanceof OnLoginFinishListener) {
                                ((OnLoginFinishListener) ActivityManager.instance().getCurrentActivity())
                                        .onLoginFinished(userInfoResult);
                            }
                            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.USER_INFO_UPDATE);
                        }

                        @Override
                        public void onRequestFailed(UserInfoResult userInfoResult) {
                            if (!Utils.checkErrorCode(context, userInfoResult.getCode())) {
                                if (userInfoResult.isUnableConnectServer()) {
                                    PromptUtils.showToast(R.string.internet_error, Toast.LENGTH_SHORT);
                                } else if (userInfoResult.getCode() == ErrorCode.REGISTER_TOO_FREQUENT) {
                                    PromptUtils.showToast(R.string.register_too_frequent, Toast.LENGTH_SHORT);
                                }
                            }

                            if (ActivityManager.instance().getCurrentActivity() instanceof OnLoginFinishListener) {
                                ((OnLoginFinishListener) ActivityManager.instance().getCurrentActivity())
                                        .onLoginFinished(userInfoResult);
                            }
                        }
                    });
        }
    }

    private static void doLoginStatistics(UserInfoResult userInfoResult, Context context) {
        String latestDate = StorageUtils.getSharedPreferences().getString(SharedPreferenceKey.LATEST_LOGIN_REQUEST_DATE, "");
        String currentDate = DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_DATE).trim();
        if (!currentDate.equals(latestDate)) {
            if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
                PublicAPI.loginStatistics((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN)).execute(null);
            }
            String userInfo = userInfoResult.getData().getId() + "";
            StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.LATEST_LOGIN_REQUEST_DATE, currentDate).commit();
            StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.LOGIN_USERS_INFO, userInfo).commit();
        } else {
            String usersInfo = StorageUtils.getSharedPreferences().getString(SharedPreferenceKey.LOGIN_USERS_INFO, "");
            String[] userInfoArray = usersInfo.split("_");
            boolean find = false;
            for (String str : userInfoArray) {
                if (str.equals(userInfoResult.getData().getId() + "")) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
                    PublicAPI.loginStatistics((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN)).execute(null);
                }
                usersInfo += "_" + userInfoResult.getData().getId();
                StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.LOGIN_USERS_INFO, usersInfo).commit();
            }
        }

    }

    private static boolean mIsRegister;

    private static Authorize.Callback mAuthorizeCallback = new Authorize.Callback() {
        @Override
        public void onStart(String s) {

        }

        @Override
        public void onFinished(UserResult result) {
            EnvironmentUtils.GeneralParameters.setUserId(0);
            Activity curActivity = ActivityManager.instance().getCurrentActivity();
            UMengConfig.setUserTagForUmengStatistic(new UMengConfig.UserTagType[]{UMengConfig.UserTagType.UN_REGISTER_USER},
                    new UMengConfig.UserTagType[]{UMengConfig.UserTagType.REGISTER_USER}, false, curActivity);
            if (result == null) {
                if (curActivity instanceof OnLoginFinishListener) {
                    ((OnLoginFinishListener) curActivity).onAuthorizeFailure();
                }
            } else {
                if (result.isSuccess()) {
                    String accessToken = result.getData().getAccessToken();

                    StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.AUTHORIZE_INFO,
                            accessToken + result.getData().getUserName()).commit();
                    doPostAuthorize(result);
                    MobclickAgent.onEvent(BaseApplication.getApplication(), UMengConfig.KEY_REGISTER_COUNT);
                    StatisticUtils.addSingleEvent(StatisticUtils.MODULE_USER, StatisticUtils.TYPE_ISSUE,
                            mIsRegister ? StatisticUtils.ORIGIN_REGISTER : StatisticUtils.ORIGIN_LOGIN,
                            StatisticUtils.VALUE_SUCCESS, StatisticUtils.ktv_USER);
                    return;
                } else {
                    if (curActivity instanceof OnLoginFinishListener) {
                    	((OnLoginFinishListener) curActivity).onLoginFinished(result);
                    }
                }
            }

            StatisticUtils.addSingleEvent(StatisticUtils.MODULE_USER, StatisticUtils.TYPE_ISSUE,
                    mIsRegister ? StatisticUtils.ORIGIN_REGISTER : StatisticUtils.ORIGIN_LOGIN,
                    StatisticUtils.VALUE_FAIL, StatisticUtils.ktv_USER);
            mIsRegister = false;
        }
    };
    private static Authorize mAuthorize = new Authorize(Authorize.TYPE_ktv, mAuthorizeCallback);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 用户密码
     */
    public static void login(String username, String password) {
        mIsRegister = false;
        mAuthorize.cancel();
        mAuthorize.login(username, password);
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 用户密码
     * @param nickName 昵称名
     */
    public static void register(Context context,String username, String password, String nickName) {
        String authorizeInfo = StorageUtils.getSharedPreferences().getString(SharedPreferenceKey.AUTHORIZE_INFO, "");
        String accessToken = (String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN); 
        if (accessToken != null && authorizeInfo.contains(accessToken) && authorizeInfo.contains(username)) {
            login(username, password);
            	UserInfoResult mUserInfoResult = UserInfoUtils.getUserInfo();
            	System.out.println("---------------------hahahah");
        } else {
            mIsRegister = true;
            mAuthorize.cancel();
            mAuthorize.register(username, password);//, nickName
        }
    }

    /**
     * 第三方授权完成之后做的事情
     *
     * @param userResult 用户信息
     */
    public static void doPostThirdPartyAuthorize(UserResult userResult) {
        doPostAuthorize(userResult);
    }

    /**
     * 授权完成之后做的事情
     *
     * @param userResult 用户信息
     */
    private static void doPostAuthorize(UserResult userResult) {
        String accessToken = userResult.getData().getAccessToken();
        requestDailySignedList(accessToken);
        CacheUtils.getObjectCache().add(CacheObjectKey.ACCESS_TOKEN, accessToken);
        StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.ACCESS_TOKEN_KEY
                , SecurityUtils.RC4.encrypt(accessToken)).commit();

        requestUserInfoAfterRecharge(BaseApplication.getApplication());
    }

    /**
     * requestMissionCount
     */
    public static void requestMissionCount() {
        UserSystemAPI.getMissionNum().execute(new BaseRequestCallback<MissionNumResult>() {
            @Override
            public void onRequestSucceed(MissionNumResult dataResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.MISSION_COUNT, dataResult);
            }

            @Override
            public void onRequestFailed(MissionNumResult dataResult) {
            }
        });
    }

    /**
	 * 获取每日签到数据List Result
	 * 
	 * @param accessToken
	 *            accessToken
	 */
    public static void requestDailySignedList(String accessToken) {
        if (accessToken != null) {
            UserSystemAPI.getSignList(accessToken).execute(new BaseRequestCallback<SignListResult>() {
                @Override
                public void onRequestSucceed(SignListResult signListResult) {
                    if (signListResult != null) {
                        CacheUtils.getObjectCache().add(CacheObjectKey.DAY_SIGNED_LIST_KEY, signListResult);
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.DAILY_SIGN_LIST_RESULT);
                    }
                }

                @Override
                public void onRequestFailed(SignListResult result) {
                    CacheUtils.getObjectCache().delete(CacheObjectKey.DAY_SIGNED_LIST_KEY);
                }
            });
        }
    }

    /**
     * requestMyFamily
     */
    public static void requestMyFamily() {
        if (LoginUtils.isAlreadyLogin()) {
            Family family = UserInfoUtils.getUserInfo().getData().getFamily();
            if (family != null) {
                FamilyAPI.familyInfo(family.getFamilyId(), 1, 1).execute(new BaseRequestCallback<FamilyInfoResult>() {
                    @Override
                    public void onRequestSucceed(FamilyInfoResult dataResult) {
                        if (dataResult == null || dataResult.getData() == null) {
                            return;
                        }
                        if (dataResult.getData().getBigLeader() != null) {
                            dataResult.getData().getBigLeader().setLeaderTag(FamilyMemberData.BIG_LEADER);
                        }
                        if (dataResult.getData().getLeaders() != null) {
                            for (int i = 0; i < dataResult.getData().getLeaders().size(); i++) {
                                dataResult.getData().getLeaders().get(i).setLeaderTag(FamilyMemberData.NORMAL_LEADER);
                            }
                        }
                        CacheUtils.getObjectCache().add(CacheObjectKey.MY_FAMILY, dataResult);
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.MY_FAMILY_DATA_CHANGED);
                    }

                    @Override
                    public void onRequestFailed(FamilyInfoResult dataResult) {
                    }
                });
            } else {
                CacheUtils.getObjectCache().delete(CacheObjectKey.MY_FAMILY);
            }
        }
    }

    /**
     * 通过qq登录，请求AccessToken
     * @param openId openId
     * @param accessToken accessToken
     * @param clientId clientId
     * @param context context
     */
    public static void requestAccessToken(final Context context, String openId, String access_token, String clientId) {
        PromptUtils.showProgressDialog(context, context.getResources().getString(R.string.doing_login));
        UserSystemAPI.postUserAccessToken(openId, access_token, clientId)
                .execute(new BaseRequestCallback<UserResult>() {
                    @Override           
                    public void onRequestSucceed(UserResult result) {
                        CacheUtils.getObjectCache().add(CacheObjectKey.ACCESS_TOKEN, result.getData().getAccessToken());
                        StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.ACCESS_TOKEN_KEY
                                , SecurityUtils.RC4.encrypt(result.getData().getAccessToken())).commit();
                        requestUserInfoAfterLogin(context);
                        requestDailySignedList(result.getData().getAccessToken());
                    }
                    
                    @Override
                    public void onRequestFailed(UserResult result) {
                        PromptUtils.showToast(R.string.login_fail, Toast.LENGTH_SHORT);
                        PromptUtils.dismissProgressDialog();
                    }
                });
    }
    
//    /**
//     * 通过微信登录，请求AccessToken
//     * @param openId openId
//     * @param accessToken accessToken
//     * @param clientId clientId
//     * @param context context
//     */
//    public static void requestAccessTokenForWeChat(final Context context, String openId, String access_token) {
//        PromptUtils.showProgressDialog(context, context.getResources().getString(R.string.doing_login));
//        System.out.println("openid:--"+openId+"accesstoken:--"+access_token);
//        UserSystemAPI.postUserAccessTokenForWechat(openId, access_token)
//                .execute(new BaseRequestCallback<UserResult>() {
//                    @Override           
//                    public void onRequestSucceed(UserResult result) {
//                    	System.out.println("success--");
//                        CacheUtils.getObjectCache().add(CacheObjectKey.ACCESS_TOKEN, result.getData().getAccessToken());
//                        StorageUtils.getSharedPreferences().edit().putString(SharedPreferenceKey.ACCESS_TOKEN_KEY
//                                , SecurityUtils.RC4.encrypt(result.getData().getAccessToken())).commit();
//                        requestUserInfoAfterLogin(context);
//                        requestDailySignedList(result.getData().getAccessToken());
//                    }
//                    
//                    @Override
//                    public void onRequestFailed(UserResult result) {
//                    	System.out.println("failed--");
//                        PromptUtils.showToast(R.string.login_fail, Toast.LENGTH_SHORT);
//                        PromptUtils.dismissProgressDialog();
//                    }
//                });
//    }

    /**
     * 请求奇迹礼物，并将结果放入缓存中
     */
    public static void requestYesterdayWonderGift() {
        WonderGiftAPI.yesterdayGiftResult().execute(new BaseRequestCallback<WonderGiftResult>() {
            @Override
            public void onRequestSucceed(WonderGiftResult wonderGiftResult) {
                if (wonderGiftResult != null && wonderGiftResult.getData() != null
                        && wonderGiftResult.getData().getStar() != null) {
                    wonderGiftResult.getData().getStar().setVisiterCount(RoomResultUtils.fakeCount(wonderGiftResult.getData().getStar().getVisiterCount()));
                    CacheUtils.getObjectCache().add(CacheObjectKey.YESTERDAY_WONDER_GIFT_OBJECT, wonderGiftResult);
                }
            }

            @Override
            public void onRequestFailed(WonderGiftResult wonderGiftResult) {
                CacheUtils.getObjectCache().delete(CacheObjectKey.YESTERDAY_WONDER_GIFT_OBJECT);
            }
        });
    }

    /**
     * 请求敏感词并放入缓存
     */
    public static void requestSensitiveWord() {
        PublicAPI.requestSensitiveWord().execute(new BaseRequestCallback<SensitiveWordResult>() {
            @Override
            public void onRequestSucceed(SensitiveWordResult sensitiveWordResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.SENSITIVE_WORD, sensitiveWordResult.getData());
            }

            @Override
            public void onRequestFailed(SensitiveWordResult result) {
            }
        });
    }

    /**
     * 请求违禁词并放入缓存
     */
    public static void requestKeyWord() {
        PublicAPI.requestKeyWord().execute(new BaseRequestCallback<KeyWordResult>() {
            @Override
            public void onRequestSucceed(KeyWordResult keyWordResult) {
                CacheUtils.getObjectCache().add(CacheObjectKey.KEY_WORD, keyWordResult.getData());
            }

            @Override
            public void onRequestFailed(KeyWordResult result) {

            }
        });
    }
    /**
     * 请求公告
     */
    public static void requestPublicInform() {
        PublicAPI.requestPublicInform().execute(new BaseRequestCallback<PublicInformResult>() {
            @Override
            public void onRequestSucceed(PublicInformResult dataResult) {
                if (dataResult.getDataList().size() > 0 && dataResult.getDataList().get(0) != null) {
                    CacheUtils.getObjectCache().add(CacheObjectKey.RECHARGE_AWARD, dataResult.getDataList().get(0).getmMsg());
                }
            }

            @Override
            public void onRequestFailed(PublicInformResult dataResult) {

            }
        });
    }

    /**
     * 请求充值记录列表
     *
     * @param context context
     */
    public static void requestRechargeRecordList(final Context context, int Page, int Size, long stime, long etime, int id1, int id2) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)) {
            UserSystemAPI.rechargeRecordList((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN), Page, Size, stime, etime, id1, id2 )
                    .execute(new BaseRequestCallback<RechargeRecordListResult>() {
                        @Override
                        public void onRequestSucceed(RechargeRecordListResult rechargeRecordListResult) {
                            if (rechargeRecordListResult.isSuccess() && rechargeRecordListResult.getCount() != 0) {
                                UMengConfig.setUserTagForUmengStatistic(new UMengConfig.UserTagType[]{UMengConfig.UserTagType.UN_REGISTER_USER
                                        , UMengConfig.UserTagType.UN_EXPENSE_USER}, new UMengConfig.UserTagType[]{UMengConfig.UserTagType.REGISTER_USER
                                        , UMengConfig.UserTagType.EXPENSE_USER}, false, context);
                            }
                        }

                        @Override
                        public void onRequestFailed(RechargeRecordListResult rechargeRecordListResult) {
                        }
                    });
        }
    }
    /**
     * 请求一些新配置的属性列表
     */
    public static void requestPropertiesList() {
        PublicAPI.requestPropertiesList().execute(new BaseRequestCallback<PropertiesListResult>() {
            @Override
            public void onRequestSucceed(PropertiesListResult result) {
                Map<String, String> hashMap = new HashMap<String, String>();
                for (PropertiesListResult.Data eachData: result.getDataList()) {
                    hashMap.put(eachData.getmId(), eachData.getmContent());
                }
                if (result.getDataList().size() > 0) {
                    CacheUtils.getObjectCache().add(CacheObjectKey.PROPERTIES_LIST, hashMap);
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.REQUEST_NOTICE_LIST_SUCCESS);
                }
            }

            @Override
            public void onRequestFailed(PropertiesListResult result) {

            }
        });
    }
}
