package show.we.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by CG on 14-1-8.
 *
 * @author ll
 * @version 3.4.0
 */
public class ContainTailLayout extends ViewGroup {

    /**
     * ContainTailLayout
     *
     * @param context context
     */
    public ContainTailLayout(Context context) {
        super(context);
    }

    /**
     * ContainTailLayout
     *
     * @param context context
     * @param attrs   attrs
     */
    public ContainTailLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() != 2) {
            throw new IllegalArgumentException("must be contains double children.");
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        View child1 = getChildAt(0);
        View child2 = getChildAt(1);
        int height = Math.max(child1.getMeasuredHeight(), child2.getMeasuredHeight());
        setMeasuredDimension(width, height);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int child1Width = child1.getMeasuredWidth();
        if (child1Width + child2.getMeasuredWidth() + paddingLeft + paddingRight > width) {
            child1Width = width - paddingLeft - paddingRight - child2.getMeasuredWidth();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(child1Width, MeasureSpec.EXACTLY);
            measureChild(child1, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child1 = getChildAt(0);
        View child2 = getChildAt(1);
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int child1Width = child1.getMeasuredWidth();
        if (child1Width + child2.getMeasuredWidth() + paddingLeft + paddingRight > width) {
            child1Width = width - paddingLeft - paddingRight - child2.getMeasuredWidth();
        }
        int centerVertical = (height - paddingTop - paddingBottom) / 2 + paddingTop;
        layoutChild(child1, paddingLeft, child1Width, centerVertical);
        layoutChild(child2, paddingLeft + child1Width, child2.getMeasuredWidth(), centerVertical);
    }

    private void layoutChild(View view, int left, int length, int centerVertical) {
        int right = left + length;
        int top = centerVertical - view.getMeasuredHeight() / 2;
        int bottom = centerVertical + view.getMeasuredHeight() / 2;
        view.layout(left, top, right, bottom);
    }
}
