/**
 * @(#)WeightedLinearLayout       2011-10-22
 * Copyright (c) 2007-2011 Shanghai ShuiDuShi Co.Ltd. All right reserved.
 */

package show.we.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import static android.view.View.MeasureSpec.*;

/**
 * A special layout when measured in AT_MOST will take up a given percentage of
 * the available space.
 *
 * @author zhan.liu
 * @version 3.0.0
 * @since 2011-10-22
 */
public class WeightedLinearLayout extends LinearLayout {
    private static final float WEIGHT_MAJOR = 0.85f;
    private static final float WEIGHT_MINOR = 0.9f;

    /**
     * 构造函数
     *
     * @param context Context
     */
    public WeightedLinearLayout(Context context) {
        super(context);
    }

    /**
     * 构造函数
     *
     * @param context Context
     * @param attrs   属性值
     */
    public WeightedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;
        final boolean isPortrait = screenWidth < screenHeight;

        final int widthMode = getMode(widthMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        boolean measure = false;

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, EXACTLY);

        final float widthWeight = isPortrait ? WEIGHT_MINOR : WEIGHT_MAJOR;
        final float heightWeight = isPortrait ? WEIGHT_MAJOR : WEIGHT_MINOR;
        if (widthMode == AT_MOST) {
            if (widthWeight > 0.0f) {
                screenWidth *= widthWeight;
                if (width > screenWidth) {
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, EXACTLY);
                    measure = true;
                }
            }
            if (heightWeight > 0.0f) {
                screenHeight *= heightWeight;
                if (height > screenHeight) {
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(screenHeight, EXACTLY);
                    measure = true;
                }
            }
        }

        if (measure) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
