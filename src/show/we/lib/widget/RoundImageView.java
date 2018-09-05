package show.we.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import show.we.lib.R;

/**
 * 圆角ImageView控件
 *
 * @author ll
 * @version 1.0.0
 */
public class RoundImageView extends ImageView {
    private int mXRadius;
    private int mYRadius;

    /**
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * @param context context
     * @param attrs   attrs
     */
    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * @param context context
     */
    public RoundImageView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundAngleImageView);
            mXRadius = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_xRadius, mXRadius);
            mYRadius = a.getDimensionPixelSize(R.styleable.RoundAngleImageView_yRadius, mYRadius);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        if (mXRadius == 0) {
            mXRadius = width / 2;
        }
        if (mYRadius == 0) {
            mYRadius = height / 2;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (!(drawable instanceof BitmapDrawable)) {
            return;
        }
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        if (bitmapDrawable == null) {
            bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.default_user_round_bg);
        }
        BitmapShader shader = new BitmapShader(bitmapDrawable.getBitmap(),
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        RectF rect = new RectF(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom);
        int bitmapWidth = bitmapDrawable.getBitmap().getWidth();
        int bitmapHeight = bitmapDrawable.getBitmap().getHeight();
        RectF src = new RectF(0.0f, 0.0f, bitmapWidth, bitmapHeight);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(src, rect, Matrix.ScaleToFit.CENTER);
        shader.setLocalMatrix(matrix);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        canvas.drawRoundRect(rect, mXRadius, mYRadius, paint);
    }

    /**
     * 设置圆角弧度
     *
     * @param x 宽
     * @param y 高
     */
    public void setCornerRadius(int x, int y) {
        mXRadius = x;
        mYRadius = y;
    }
}
