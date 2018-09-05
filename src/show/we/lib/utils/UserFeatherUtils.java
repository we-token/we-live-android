package show.we.lib.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.Toast;
import show.we.sdk.request.BaseResult;
import show.we.lib.R;
import show.we.lib.cloudapi.BaseRequestCallback;
import show.we.lib.cloudapi.GiftAPI;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Config;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.UserInfoResult;
import show.we.lib.widget.animation.AnimationFactory;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-7-26
 *          Time: 上午11:19
 *          To change this template use File | Settings | File Templates.
 */
public class UserFeatherUtils {

    private static final int TRIGGER_MSG_ID = 0x01;
    private static final int FEATHER_GENERATE_INTERVAL = 5 * 60 * 1000;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (TRIGGER_MSG_ID == msg.what) {
                mHandler.removeMessages(TRIGGER_MSG_ID);
                requestGenerateFeather();
            }
        }
    };

    /**
     * 触发生成羽毛
     */
    public static void trigger() {
        if (!mHandler.hasMessages(TRIGGER_MSG_ID)) {
            mHandler.sendEmptyMessageDelayed(TRIGGER_MSG_ID, FEATHER_GENERATE_INTERVAL);
        }
    }

    private static final int MAX_GET_FEATHER_RETRY_TIMES = 3;
    private static int mGetFeatherRetryTime = 0;

    private static void requestGenerateFeather() {
        if (UserInfoUtils.getAccessToken() == null) {
            return;
        }
        GiftAPI.getFeather((String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN)).execute(new BaseRequestCallback<BaseResult>() {

            @Override
            public void onRequestSucceed(BaseResult result) {
                processRequestFinished(result);
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                processRequestFinished(result);
            }

            public void processRequestFinished(BaseResult baseResult) {
                if (LoginUtils.isAlreadyLogin() && baseResult != null) {
                    UserInfoResult userInfoResult = UserInfoUtils.getUserInfo();
                    int featherCount = userInfoResult.getData().getFinance() != null ? userInfoResult.getData().getFinance()
                            .getFeatherCount() : 0;

                    if (baseResult.isSuccess()) {
                        userInfoResult.getData().getFinance().setFeatherCount(++featherCount);
                        CacheUtils.getObjectCache().add(CacheObjectKey.USER_INFO_KEY, userInfoResult);
                        mGetFeatherRetryTime = 0;
                        DataChangeNotification.getInstance().notifyDataChanged(IssueKey.GET_FEATHER_SUCCESS);
                    } else {
                        if (mGetFeatherRetryTime < MAX_GET_FEATHER_RETRY_TIMES) {
                            mGetFeatherRetryTime++;
                            requestGenerateFeather();
                            return;
                        } else {
                            mGetFeatherRetryTime = 0;
                        }
                    }

                    if (featherCount < Config.MAX_FEATHER_COUNT) {
                        trigger();
                    }
                }
            }
        });
    }

    /**
     * 获取当前羽毛数
     *
     * @return 羽毛数量
     */
    public static int getFeatherNum() {
        return LoginUtils.isAlreadyLogin() ? UserInfoUtils.getUserInfo().getData().getFinance().getFeatherCount() : 1;
    }

    /**
     * 开始送羽毛动画
     *
     * @param animateImageView 执行动画的view
     * @param animateStart     动画开始位置对应的View
     * @param animateEnd       动画结束位置对应的View
     */
    public static void runSendFeatherAnimation(final ImageView animateImageView, View animateStart, View animateEnd) {
        if (animateImageView == null || animateStart == null || animateEnd == null) {
            return;
        }

        int[] userLoc = new int[2];
        animateStart.getLocationOnScreen(userLoc);

        int[] starLoc = new int[2];
        animateEnd.getLocationOnScreen(starLoc);

        runSendFeatherAnimation(animateImageView, userLoc, starLoc);
    }

    private static void runSendFeatherAnimation(final ImageView animateImageView, int[] startLocation, int[] endLocation) {
        int[] originLoc = new int[2];
        animateImageView.getLocationOnScreen(originLoc);

        final int duration = 2000;
        final float endAlpha = 0.3f;
        final float toScaleDegree = 0.5f;
        final float pivotCenter = 0.5f;
        AnimationSet otherAnimationSet = AnimationFactory.buildAnimationSet(true
                , AnimationFactory.buildAlphaAnimation(1.0f, endAlpha, duration)
                , AnimationFactory.buildScaleAnimation(1.0f, toScaleDegree, 1.0f, toScaleDegree,
                Animation.RELATIVE_TO_SELF, pivotCenter, Animation.RELATIVE_TO_SELF, pivotCenter, duration, 0));

        AnimationFactory.runTrackAnimationOnTempView(animateImageView, startLocation, endLocation, duration, otherAnimationSet);
    }

    /**
     * 异步请求赠送羽毛
     *
     * @param roomId 主播房间id
     */
    public static void requestSendFeather(long roomId) {
        if (UserInfoUtils.getAccessToken() == null) {
            return;
        }
        GiftAPI.sendFeather(UserInfoUtils.getAccessToken(), roomId).execute(new BaseRequestCallback<BaseResult>() {
            @Override
            public void onRequestSucceed(BaseResult result) {
                trigger();
            }

            @Override
            public void onRequestFailed(BaseResult result) {
                if (LoginUtils.isAlreadyLogin()) {
                    UserInfoResult userInfoResult = UserInfoUtils.getUserInfo();
                    if (userInfoResult.getData().getFinance() != null) {
                        userInfoResult.getData().getFinance().setFeatherCount(userInfoResult.getData().getFinance()
                                .getFeatherCount() + 1);
                        CacheUtils.getObjectCache().add(CacheObjectKey.USER_INFO_KEY, userInfoResult);
                    }
                    DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SEND_FEATHER_FAILURE);
                    PromptUtils.showToast(R.string.send_feather_error, Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
