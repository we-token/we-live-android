package show.we.lib.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.utils.InputMethodUtils;

/**
 * 检测软键盘事件ViewGroup，点击软键盘区域以外自动关闭软键盘
 * @author ll
 * @version 1.0.0
 */
public class DetectSoftInputEventFrameLayout extends FrameLayout {

    private int mUsableHeightPrevious;
    private InputMethodUtils.OnSoftInputEventListener mOnSoftInputEventListener;
    private boolean mCloseSoftInputOnTouchOutside = true;
    private boolean mInterceptTouchEvent = false;

    /**
     * @param context context
     */
    public DetectSoftInputEventFrameLayout(Context context) {
        this(context, null);
    }

    /**
     * @param context context
     * @param attrs attrs
     */
    public DetectSoftInputEventFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public DetectSoftInputEventFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        registerDetect();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registerDetect();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterDetect();
    }

    /**
     * 监听软键盘弹出
     */
    public void registerDetect() {
        removeCallbacks(mDetectSoftInputEventRunnable);
        post(mDetectSoftInputEventRunnable);
    }

    /**
     * 取消监听
     */
    public void unregisterDetect() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(mListener);
        }
        removeCallbacks(mDetectSoftInputEventRunnable);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (InputMethodUtils.isInputMethodShowing() && getContext() instanceof Activity && mCloseSoftInputOnTouchOutside) {
            InputMethodUtils.hideSoftInput((Activity)getContext());
        }
        return mInterceptTouchEvent || super.onTouchEvent(event);
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != mUsableHeightPrevious) {
            int usableHeightSansKeyboard = getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard / 4)) {
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.INPUT_METHOD_OPENED);
                InputMethodUtils.setIsInputMethodShowing(true);
                if (mOnSoftInputEventListener != null) {
                    mOnSoftInputEventListener.onSoftInputOpened();
                }
            } else {
                DataChangeNotification.getInstance().notifyDataChanged(IssueKey.INPUT_METHOD_CLOSED);
                InputMethodUtils.setIsInputMethodShowing(false);
                if (mOnSoftInputEventListener != null) {
                    mOnSoftInputEventListener.onSoftInputClosed();
                }
            }
            mUsableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }

    private final Runnable mDetectSoftInputEventRunnable = new Runnable() {
        @Override
        public void run() {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getViewTreeObserver().removeOnGlobalLayoutListener(mListener);
            }
            getViewTreeObserver().addOnGlobalLayoutListener(mListener);
        }
    };

    private final ViewTreeObserver.OnGlobalLayoutListener mListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            possiblyResizeChildOfContent();
        }
    };

    /**
     * 设置软键盘事件监听
     * @param listener listener
     */
    public void setOnSoftInputEventListener(InputMethodUtils.OnSoftInputEventListener listener) {
        mOnSoftInputEventListener = listener;
    }

    /**
     * 点击软键盘外部区域是否关闭软键盘
     * @param closeSoftInputOnTouchOutside closeSoftInputOnTouchOutside
     */
    public void canCloseSoftInputOnTouchOutside(boolean closeSoftInputOnTouchOutside) {
        mCloseSoftInputOnTouchOutside = closeSoftInputOnTouchOutside;
    }

    /**
     * 点击软键盘外部区域是否拦截点击事件
     * @param interceptTouchEvent interceptTouchEvent
     */
    public void interceptTouchEvent(boolean interceptTouchEvent) {
        mInterceptTouchEvent = interceptTouchEvent;
    }
}
