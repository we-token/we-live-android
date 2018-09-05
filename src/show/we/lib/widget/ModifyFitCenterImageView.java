package show.we.lib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 修改ImageView的fitCenter行为
 *
 * @author ll
 * @version 2.0.0
 * @since 2012-9-4
 */
public class ModifyFitCenterImageView extends ImageView {

    private boolean mScaleImageByHeight;

    /**
     * 构造器
     *
     * @param context 上下文对象
     */
    public ModifyFitCenterImageView(Context context) {
        super(context);
    }

    /**
     * 构造器
     *
     * @param context 上下文对象
     * @param attrs   属性值集合
     */
    public ModifyFitCenterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 构造器
     *
     * @param context  上下文对象
     * @param attrs    属性值集合
     * @param defStyle 风格
     */
    public ModifyFitCenterImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 按照高度等比例拉伸图片
     *
     * @param scaleByHeight 是否按照高度等比例拉伸图片
     */
    public void setScaleByHeight(boolean scaleByHeight) {
        mScaleImageByHeight = scaleByHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mScaleImageByHeight) {
            Drawable d = getDrawable();
            if (d != null) {
                int drawableWidth = d.getIntrinsicWidth();
                int drawableHeight = d.getIntrinsicHeight();
                if (drawableWidth > 0 && drawableHeight > 0) {
                    int viewWidth = getMeasuredWidth();
                    int viewHeight = getMeasuredHeight();
                    int showHeight = viewHeight - getPaddingTop() - getPaddingBottom();
                    int showWidth = showHeight * drawableWidth / drawableHeight;
                    int needViewWidth = showWidth + getPaddingLeft() + getPaddingRight();
                    if (viewWidth != needViewWidth) {
                        viewWidth = needViewWidth;
                        setMeasuredDimension(viewWidth, viewHeight);
                    }
                }
            }
        } else {
            if (getScaleType() == ScaleType.FIT_CENTER) {
                Drawable d = getDrawable();
                if (d != null) {
                    int drawableWidth = d.getIntrinsicWidth();
                    int drawableHeight = d.getIntrinsicHeight();
                    if (drawableWidth > 0 && drawableHeight > 0) {
                        int viewWidth = getMeasuredWidth();
                        int viewHeight = getMeasuredHeight();
                        int showWidth = viewWidth - getPaddingLeft() - getPaddingRight();
                        int showHeight = showWidth * drawableHeight / drawableWidth;
                        int needViewHeight = showHeight + getPaddingTop() + getPaddingBottom();
                        if (viewHeight != needViewHeight) {
                            viewHeight = needViewHeight;
                            setMeasuredDimension(viewWidth, viewHeight);
                        }
                    }
                }
            }
        }
    }
}
