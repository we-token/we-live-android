package show.we.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.Toast;
import show.we.lib.R;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.*;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.widget.animation.AnimationFactory;
import show.we.lib.widget.dialog.BaseDialog;

import java.util.List;
import java.util.Map;

/**
 * Created by CG on 13-10-28.
 *
 * @author ll
 * @version 3.0.0
 */
public class GiftUtils {

    /**
     * 根据礼物id获取礼物信息
     *
     * @param giftId 礼物Id
     * @return 对应Id的礼物信息
     */
    public static GiftListResult.Gift getGift(long giftId) {
        GiftListResult.Gift findGift = null;
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.GIFT_LIST_KEY)) {
            List<GiftListResult.Gift> gifts = ((GiftListResult) CacheUtils.getObjectCache().get(CacheObjectKey.GIFT_LIST_KEY))
                    .getData().getGiftList();
            for (GiftListResult.Gift gift : gifts) {
                if (gift.getId() == giftId) {
                    findGift = gift;
                }
            }
        }
        if (findGift == null) {
            RequestUtils.requestGiftList();
        }
        return findGift;
    }

    /**
     * getGiftList
     *
     * @return GiftListResult
     */
    public static GiftListResult getGiftList() {
        Object object = CacheUtils.getObjectCache().get(CacheObjectKey.GIFT_LIST_KEY);
        return object != null ? (GiftListResult) object : null;
    }

    /**
     * getBagGifts
     *
     * @return BagGiftResult
     */
    public static BagGiftResult getBagGifts() {
        return CacheUtils.getObjectCache().contain(CacheObjectKey.BAG_GIFT_LIST_KEY)
                ? (BagGiftResult) CacheUtils.getObjectCache().get(CacheObjectKey.BAG_GIFT_LIST_KEY)
                : null;
    }

    /**
     * requestSendGift
     *
     * @param dialog          发送礼物对话框
     * @param userId          接收礼物的用户id
     * @param gift            gift
     * @param count           count
     * @param giftImageView   用于礼物动画的开始位置及需要显示的礼物的图片
     */
    public static void requestSendGift(BaseDialog dialog, long userId, GiftListResult.Gift gift, int count
            , ImageView giftImageView) {
        if (gift == null || giftImageView == null) {
            PromptUtils.showToast(R.string.please_select_gift, Toast.LENGTH_SHORT);
        } else if (count <= 0) {
            PromptUtils.showToast(R.string.please_give_gift_number, Toast.LENGTH_SHORT);
        } else {
            Object[] objects = {gift, count, userId};
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SELECT_GIFT, objects);
            sendGift(dialog.getContext(), userId, gift, count, giftImageView);
            InputMethodUtils.hideSoftInput(dialog.getCurrentFocus());
            dialog.dismiss();
        }
    }

    /**
     * sendGift
     *
     * @param context         context
     * @param userId          接收礼物的用户id
     * @param gift            gift
     * @param count           count
     * @param giftImageView   用于礼物动画的开始位置及需要显示的礼物的图片
     */
    public static void sendGift(Context context, long userId, GiftListResult.Gift gift, int count, ImageView giftImageView) {
        if (gift instanceof BagGift) {
            BagGift bagGift = (BagGift) gift;
            RequestUtils.requestSendBagGift(bagGift, LiveCommonData.getRoomId(), gift.getId(), userId, count, context);
            if (CacheUtils.getObjectCache().contain(CacheObjectKey.BAG_GIFT_LIST_KEY)) {
                BagGiftResult bagGiftResult = (BagGiftResult) CacheUtils.getObjectCache().get(CacheObjectKey.BAG_GIFT_LIST_KEY);

                Map<Object, Object> bagMap = bagGiftResult.getData().getBagMap();
                if (bagMap != null && bagMap.get(bagGift.getId() + "") != null) {
                    long value = ((Number) bagMap.get(bagGift.getId() + "")).longValue() - count;
                    bagMap.put(bagGift.getId() + "", value);
                }
                long cost = count * bagGift.getCoinPrice();
                Finance finance = UserInfoUtils.getUserInfo().getData().getFinance();
                finance.setCoinSpendTotal(finance.getCoinSpendTotal() + cost, true);
            }
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SEND_GIFT_COMPLETED, giftImageView);
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ISSUE_GIFT_LIST_DIALOG_NOTIFY);
        } else {
            long cost = count * gift.getCoinPrice();
            UserInfoResult userInfoResult = UserInfoUtils.getUserInfo();
            long coinCount = userInfoResult.getData().getFinance().getCoinCount();
            if (coinCount >= cost) {
                Finance finance = UserInfoUtils.getUserInfo().getData().getFinance();
                finance.setCoinCount(coinCount - cost);
                finance.setCoinSpendTotal(finance.getCoinSpendTotal() + cost, true);
                if (gift.getName().equals(context.getString(R.string.god_of_wealth))) {
                    RequestUtils.requestSendFortune(context, LiveCommonData.getRoomId(), count);
                } else {
                    RequestUtils.requestSendGift(LiveCommonData.getRoomId(), gift.getId(), userId, count, context);
                }
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.SEND_GIFT_COMPLETED, giftImageView);
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.ISSUE_GIFT_LIST_DIALOG_NOTIFY);
            } else {
                PromptUtils.showToast(R.string.money_not_enough, Toast.LENGTH_SHORT);
				Utils.entryRechargeActivity(context);
            }
        }
    }

    private static final long TRANSLATE_DURATION_TIME = 2000L;
    private static final long SCALE_DURATION_TIME = 1000L;
    private static boolean mIsFlyingGift;

    /**
     * flyGiftAfterSent
     *
     * @param giftImageView giftImageView
     * @param activity      activity
     */
    public static void flyGiftAfterSent(ImageView giftImageView, Activity activity) {
        if (giftImageView == null || mIsFlyingGift) {
            return;
        }
        mIsFlyingGift = true;
        final ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        final ImageView animationImageView = new ImageView(activity);
        animationImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        animationImageView.setImageDrawable(giftImageView.getDrawable());

        int[] startLocation = new int[2];
        giftImageView.getLocationOnScreen(startLocation);

        int endX = DisplayUtils.getWidthPixels() / 2;
        int endY = (int) (3 * endX / 4f);

        AnimationSet animationSet = AnimationFactory.buildAnimationSet(true
                , AnimationFactory.buildTranslateAnimation(startLocation[0], endX, startLocation[1], endY, TRANSLATE_DURATION_TIME)
                , AnimationFactory.buildScaleAnimation(1f, 2f, 1f, 2f, SCALE_DURATION_TIME, TRANSLATE_DURATION_TIME));
        rootView.addView(animationImageView);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /** 不能直接remove掉animateView，否则ViewGroup的dispatchDraw方法会报空指针错误，具体原因不明 **/
                rootView.post(new Runnable() {
                    @Override
                    public void run() {
                        rootView.removeView(animationImageView);
                    }
                });

                mIsFlyingGift = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animationImageView.startAnimation(animationSet);
    }
}
