package show.we.lib.widget.animation;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;

import show.we.lib.ActivityManager;

/**
 * 动画工厂类
 *
 * @author ll
 * @version 1.0.0
 */
final public class AnimationFactory {

    private AnimationFactory() {
    }

    private static final int ABSOLUTE = Animation.ABSOLUTE;

    /**
     * TranslateAnimation
     *
     * @param fromXDelta fromXDelta
     * @param toXDelta   toXDelta
     * @param fromYDelta fromYDelta
     * @param toYDelta   toYDelta
     * @param duration   duration
     * @return TranslateAnimation
     */
    public static TranslateAnimation buildTranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long duration) {
        return buildTranslateAnimation(ABSOLUTE, fromXDelta, ABSOLUTE,
                toXDelta, ABSOLUTE, fromYDelta, ABSOLUTE, toYDelta, duration, false);
    }

    /**
     * TranslateAnimation
     *
     * @param fromXDelta fromXDelta
     * @param toXDelta   toXDelta
     * @param fromYDelta fromYDelta
     * @param toYDelta   toYDelta
     * @param fillAfter  fillAfter
     * @param duration   duration
     * @return TranslateAnimation
     */
    public static TranslateAnimation buildTranslateAnimation(float fromXDelta, float toXDelta
            , float fromYDelta, float toYDelta, long duration, boolean fillAfter) {
        return buildTranslateAnimation(ABSOLUTE, fromXDelta, ABSOLUTE,
                toXDelta, ABSOLUTE, fromYDelta, ABSOLUTE, toYDelta, duration, fillAfter);
    }

    /**
     * TranslateAnimation
     *
     * @param fromXType  fromXType
     * @param fromXValue fromXValue
     * @param toXType    toXType
     * @param toXValue   toXValue
     * @param fromYType  fromYType
     * @param fromYValue fromYValue
     * @param toYType    toYType
     * @param toYValue   toYValue
     * @param fillAfter  fillAfter
     * @param duration   duration
     * @return TranslateAnimation
     */
    public static TranslateAnimation buildTranslateAnimation(int fromXType, float fromXValue, int toXType, float toXValue
            , int fromYType, float fromYValue, int toYType, float toYValue, long duration, boolean fillAfter) {
        TranslateAnimation animation = new TranslateAnimation(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue, toYType, toYValue);
        animation.setDuration(duration);
        animation.setFillAfter(fillAfter);
        return animation;
    }

    /**
     * AlphaAnimation
     *
     * @param fromAlpha  fromAlpha
     * @param toAlpha    toAlpha
     * @param duration   duration
     * @param fillEnable fillEnable
     * @param fillAfter  fillAfter
     * @return AlphaAnimation
     */
    public static AlphaAnimation buildAlphaAnimation(float fromAlpha, float toAlpha, long duration, boolean fillEnable, boolean fillAfter) {
        AlphaAnimation animation = new AlphaAnimation(fromAlpha, toAlpha);
        animation.setFillEnabled(fillEnable);
        animation.setFillAfter(fillAfter);
        animation.setDuration(duration);
        return animation;
    }

    /**
     * AlphaAnimation
     *
     * @param fromAlpha fromAlpha
     * @param toAlpha   toAlpha
     * @param duration  duration
     * @return AlphaAnimation
     */
    public static AlphaAnimation buildAlphaAnimation(float fromAlpha, float toAlpha, long duration) {
        return buildAlphaAnimation(fromAlpha, toAlpha, duration, false, false);
    }

    /**
     * RotateAnimation
     *
     * @param fromDegrees fromDegrees
     * @param toDegrees   toDegrees
     * @param duration    duration
     * @return RotateAnimation
     */
    public static RotateAnimation buildRotateAnimation(float fromDegrees, float toDegrees, long duration) {
        return buildRotateAnimation(fromDegrees, toDegrees, 0.0f, 0.0f, duration);
    }

    /**
     * RotateAnimation
     *
     * @param fromDegrees fromDegrees
     * @param toDegrees   toDegrees
     * @param pivotX      pivotX
     * @param pivotY      pivotY
     * @param duration    duration
     * @return RotateAnimation
     */
    public static RotateAnimation buildRotateAnimation(float fromDegrees, float toDegrees, float pivotX, float pivotY, long duration) {
        return buildRotateAnimation(fromDegrees, toDegrees, ABSOLUTE, pivotX, ABSOLUTE, pivotY, duration);
    }


    /**
     * RotateAnimation
     *
     * @param fromDegrees fromDegrees
     * @param toDegrees   toDegrees
     * @param pivotXType  pivotXType
     * @param pivotXValue pivotXValue
     * @param pivotYType  pivotYType
     * @param pivotYValue pivotYValue
     * @param duration    duration
     * @return RotateAnimation
     */
    public static RotateAnimation buildRotateAnimation(float fromDegrees, float toDegrees, int pivotXType
            , float pivotXValue, int pivotYType, float pivotYValue, long duration) {
        return buildRotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue, duration, false);
    }

    /**
     * RotateAnimation
     *
     * @param fromDegrees fromDegrees
     * @param toDegrees   toDegrees
     * @param pivotXType  pivotXType
     * @param pivotXValue pivotXValue
     * @param pivotYType  pivotYType
     * @param pivotYValue pivotYValue
     * @param duration    duration
     * @param fillAfter   fillAfter
     * @return RotateAnimation
     */
    public static RotateAnimation buildRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue
            , int pivotYType, float pivotYValue, long duration, boolean fillAfter) {
        return buildRotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue
                , duration, fillAfter, new AccelerateDecelerateInterpolator());
    }

    /**
     * RotateAnimation
     *
     * @param fromDegrees  fromDegrees
     * @param toDegrees    toDegrees
     * @param pivotXType   pivotXType
     * @param pivotXValue  pivotXValue
     * @param pivotYType   pivotYType
     * @param pivotYValue  pivotYValue
     * @param duration     duration
     * @param interpolator interpolator
     * @param fillAfter    fillAfter
     * @return RotateAnimation
     */
    public static RotateAnimation buildRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue
            , int pivotYType, float pivotYValue, long duration, boolean fillAfter, Interpolator interpolator) {
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        animation.setInterpolator(interpolator);
        animation.setFillAfter(fillAfter);
        animation.setDuration(duration);
        return animation;
    }

    /**
     * ScaleAnimation
     *
     * @param fromX    fromX
     * @param toX      toX
     * @param fromY    fromY
     * @param toY      toY
     * @param duration duration
     * @return ScaleAnimation
     */
    public static ScaleAnimation buildScaleAnimation(float fromX, float toX, float fromY, float toY, long duration) {
        return buildScaleAnimation(fromX, toX, fromY, toY, duration, 0);
    }

    /**
     * @param fromX       fromX
     * @param toX         toX
     * @param fromY       fromY
     * @param toY         toY
     * @param duration    duration
     * @param startOffset startOffset
     * @return ScaleAnimation
     */
    public static ScaleAnimation buildScaleAnimation(float fromX, float toX, float fromY, float toY, long duration, long startOffset) {
        return buildScaleAnimation(fromX, toX, fromY, toY, 0.5f, 0.5f, duration, startOffset);
    }

    /**
     * ScaleAnimation
     *
     * @param fromX       fromX
     * @param toX         toX
     * @param fromY       fromY
     * @param toY         toY
     * @param pivotX      pivotX
     * @param pivotY      pivotY
     * @param duration    duration
     * @param startOffset startOffset
     * @return ScaleAnimation
     */
    public static ScaleAnimation buildScaleAnimation(float fromX, float toX, float fromY, float toY
            , float pivotX, float pivotY, long duration, long startOffset) {
        return buildScaleAnimation(fromX, toX, fromY, toY, Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY, duration, startOffset);
    }

    /**
     * ScaleAnimation
     *
     * @param fromX       fromX
     * @param toX         toX
     * @param fromY       fromY
     * @param toY         toY
     * @param pivotXType  pivotXType
     * @param pivotXValue pivotXValue
     * @param pivotYType  pivotYType
     * @param pivotYValue pivotYValue
     * @param duration    duration
     * @param startOffset startOffset
     * @return ScaleAnimation
     */
    public static ScaleAnimation buildScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType
            , float pivotXValue, int pivotYType, float pivotYValue, long duration, long startOffset) {
        ScaleAnimation animation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
        animation.setDuration(duration);
        animation.setStartOffset(startOffset);
        return animation;
    }

    /**
     * AnimationSet
     *
     * @param shareInterpolator shareInterpolator
     * @param animations        animations
     * @return AnimationSet
     */
    public static AnimationSet buildAnimationSet(boolean shareInterpolator, Animation... animations) {
        AnimationSet animationSet = new AnimationSet(shareInterpolator);
        for (Animation animation : animations) {
            animationSet.addAnimation(animation);
        }
        return animationSet;
    }

    /**
     * 运行位移组合动画
     *
     * @param animateView       动画View
     * @param startLocation     动画开始位置
     * @param endLocation       动画结束位置
     * @param duration          动画时长
     * @param otherAnimationSet 其他动画集合
     */
    public static void runTrackAnimationOnTempView(final View animateView, final int[] startLocation, final int[] endLocation
            , final long duration, final AnimationSet otherAnimationSet) {
        runTrackAnimationOnTempView(animateView, startLocation, endLocation, duration, otherAnimationSet, null);
    }

    /**
     * 运行位移组合动画
     *
     * @param animateView       动画View
     * @param startLocation     动画开始位置
     * @param endLocation       动画结束位置
     * @param duration          动画时长
     * @param otherAnimationSet 其他动画集合
     * @param listener          动画回调接口
     */
    public static void runTrackAnimationOnTempView(final View animateView, final int[] startLocation, final int[] endLocation
            , final long duration, final AnimationSet otherAnimationSet, Animation.AnimationListener listener) {
        Activity currentActivity = ActivityManager.instance().getCurrentActivity();
        if (currentActivity != null) {
            final ViewGroup decorView = (ViewGroup) currentActivity.getWindow().getDecorView();
            decorView.addView(animateView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            int[] originLoc = new int[2];
            animateView.getLocationOnScreen(originLoc);

            Animation translateAnimation = buildTranslateAnimation(startLocation[0] - originLoc[0],
                    endLocation[0] - originLoc[0], startLocation[1] - originLoc[1], endLocation[1] - originLoc[1], duration);
            otherAnimationSet.addAnimation(translateAnimation);

            runAnimationOnTempView(animateView, decorView, otherAnimationSet, listener);
        }
    }

    /**
     * new一个临时的view运行动画，动画完毕移除此view
     *
     * @param animateView 动画View
     * @param animation   动画实例
     */
    public static void runAnimationOnTempView(final View animateView, Animation animation) {
        runAnimationOnTempView(animateView, animation, null);
    }

    /**
     * new一个临时的view运行动画，动画完毕移除此view
     *
     * @param tempAnimateView 动画View
     * @param animation       动画实例
     * @param listener        动画回调接口
     */
    public static void runAnimationOnTempView(final View tempAnimateView, Animation animation, Animation.AnimationListener listener) {
        Activity currentActivity = ActivityManager.instance().getCurrentActivity();
        if (currentActivity != null) {
            final ViewGroup decorView = (ViewGroup) currentActivity.getWindow().getDecorView();
            decorView.addView(tempAnimateView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            runAnimationOnTempView(tempAnimateView, decorView, animation, listener);
        }
    }

    private static void runAnimationOnTempView(final View animateView, final ViewGroup viewGroup
            , Animation animation, final Animation.AnimationListener listener) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (listener != null) {
                    listener.onAnimationStart(animation);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /** 不能直接remove掉animateView，否则ViewGroup的dispatchDraw方法会报空指针错误，具体原因不明 **/
                viewGroup.post(new Runnable() {
                    @Override
                    public void run() {
                        viewGroup.removeView(animateView);
                    }
                });

                if (listener != null) {
                    listener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (listener != null) {
                    listener.onAnimationRepeat(animation);
                }
            }
        });
        animateView.startAnimation(animation);
    }
}
