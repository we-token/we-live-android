package show.we.lib.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import show.we.lib.widget.animation.AnimationFactory;

/**
 * Created by CG on 14-4-3.
 * 1. 成对使用
 * 2. 2.3版本下ParentView为FrameLayout时,有布局的动画会出问题，改为RelativeLayout.
 * @author ll
 * @version 3.7.0
 */
public class ToggleAnimationUtils {

    private static final long ANIMATION_DURATION = 250;

    /**
     * toggleTopViewWithAnimationAfterLayout
     * @param view view
     */
    public static void toggleTopViewWithAnimationAfterLayout(View view) {
        toggleTopViewWithAnimationAfterLayout(view, view.getHeight());
    }

    /**
     * toggleTopViewWithAnimationAfterLayout
     * @param view view
     * @param move move
     */
    public static void toggleTopViewWithAnimationAfterLayout(View view, int move) {
        move = Math.abs(move);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        if (layoutParams == null || move == 0) {
            return;
        }
        boolean show = layoutParams.topMargin != 0;
        layoutParams.setMargins(0, show ? 0 : -move, 0, 0);
        view.requestLayout();

        // 运行动画
        int startY = show ? -move : move;
        view.startAnimation(AnimationFactory.buildTranslateAnimation(0, 0, startY, 0, ANIMATION_DURATION, true));
    }

    /**
     * toggleBottomViewWithAnimationAfterLayout
     * @param view view
     */
    public static void toggleBottomViewWithAnimationAfterLayout(View view) {
        toggleBottomViewWithAnimationAfterLayout(view, view.getHeight());
    }

    /**
     * toggleBottomViewWithAnimationAfterLayout
     * @param view view
     * @param move move
     */
    public static void toggleBottomViewWithAnimationAfterLayout(View view, int move) {
        move = Math.abs(move);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        if (layoutParams == null || move == 0) {
            return;
        }
        boolean show = layoutParams.bottomMargin != 0;
        layoutParams.setMargins(0, 0, 0, show ? 0 : -move);
        view.requestLayout();

        // 运行动画
        int startY = show ? move : -move;
        view.startAnimation(AnimationFactory.buildTranslateAnimation(0, 0, startY, 0, ANIMATION_DURATION, true));
    }

    /**
     * toggleTopViewWithAnimationBeforeLayout
     * @param view view
     */
    public static void toggleTopViewWithAnimationBeforeLayout(View view) {
        toggleTopViewWithAnimationBeforeLayout(view, view.getHeight());
    }

    /**
     * toggleTopViewWithAnimationBeforeLayout
     * @param view view
     * @param move move
     */
    public static void toggleTopViewWithAnimationBeforeLayout(final View view, int move) {
        final int absMove = Math.abs(move);
        final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        if (layoutParams == null || absMove == 0) {
            return;
        }

        final boolean show = layoutParams.topMargin != 0;

        // 运行动画
        int endY = show ? absMove : -absMove;
        TranslateAnimation translateAnimation = AnimationFactory.buildTranslateAnimation(0, 0, 0, endY, ANIMATION_DURATION, false);
        translateAnimation.setFillBefore(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                layoutParams.setMargins(0, show ? 0 : -absMove, 0, 0);
                view.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(translateAnimation);
    }

    /**
     * toggleBottomViewWithAnimationBeforeLayout
     * @param view view
     */
    public static void toggleBottomViewWithAnimationBeforeLayout(View view) {
        toggleBottomViewWithAnimationBeforeLayout(view, view.getHeight());
    }

    /**
     * toggleBottomViewWithAnimationBeforeLayout
     * @param view view
     * @param move move
     */
    public static void toggleBottomViewWithAnimationBeforeLayout(final View view, int move) {
        final int absMove = Math.abs(move);
        final ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        if (layoutParams == null || absMove == 0) {
            return;
        }

        final boolean show = layoutParams.bottomMargin != 0;

        // 运行动画
        int endY = show ? -absMove : absMove;
        TranslateAnimation translateAnimation = AnimationFactory.buildTranslateAnimation(0, 0, 0, endY, ANIMATION_DURATION, false);
        translateAnimation.setFillBefore(true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                layoutParams.setMargins(0, 0, 0, show ? 0 : -absMove);
                view.requestLayout();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(translateAnimation);
    }

    /**
     * toggleTopViewWithAnimation
     * @param view view
     */
    public static void toggleTopViewWithAnimation(final View view) {
        toggleTopViewWithAnimation(view, view.getHeight());
    }

    /**
     * toggleTopViewWithAnimation
     * @param view view
     * @param move move
     */
    public static void toggleTopViewWithAnimation(View view, int move) {
        if (move == 0) {
            return;
        }
        int absMove = Math.abs(move);
        boolean show = view.getVisibility() != View.VISIBLE;
        int startY = show ? -absMove : 0;
        int endY = show ? 0 : -absMove;
        toggleBottomViewWithAnimation(view, startY, endY, show);
    }

    /**
     * toggleBottomViewWithAnimation
     * @param view view
     */
    public static void toggleBottomViewWithAnimation(final View view) {
        toggleBottomViewWithAnimation(view, view.getHeight());
    }

    /**
     * toggleBottomViewWithAnimation
     * @param view view
     * @param move move
     */
    public static void toggleBottomViewWithAnimation(View view, int move) {
        if (move == 0) {
            return;
        }
        int absMove = Math.abs(move);
        boolean show = view.getVisibility() != View.VISIBLE;
        int startY = show ? absMove : 0;
        int endY = show ? 0 : absMove;
        toggleBottomViewWithAnimation(view, startY, endY, show);
    }

    private static void toggleBottomViewWithAnimation(final View view, int startY, int endY, final boolean show) {
        // 运行动画
        TranslateAnimation translateAnimation = AnimationFactory.buildTranslateAnimation(0, 0, startY, endY, ANIMATION_DURATION, true);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(translateAnimation);
    }
}
