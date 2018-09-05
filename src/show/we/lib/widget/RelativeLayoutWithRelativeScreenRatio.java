package show.we.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import show.we.lib.R;
import show.we.lib.utils.DisplayUtils;

/**
 * 使用比例的相对布局
 *
 * @author ll
 * @version 1.0.0
 */
public class RelativeLayoutWithRelativeScreenRatio extends RelativeLayout {

    private static final float DEFAULT_RATIO = 4.0f / 3;
    private static final int FIX_WIDTH = 0;
    private static final int FIX_HEIGHT = 1;
    private static final int AUTO = 2;

    private float mRatio = DEFAULT_RATIO;
    private float mRelativeWidth;
    private float mRelativeHeight;
    private int mOrientation = FIX_WIDTH;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public RelativeLayoutWithRelativeScreenRatio(Context context) {
        this(context, null);
    }

    /**
     * 构造函数
     *
     * @param context 上下文对象
     * @param attrs   属性
     */
    public RelativeLayoutWithRelativeScreenRatio(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造函数
     *
     * @param context  上下文对象
     * @param attrs    属性
     * @param defStyle 默认风格
     */
    public RelativeLayoutWithRelativeScreenRatio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RelativeLayoutWithRelativeScreenRatio);
        mOrientation = a.getInt(R.styleable.RelativeLayoutWithRelativeScreenRatio_orientation, FIX_WIDTH);
        mRatio = a.getFloat(R.styleable.RelativeLayoutWithRelativeScreenRatio_ratio, DEFAULT_RATIO);
        mRelativeWidth = a.getFloat(R.styleable.RelativeLayoutWithRelativeScreenRatio_relativeWidth, 0);
        mRelativeHeight = a.getFloat(R.styleable.RelativeLayoutWithRelativeScreenRatio_relativeHeight, 0);
    }

    /**
     * 设置宽度和高度的比例
     *
     * @param ratio 宽度和高度的比例
     */
    public void setWidthAndHeightRatio(float ratio) {
        mRatio = ratio;
    }

    /**
     * 设置相对屏幕宽度的比值
     *
     * @param ratio ratio
     */
    public void setRelativeScreenWidthRatio(float ratio) {
        mRelativeWidth = ratio;
    }

    /**
     * 设置相对屏幕高度的比值
     *
     * @param ratio ratio
     */
    public void setRelativeScreenHeightRatio(float ratio) {
        mRelativeHeight = ratio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRatio > 0) {
            float width = 0;
            float height = 0;
            switch (mOrientation) {
                case FIX_WIDTH:
                    width = mRelativeWidth > 0 ? (int) (DisplayUtils.getWidthPixels() * mRelativeWidth)
                            : MeasureSpec.getSize(widthMeasureSpec);
                    height = width / mRatio;
                    break;
                case FIX_HEIGHT:
                    height = mRelativeHeight > 0 ? (int) (DisplayUtils.getHeightPixels() * mRelativeHeight)
                            : MeasureSpec.getSize(heightMeasureSpec);
                    width = height * mRatio;
                    break;
                case AUTO:
                    width = mRelativeWidth > 0 ? (int) (DisplayUtils.getWidthPixels() * mRelativeWidth)
                            : MeasureSpec.getSize(widthMeasureSpec);
                    height = mRelativeHeight > 0 ? (int) (DisplayUtils.getHeightPixels() * mRelativeHeight)
                            : MeasureSpec.getSize(heightMeasureSpec);
                    width = Math.min(width, height);
                    height = Math.min(width, height);
                    break;
                default:
                    break;
            }
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) width, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
