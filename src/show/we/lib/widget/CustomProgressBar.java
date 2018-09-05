package show.we.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import show.we.lib.R;

/**
 * @author ll
 * @version 1.0.0
 */
public class CustomProgressBar extends View {

    private float mRatio;
    private boolean mIsVertical;
    private Drawable mProgressDrawable;

    /**
     * Constructor
     *
     * @param context context
     */
    public CustomProgressBar(Context context) {
        this(context, null);
    }

    /**
     * Constructor
     *
     * @param context context
     * @param attrs   attrs
     */
    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyle, 0);
            int drawableId = ta.getResourceId(R.styleable.CustomProgressBar_progressDrawable, 0);
            if (drawableId != 0) {
                mProgressDrawable = getResources().getDrawable(drawableId);
            }
            ta.recycle();
        }
    }

    /**
     * 设置进度比例
     *
     * @param ratio 进度比例
     */
    public void setRatio(float ratio) {
        mRatio = Math.max(0, Math.min(1.0f, ratio));
        invalidate();
    }

    /**
     * 获取进度比例
     *
     * @return 进度比例
     */
    public float getRatio() {
        return mRatio;
    }

    /**
     * setIsVertical
     *
     * @param isVertical isVertical
     */
    public void setIsVertical(boolean isVertical) {
        mIsVertical = isVertical;
    }

    /**
     * isVertical
     *
     * @return isVertical
     */
    public boolean isVertical() {
        return mIsVertical;
    }

    /**
     * setProgressDrawable
     *
     * @param progressDrawable progressDrawable
     */
    public void setProgressDrawable(Drawable progressDrawable) {
        mProgressDrawable = progressDrawable;
    }

    /**
     * getProgressDrawable
     *
     * @return mProgressDrawable
     */
    public Drawable getProgressDrawable() {
        return mProgressDrawable;
    }

    @Override
    protected void drawableStateChanged() {
        Drawable d = mProgressDrawable;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
        super.drawableStateChanged();
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mProgressDrawable || super.verifyDrawable(who);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mProgressDrawable != null) {
            Rect rect = new Rect(0, 0, mProgressDrawable.getMinimumWidth(), mProgressDrawable.getMinimumHeight());
            if (mIsVertical) {
                int top = getPaddingTop();
                int height = (int) ((getHeight() - top - getPaddingBottom() - rect.height()) * mRatio);
                int bottom = top + height + rect.height();
                rect.set(rect.left, top, rect.right, bottom);
                rect.offset(getWidth() / 2 - rect.width() / 2, 0);
            } else {
                int left = getPaddingLeft();
                int width = (int) ((getWidth() - left - getPaddingRight() - rect.width()) * mRatio);
                int right = left + width + rect.width();
                rect.set(left, rect.top, right, rect.bottom);
                rect.offset(0, getHeight() / 2 - rect.height() / 2);
            }
            mProgressDrawable.setBounds(rect);
            mProgressDrawable.draw(canvas);
        }
    }
}
