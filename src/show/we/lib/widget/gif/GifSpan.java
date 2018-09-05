package show.we.lib.widget.gif;

import android.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * GifSpan
 *
 * @author ll
 * @version 1.0.0
 */
public class GifSpan extends DynamicDrawableSpan {
    private Context mContext;
    private WeakReference<View> mAttachViewRef;
    private boolean mShowFirstFrameOnly = false;
    private int mWidth;
    private int mHeight;

    private List<Frame> mFrameList;
    private int mCurFrameIndex = 0;

    /**
     * GifSpan构造函数
     *
     * @param context            上下文
     * @param resId              Gif文件的资源ID
     * @param attachView         GifSpan所附着的View对象
     * @param verticalAlignment  水平对齐
     * @param showFirstFrameOnly 是否只显示第一帧
     * @param width              宽度
     * @param height             高度
     */
    public GifSpan(Context context, int resId, View attachView, int verticalAlignment, boolean showFirstFrameOnly, int width, int height) {
        super(verticalAlignment);
        mFrameList = new ArrayList<Frame>();
        try {
            init(context, attachView, showFirstFrameOnly, width, height);
            new GifParser().doParse(context.getResources().openRawResource(resId), mFrameList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * GifSpan构造函数
     *
     * @param context            上下文
     * @param frameList          Gif帧数据列表
     * @param attachView         GifSpan所附着的View对象
     * @param verticalAlignment  水平对齐
     * @param showFirstFrameOnly 是否只显示第一帧
     * @param width              宽度
     * @param height             高度
     */
    public GifSpan(Context context, List<Frame> frameList, View attachView, int verticalAlignment, boolean showFirstFrameOnly, int width, int height) {
        super(verticalAlignment);
        mFrameList = frameList;
        init(context, attachView, showFirstFrameOnly, width, height);
    }

    private void init(Context context, View attachView, boolean showFirstFrameOnly, int width, int height) {
        mContext = context;
        mWidth = width;
        mHeight = height;
        mAttachViewRef = new WeakReference<View>(attachView);
        mShowFirstFrameOnly = showFirstFrameOnly;
    }

    private long mPreDrawTimeStamp;

    @Override
    public Drawable getDrawable() {
        if (mAttachViewRef != null && mAttachViewRef.get() != null && mCurFrameIndex >= 0 && mFrameList != null && mFrameList.size() > 0) {
            Frame curFrame = mFrameList.get(mCurFrameIndex);
            if (curFrame != null && mShowFirstFrameOnly) {
                return createDrawable(curFrame.getBitmap());
            }

            long delay = System.currentTimeMillis() - mPreDrawTimeStamp;

            if (curFrame == null || delay > curFrame.getDuration()) {
                mPreDrawTimeStamp = System.currentTimeMillis();
                Drawable drawable = getNextDrawable();
                if (curFrame != null) {
                    mAttachViewRef.get().postInvalidateDelayed(curFrame.getDuration());
                }
                return drawable;
            } else {
                mAttachViewRef.get().postInvalidateDelayed(curFrame.getDuration() - delay);
                return createDrawable(curFrame.getBitmap());
            }
        }

        return getDefaultDrawable();
    }

    private Drawable getDefaultDrawable() {
        Drawable drawable = new ColorDrawable(R.color.transparent);
        drawable.setBounds(0, 0, mWidth, mHeight);
        return drawable;
    }

    private Drawable getNextDrawable() {
        if (mFrameList.size() > 0 && (mCurFrameIndex <= mFrameList.size() - 1)) {
            mCurFrameIndex = (mCurFrameIndex + 1) % mFrameList.size();
            return createDrawable(mFrameList.get(mCurFrameIndex).getBitmap());
        }

        return getDefaultDrawable();
    }

    private Drawable createDrawable(Bitmap bitmap) {
        if (bitmap == null) {
            return getDefaultDrawable();
        }
        Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
        drawable.setBounds(0, 0, mWidth, mHeight);
        return drawable;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable b = getDrawable();
        canvas.save();

        int transY = bottom - b.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= paint.getFontMetricsInt().descent / 2;
        }

        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
