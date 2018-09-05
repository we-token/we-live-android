package show.we.lib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.HashSet;

/**
 * @author ll
 * @version 1.0.0
 */
public class SlideClosableRelativeLayout extends RelativeLayout {

    private static final boolean USE_CACHE = false;
    private static final int VELOCITY_UNIT = 1000;

    /**
     * Indicates that the layout is in an idle, settled state. The current page
     * is fully in view and no animation is in progress.
     */
    protected static final int SCROLL_STATE_IDLE = 0;

    /**
     * Indicates that the layout is currently being dragged by the user.
     */
    protected static final int SCROLL_STATE_DRAGGING = 1;

    /**
     * Indicates that the layout is in the process of settling to a final position.
     */
    protected static final int SCROLL_STATE_SETTLING = 2;

    /**
     * Indicates that the layout is int the process of closing itself
     */
    protected static final int SCROLL_STATE_CLOSE = 3;

    /**
     * Cannot scroll by user
     */
    public static final int SLIDING_CLOSE_MODE_NONE = 0;

    /**
     * Can be dragged scroll from left horizontally.
     */
    public static final int SLIDING_CLOSE_MODE_HORIZONTAL_LEFT = 1;

    /**
     * Can be dragged scroll from right horizontally.
     */
    public static final int SLIDING_CLOSE_MODE_HORIZONTAL_RIGHT = 2;

    /**
     * Can be dragged scroll from top vertically
     */
    public static final int SLIDING_CLOSE_MODE_VERTICAL_TOP = 4;

    /**
     * Can be dragged scroll from bottom vertically
     */
    public static final int SLIDING_CLOSE_MODE_VERTICAL_BOTTOM = 8;

    /**
     *
     */
    public static final int SLIDING_CLOSE_MODE_HORIZONTAL = SLIDING_CLOSE_MODE_HORIZONTAL_LEFT | SLIDING_CLOSE_MODE_HORIZONTAL_RIGHT;

    /**
     *
     */
    public static final int SLIDING_CLOSE_MODE_VERTICAL = SLIDING_CLOSE_MODE_VERTICAL_TOP | SLIDING_CLOSE_MODE_VERTICAL_BOTTOM;

    /**
     *
     */
    public static final int SLIDING_CLOSE_MODE_BOTH_SIDE = SLIDING_CLOSE_MODE_HORIZONTAL | SLIDING_CLOSE_MODE_VERTICAL;

    private static final int MOTION_STATE_UNAVAILABLE = -1;
    private static final int MOTION_STATE_NONE = 0;
    private static final int MOTION_STATE_OPENING = 1;
    private static final int MOTION_STATE_CLOSING = 2;
    // If the pager is at least this close to its final position, complete the scroll
    // on touch down and let the user interact with the content inside instead of
    // "catching" the flinging pager.
    private static final int CLOSE_ENOUGH = 2; // dp
    private static final int MIN_DISTANCE_FOR_FLING = 80; // dips
    private static final int DEFAULT_SHADOW_WIDTH = 5;
    private static final int DEFAULT_TOUCH_MARGIN_WIDTH = 5;

    private static final int MAX_SETTLE_DURATION = 600; // ms

    private static final Interpolator INTERPOLATOR = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private int mScrollState = SCROLL_STATE_IDLE;

    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private int mTouchSlop;

    private float mInitialMotionX;
    private float mInitialMotionY;
    private Scroller mScroller;

    private boolean mScrollingCacheEnabled;

    private int mSlidingCloseMode = SLIDING_CLOSE_MODE_HORIZONTAL_LEFT | SLIDING_CLOSE_MODE_HORIZONTAL_RIGHT;

    /**
     * Position of the last motion event.
     */
    private float mLastMotionX;
    private float mLastMotionY;
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    protected int mActivePointerId = INVALID_POINTER;
    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mFlingDistance;
    private int mCloseEnough;

    private Drawable mLeftShadowDrawable;
    private Drawable mTopShadowDrawable;
    private Drawable mRightShadowDrawable;
    private Drawable mBottomShadowDrawable;
    private int mShadowWidth;
    private int mTouchMarginWidth;
    private int mMotionState = MOTION_STATE_NONE;
    private int mInitOpenState = MOTION_STATE_NONE;
    private boolean mIsOpened = true;
    private boolean mIsClosed = false;

    private OnSlidingOpenListener mSlidingOpenListener;
    private OnSlidingCloseListener mSlidingCloseListener;
    private OnSlidingScrollListener mSlidingScrollListener;

    private Bitmap mChildCacheBitmap;

    private Rect mSlidingOpenHitRect = new Rect();

    private boolean mNeedLayout = false;
    private boolean mEnableMarginOpen = true;

    private HashSet<View> mIgnoredViewSet = new HashSet<View>();
    /**
     * @param context android mContext
     * @see android.view.View#View(android.content.Context)
     */
    public SlideClosableRelativeLayout(Context context) {
        super(context);
        init();
    }

    /**
     * @param context android mContext
     * @param attrs   attribute set
     * @see android.view.View#View(android.content.Context, android.util.AttributeSet)
     */
    public SlideClosableRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context  android mContext
     * @param attrs    attribute set
     * @param defStyle default style
     * @see android.view.View#View(android.content.Context, android.util.AttributeSet, int)
     */
    public SlideClosableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        setWillNotDraw(true);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        final Context context = getContext();
        if (context == null) {
            throw new NullPointerException("view has not attach any view tree");
        }
        mScroller = new Scroller(context, INTERPOLATOR);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

        final float density = context.getResources().getDisplayMetrics().density;
        mFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
        mCloseEnough = (int) (CLOSE_ENOUGH * density);
        mShadowWidth = (int) (DEFAULT_SHADOW_WIDTH * density);
        mTouchMarginWidth = (int) (DEFAULT_TOUCH_MARGIN_WIDTH * density);

        if (ViewCompat.getImportantForAccessibility(this) == ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
        }
    }

    /**
     * Set sliding open start hit box
     *
     * @param left  left
     * @param top   top
     * @param right right
     * @param down  down
     */
    public void setSlidingOpenHitRect(int left, int top, int right, int down) {
        mSlidingOpenHitRect.set(left, top, right, down);
    }

    /**
     * @return true if yes
     */
    public boolean hasSlidingOpenHitRect() {
        return !mSlidingOpenHitRect.isEmpty();
    }

    /**
     * Sets the listener that receives a notification when the drawer becomes open.
     *
     * @param onSlidingOpenListener The listener to be notified when the drawer is opened.
     */
    public void setOnSlidingOpenListener(OnSlidingOpenListener onSlidingOpenListener) {
        mSlidingOpenListener = onSlidingOpenListener;
    }

    /**
     * Sets the listener that receives a notification when this view's content becomes close.
     *
     * @param onSlidingCloseListener The listener to be notified when the drawer is closed.
     */
    public void setOnSlidingCloseListener(OnSlidingCloseListener onSlidingCloseListener) {
        mSlidingCloseListener = onSlidingCloseListener;
    }

    /**
     * Sets enable open closed panel from screen margin
     * @param enableMarginOpen enable
     */
    public void setEnableMarginOpen(boolean enableMarginOpen) {
        mEnableMarginOpen = enableMarginOpen;
    }

    /**
     * Sets the listener that receives a notification when the drawer starts or ends
     * a scroll. A fling is considered as a scroll. A fling will also trigger a
     * drawer opened or drawer closed event.
     *
     * @param onSlidingScrollListener The listener to be notified when scrolling
     *                                starts or stops.
     */
    public void setOnSlidingScrollListener(OnSlidingScrollListener onSlidingScrollListener) {
        mSlidingScrollListener = onSlidingScrollListener;
    }

    private void setLastAbsoluteMotion(MotionEvent ev, int pointerId) {
        int pointerIndex = MotionEventCompat.findPointerIndex(ev, pointerId);
        mLastMotionX = MotionEventCompat.getX(ev, pointerIndex);
        mLastMotionY = MotionEventCompat.getY(ev, pointerIndex);
    }

    private void setInitialMotion() {
        mInitialMotionX = mLastMotionX;
        mInitialMotionY = mLastMotionY;
    }

    private void setScrollState(int newState) {
        if (mScrollState != newState) {
            mScrollState = newState;
        }
    }

    private void setMotionState(int newState) {
        if (mMotionState != newState) {
            mMotionState = newState;
        }
    }

    /**
     * 是否是正在打开状态
     * @return true if yes
     */
    public boolean isOpening() {
        return mMotionState == MOTION_STATE_OPENING;
    }

    /**
     * 是否是正在关闭状态
     * @return true if yes
     */
    public boolean isClosing() {
        return mMotionState == MOTION_STATE_CLOSING;
    }

    /**
     * 是否是已经关闭状态
     * @return true if yes
     */
    public boolean isClosed() {
        return mIsClosed;
    }

    /**
     * reset
     */
    public void reset() {
        mScroller.abortAnimation();
        setScrollState(SCROLL_STATE_IDLE);
        scrollTo(0, 0);
    }

    private void enableLayers(boolean enable) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int layerType = enable ? ViewCompat.LAYER_TYPE_HARDWARE : ViewCompat.LAYER_TYPE_NONE;
            ViewCompat.setLayerType(getChildAt(i), layerType, null);
        }
    }

    /**
     * Set sliding close mode
     *
     * @param slidingCloseMode type
     */
    public void setSlidingCloseMode(int slidingCloseMode) {
        mSlidingCloseMode = slidingCloseMode;
        int leftShadow = 0, topShadow = 0, rightShadow = 0, bottomShadow = 0;
        /*if (isSlidingCloseModeHorizontal()) {
            leftShadow = R.drawable.right_shadow;
            rightShadow = R.drawable.left_shadow;
        }

        if (isSlidingCloseModeVertical()) {
            topShadow = R.drawable.bottom_shadow;
            bottomShadow = R.drawable.top_shadow;
        }*/
        setShadowDrawable(leftShadow, topShadow, rightShadow, bottomShadow);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mScrollState == SCROLL_STATE_SETTLING) {
            if (changed) {
                mNeedLayout = true;
            }
            return;
        }
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            int width = r - l;
            int height = b - t;
            if (width > 0 && height > 0) {
                if (mInitOpenState == MOTION_STATE_CLOSING) {
                    resetClosePosition(false);
                    mIsOpened = false;
                } else if (mInitOpenState == MOTION_STATE_OPENING) {
                    resetOpenPosition(false);
                    mIsOpened = true;
                }
                mInitOpenState = MOTION_STATE_UNAVAILABLE;
            }
        }
    }


    /**
     * 添加IgnoredView,使该控件可以滑动
     * @param view view
     */
    public void addIgnoredView(View view) {
        mIgnoredViewSet.add(view);
    }

    /**
     * 移除IgnoredView
     * @param view   view
     */
    public void removeIgnoredView(View view) {
        mIgnoredViewSet.remove(view);
    }

    /**
     * 移除IgnoredView
     */
    public void clearIgnoredView() {
        mIgnoredViewSet.clear();
    }

    private boolean isIgnore(MotionEvent ev) {
        Rect rect = new Rect();
        for (View v : mIgnoredViewSet) {
            v.getHitRect(rect);
            if (rect.contains((int) ev.getX(), (int) ev.getY())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;
        // Always take care of the touch gesture being complete.
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the drag.
            mIsBeingDragged = false;
            mIsUnableToDrag = false;
            mActivePointerId = INVALID_POINTER;
            recycleVelocityTracker();
            return false;
        }
        // Nothing more to do here if we have decided whether or not we
        // are dragging.
        if (action != MotionEvent.ACTION_DOWN) {
            if (mIsBeingDragged) {
                return true;
            }
            if (mIsUnableToDrag) {
                return false;
            }
        }

        switch (action) {
            default:
                endDrag();
                break;
            case MotionEvent.ACTION_MOVE:
                /*
                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
                 * whether the user has moved far enough from his original down touch.
                 */

                /*
                * Locally do absolute value. mLastMotionY is set to the y value
                * of the down event.
                */
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }
                int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);

                final float x = MotionEventCompat.getX(ev, pointerIndex);
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float dx = x - mLastMotionX;
                final float dy = y - mLastMotionY;

                if (shouldDisableDrag(dx, dy) || (shouldCheckScroll(dx, dy) && canScroll(this, false, (int) dx, (int) dy, (int) x, (int) y))) {
                    // Nested view has scrollable area under this point. Let it be handled there.
                    mLastMotionX = x;
                    mLastMotionY = y;
                    setInitialMotion();
                    mIsUnableToDrag = true;
                    return false;
                }
                if (shouldStartDrag(dx, dy)) {
                    startDrag(x, y);
                }
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    if (performDrag(x, y)) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
                break;

            case MotionEvent.ACTION_DOWN:
                if (!scrollEnable(ev)) {
                    return super.onInterceptTouchEvent(ev);
                }
                if (isIgnore(ev)) {
                    mIsUnableToDrag = true;
                    return super.onInterceptTouchEvent(ev);
                }
                /*
                 * Remember location of down touch.
                 * ACTION_DOWN always refers to pointer index 0.
                 */
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                setLastAbsoluteMotion(ev, mActivePointerId);
                setInitialMotion();
                mIsUnableToDrag = false;
                mScroller.computeScrollOffset();
                if (mScrollState == SCROLL_STATE_SETTLING
                        && shouldClose(mScroller.getCurrX(), mScroller.getCurrY(), mScroller.getFinalX(), mScroller.getFinalY())) {
                    // Let the user 'catch' the pager as it animates.
                    mScroller.abortAnimation();
                    mIsBeingDragged = true;
                    setScrollState(SCROLL_STATE_DRAGGING);
                } else {
                    completeScroll();
                    mIsBeingDragged = false;
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }

        addMovementToVelocityTracker(ev);

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
        return mIsBeingDragged;
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (mScrollingCacheEnabled != enabled) {
            mScrollingCacheEnabled = enabled;
            mChildCacheBitmap = null;
            if (USE_CACHE) {
                final int size = getChildCount();
                for (int i = 0; i < size; ++i) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() != GONE) {
                        boolean needSetCache = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            needSetCache = !child.isHardwareAccelerated();
                        }
                        if (needSetCache) {
                            child.setDrawingCacheEnabled(enabled);
                        }
                    }
                }
            }
        }
    }

    private void startDrag() {
        mIsBeingDragged = true;
        notifySlidingStart();
        if (mScrollState == SCROLL_STATE_CLOSE) {
            setMotionState(MOTION_STATE_OPENING);
        }
        setScrollState(SCROLL_STATE_DRAGGING);
        setScrollingCacheEnabled(true);
    }

    private void startDrag(float currX, float currY) {
        mLastMotionX = getNewPosition(currX, mInitialMotionX);
        mLastMotionY = getNewPosition(currY, mInitialMotionY);
        startDrag();
    }

    private void resetDragState() {
        mIsBeingDragged = false;
        mActivePointerId = INVALID_POINTER;
        recycleVelocityTracker();
    }

    private void endDrag() {
        resetDragState();
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        if (scrollX != 0 || scrollY != 0) {
            if (Math.abs(scrollX) > (getWidth() >> 1) || Math.abs(scrollY) > (getHeight() >> 1)) {
                closeImpl(true);
            } else {
                setScrollState(SCROLL_STATE_SETTLING);
                mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY, MAX_SETTLE_DURATION);
                ViewCompat.postInvalidateOnAnimation(this);
            }
        } else {
            setScrollState(SCROLL_STATE_IDLE);
            notifySlidingEnd();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void addMovementToVelocityTracker(MotionEvent motion) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(motion);
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            setLastAbsoluteMotion(ev, mActivePointerId);
        }
    }

    private boolean scrollEnable(MotionEvent event) {
        if (mScrollState == SCROLL_STATE_CLOSE) {
            float x = event.getX(), y = event.getY();
            int width = getWidth(), height = getHeight();
            return mSlidingOpenHitRect.contains((int) x, (int) y)
                    || (mEnableMarginOpen
                            && (
                                (isSlidingCloseModeHorizontalLeft() && x < mTouchMarginWidth)
                                || (isSlidingCloseModeHorizontalRight() && x < width && x > width - mTouchMarginWidth)
                                || (isSlidingCloseModeVerticalTop() && y < mTouchMarginWidth)
                                || (isSlidingCloseModeVerticalBottom() && y < height && y > height - mTouchMarginWidth)
                                )
                            );
        } else {
            return mSlidingCloseMode != SLIDING_CLOSE_MODE_NONE && getChildCount() > 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mIsUnableToDrag) {
            return super.onTouchEvent(ev);
        }

        if (!scrollEnable(ev)) {
            recycleVelocityTracker();
            return super.onTouchEvent(ev);
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
            // Don't handle edge touches immediately -- they may actually belong to one of our
            // descendants.
            return false;
        }

        addMovementToVelocityTracker(ev);

        final int action = ev.getAction();
        boolean needsInvalidate = false;

        switch (action & MotionEventCompat.ACTION_MASK) {
            default:
                break;
            case MotionEvent.ACTION_DOWN:
                mScroller.abortAnimation();
                startDrag();
                // Remember where the motion event started
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                setLastAbsoluteMotion(ev, mActivePointerId);
                setInitialMotion();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsBeingDragged) {
                    final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                    final float x = MotionEventCompat.getX(ev, pointerIndex);
                    final float y = MotionEventCompat.getY(ev, pointerIndex);
                    final float xDiff = Math.abs(x - mLastMotionX);
                    final float yDiff = Math.abs(y - mLastMotionY);
                    if (shouldStartDrag(xDiff, yDiff)) {
                        startDrag(x, y);
                    }
                }
                // Not else! Note that mIsBeingDragged can be set above.
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                    final float x = MotionEventCompat.getX(ev, activePointerIndex);
                    final float y = MotionEventCompat.getY(ev, activePointerIndex);
                    needsInvalidate = performDrag(x, y);
                }
                break;
            case MotionEvent.ACTION_UP:
                final int activePointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float x = MotionEventCompat.getX(ev, activePointerIndex);
                final float y = MotionEventCompat.getY(ev, activePointerIndex);
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(VELOCITY_UNIT, mMaximumVelocity);
                    int initialVelocityX = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, mActivePointerId);
                    int initialVelocityY = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, mActivePointerId);
                    final int totalDeltaX = (int) (x - mInitialMotionX);
                    final int totalDeltaY = (int) (y - mInitialMotionY);
                    if (mSlidingOpenHitRect.contains((int) x, (int) y) && !mIsOpened) {
                        open(true);
                        resetDragState();
                    } else if (determineOpenOrClose(initialVelocityX, initialVelocityY, totalDeltaX, totalDeltaY)) {
                        resetDragState();
                    } else {
                        endDrag();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    endDrag();
                }
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, MotionEventCompat.getActionIndex(ev));
                setLastAbsoluteMotion(ev, mActivePointerId);
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                setLastAbsoluteMotion(ev, mActivePointerId);
                break;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private float getNewPosition(float curr, float initial) {
        return curr - initial > 0 ? initial + mTouchSlop : initial - mTouchSlop;
    }

    @Override
    public void computeScroll() {
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // Keep on drawing until the animation has finished.
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        // Done with scroll, clean up state.
        completeScroll();
    }

    private void completeScroll() {
        boolean needPopulate = mScrollState == SCROLL_STATE_SETTLING;
        if (needPopulate) {
            // Done with scroll, no longer want to cache view drawing.
            setScrollingCacheEnabled(false);
            mScroller.abortAnimation();
            if (mMotionState == MOTION_STATE_CLOSING
                    || Math.abs(getScrollX()) >= getWidth()
                    || Math.abs(getScrollY()) >= getHeight()) {
                setScrollState(SCROLL_STATE_CLOSE);
                if (mIsOpened) {
                    mIsOpened = false;
                    mIsClosed = true;
                    if (mSlidingCloseListener != null) {
                        mSlidingCloseListener.onSlidingClosed();
                    }
                }
            } else {
                setScrollState(SCROLL_STATE_IDLE);
                if (!mIsOpened) {
                    mIsOpened = true;
                    if (mMotionState == MOTION_STATE_OPENING && mSlidingOpenListener != null) {
                        mSlidingOpenListener.onSlidingOpened();
                    }
                }
            }
            setMotionState(MOTION_STATE_NONE);
            notifySlidingEnd();
        }
    }

    private boolean performDrag(float x, float y) {
        final float deltaX = x - mLastMotionX;
        final float deltaY = y - mLastMotionY;
        mLastMotionX = x;
        mLastMotionY = y;
        return doScrollPosition((int) deltaX, (int) deltaY);
    }

    /**
     * Tests scrollability within child views of v given a delta of dx.
     *
     * @param v      View to test for horizontal scrollability
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx     Delta x scrolled in pixels
     * @param dy     Delta y scrolled in pixels
     * @param x      X coordinate of the active touch point
     * @param y      Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    private boolean canScroll(View v, boolean checkV, int dx, int dy, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            int newX = x + scrollX, newY = y + scrollY;
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (newX >= child.getLeft() && newX < child.getRight()
                        && newY >= child.getTop() && newY < child.getBottom()
                        && canScroll(child, true, dx, dy, newX - child.getLeft(), newY - child.getTop())) {
                    return true;
                }
            }
        }
        return checkV && canScroll(v, dx, dy);
    }

    protected boolean determineOpenOrClose(int velocityX, int velocityY, int deltaX, int deltaY) {
        boolean consumed = false;
        boolean hasHorizontalDistance = Math.abs(deltaX) > mFlingDistance;
        boolean hasVerticalDistance = Math.abs(deltaY) > mFlingDistance;
        if ((hasHorizontalDistance && isSlidingCloseModeHorizontalLeft() && velocityX < -mMinimumVelocity)
                || (hasHorizontalDistance && isSlidingCloseModeHorizontalRight() && velocityX > mMinimumVelocity)
                || (hasVerticalDistance && isSlidingCloseModeVerticalTop() && velocityY < -mMinimumVelocity)
                || (hasVerticalDistance && isSlidingCloseModeVerticalBottom() && velocityY > mMinimumVelocity)) {
            closeImpl(true);
            consumed = true;
        } else if ((hasHorizontalDistance && isSlidingCloseModeHorizontal() && Math.abs(velocityX) > mMinimumVelocity)
                || (hasVerticalDistance && isSlidingCloseModeVertical() && Math.abs(velocityY) > mMinimumVelocity)) {
            openImpl(true);
            consumed = true;
        }
        return consumed;
    }

    protected boolean doScrollPosition(int deltaX, int deltaY) {
        int newScrollX = getScrollX() - deltaX;
        int newScrollY = getScrollY() - deltaY;
        if ((!isSlidingCloseModeHorizontalLeft() && newScrollX > 0)
                || (!isSlidingCloseModeHorizontalRight() && newScrollX < 0)
                || !isSlidingCloseModeHorizontal()) {
            deltaX = 0;
        }
        if ((!isSlidingCloseModeVerticalTop() && newScrollY > 0)
                || (!isSlidingCloseModeVerticalBottom() && newScrollY < 0)
                || !isSlidingCloseModeVertical()) {
            deltaY = 0;
        }
        if (deltaX != 0 || deltaY != 0) {
            scrollBy(-deltaX, -deltaY);
            return true;
        }
        return false;
    }

    protected boolean shouldClose(int currScrollX, int currScrollY, int finalScrollX, int finalScrollY) {
        if (isSlidingCloseModeHorizontal()) {
            return Math.abs(finalScrollX - currScrollX) > mCloseEnough;
        }
        return Math.abs(finalScrollY - currScrollY) > mCloseEnough;
    }

    protected boolean shouldStartDrag(float xDiff, float yDiff) {
        return (mScrollState == SCROLL_STATE_CLOSE && (Math.abs(xDiff) > mTouchSlop || Math.abs(yDiff) > mTouchSlop))
                || (isSlidingCloseModeHorizontalLeft() && xDiff < -mTouchSlop)
                || (isSlidingCloseModeHorizontalRight() && xDiff > mTouchSlop)
                || (isSlidingCloseModeVerticalTop() && yDiff < -mTouchSlop)
                || (isSlidingCloseModeVerticalBottom() && yDiff > mTouchSlop);
    }

    protected boolean shouldDisableDrag(float xDiff, float yDiff) {
        boolean horizontalMode = isSlidingCloseModeHorizontal();
        boolean verticalMode = isSlidingCloseModeVertical();
        if (horizontalMode && !verticalMode) {
            return Math.abs(yDiff) > mTouchSlop;
        } else if (!horizontalMode && verticalMode) {
            return Math.abs(xDiff) > mTouchSlop;
        }
        return !horizontalMode || !verticalMode;
    }

    protected boolean shouldCheckScroll(float dx, float dy) {
        if (isSlidingCloseModeHorizontal()) {
            return dx != 0;
        }
        return isSlidingCloseModeVertical() && dy != 0;
    }

    protected boolean canScroll(View v, int dx, int dy) {
        if (isSlidingCloseModeHorizontal()) {
            return ViewCompat.canScrollHorizontally(v, -dx);
        }
        return isSlidingCloseModeVertical() && ViewCompat.canScrollVertically(v, -dy);
    }

    /**
     * 获取滑动关闭的模式
     * @return 模式id
     */
    public int getSlidingCloseMode() {
        return mSlidingCloseMode;
    }

    /**
     * @return true if yes
     */
    public boolean isSlidingCloseModeHorizontal() {
        return isSlidingCloseModeHorizontalLeft() || isSlidingCloseModeHorizontalRight();
    }

    /**
     * @return true if yes
     */
    public boolean isSlidingCloseModeVertical() {
        return isSlidingCloseModeVerticalBottom() || isSlidingCloseModeVerticalTop();
    }

    /**
     * @return true if yes
     */
    public boolean isSlidingCloseModeHorizontalLeft() {
        return (mSlidingCloseMode & SLIDING_CLOSE_MODE_HORIZONTAL_LEFT) == SCROLLBAR_POSITION_LEFT;
    }

    /**
     * @return true if yes
     */
    public boolean isSlidingCloseModeHorizontalRight() {
        return (mSlidingCloseMode & SLIDING_CLOSE_MODE_HORIZONTAL_RIGHT) == SCROLLBAR_POSITION_RIGHT;
    }

    /**
     * @return true if yes
     */
    public boolean isSlidingCloseModeVerticalTop() {
        return (mSlidingCloseMode & SLIDING_CLOSE_MODE_VERTICAL_TOP) == SLIDING_CLOSE_MODE_VERTICAL_TOP;
    }

    /**
     * @return true if yes
     */
    public boolean isSlidingCloseModeVerticalBottom() {
        return (mSlidingCloseMode & SLIDING_CLOSE_MODE_VERTICAL_BOTTOM) == SLIDING_CLOSE_MODE_VERTICAL_BOTTOM;
    }

    /**
     * @return true if yes
     */
    public boolean isOpened() {
        return mInitOpenState != MOTION_STATE_CLOSING && mIsOpened;
    }

    /**
     * Close page
     *
     * @param animation with animation
     */
    public void close(final boolean animation) {
        setMotionState(MOTION_STATE_CLOSING);
        post(new Runnable() {
            @Override
            public void run() {
                closeImpl(animation);
            }
        });
    }

    /**
     * @param animation has animation
     * @return true if begin animation
     */
    private boolean resetClosePosition(boolean animation) {
        int width = getWidth(), height = getHeight();
        int dx, dy;
        if (isSlidingCloseModeVertical()) {
            dy = isSlidingCloseModeVerticalTop() ? height : -height;
            dx = 0;
        } else if (isSlidingCloseModeHorizontal()) {
            dx = isSlidingCloseModeHorizontalLeft() ? width : -width;
            dy = 0;
        } else {
            dx = 0;
            dy = 0;
        }
        int scrollX = getScrollX(), scrollY = getScrollY();
        if (scrollX > 0) {
            dx = width - scrollX;
        } else if (scrollX < 0) {
            dx = -scrollX - width;
        }
        if (scrollY > 0) {
            dy = height - scrollY;
        } else if (scrollY < 0) {
            dy = -scrollY - height;
        }
        if (dx != 0 || dy != 0) {
            if (animation && isShown()) {
                mScroller.startScroll(scrollX, scrollY, dx, dy, MAX_SETTLE_DURATION);
                ViewCompat.postInvalidateOnAnimation(this);
                return true;
            } else {
                scrollBy(dx, dy);
            }
        }
        setScrollState(SCROLL_STATE_CLOSE);
        return false;
    }

    private void closeImpl(boolean animation) {
        boolean inDragging = mScrollState == SCROLL_STATE_DRAGGING;
        setScrollState(SCROLL_STATE_SETTLING);
        if (resetClosePosition(animation)) {
            if (!inDragging) {
                notifySlidingStart();
            }
        } else {
            completeScroll();
        }
    }

    private boolean hasEmptyRegion() {
        return getWidth() <= 0 || getHeight() <= 0;
    }

    /**
     * 设置初始的状态，如果在添加到布局且显示后，此方法不再有效果，默认为打开状态
     *
     * @param open true if open
     */
    public void setInitSlidingOpenState(boolean open) {
        if (mInitOpenState >= 0) {
            mInitOpenState = open ? MOTION_STATE_OPENING : MOTION_STATE_CLOSING;
        }
    }

    /**
     * @param animation has animation
     * @return true if begin animation
     */
    private boolean resetOpenPosition(boolean animation) {
        int scrollX = getScrollX(), scrollY = getScrollY();
        if (animation && isShown()) {
            mScroller.startScroll(scrollX, scrollY, -scrollX, -scrollY, MAX_SETTLE_DURATION);
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        } else {
            scrollBy(-scrollX, -scrollY);
        }
        setScrollState(SCROLL_STATE_IDLE);
        return false;
    }

    /**
     * Open page
     *
     * @param animation with animation
     */
    public void open(final boolean animation) {
        setMotionState(MOTION_STATE_OPENING);
        post(new Runnable() {
            @Override
            public void run() {
                openImpl(animation);
            }
        });
    }

    private void openImpl(boolean animation) {
        boolean inDragging = mScrollState == SCROLL_STATE_DRAGGING;
        setScrollState(SCROLL_STATE_SETTLING);
        if (resetOpenPosition(animation)) {
            if (!inDragging) {
                notifySlidingStart();
            }
        } else {
            completeScroll();
        }
    }

    private void notifySlidingStart() {
        if (mSlidingScrollListener != null) {
            mSlidingScrollListener.onScrollStarted();
        }
    }

    private void notifySlidingEnd() {
        if (mNeedLayout && isOpened()) {
            mNeedLayout = false;
            post(new Runnable() {
                @Override
                public void run() {
                    requestLayout();
                }
            });
        }
        if (mSlidingScrollListener != null) {
            mSlidingScrollListener.onScrollEnded();
        }
    }

    /**
     * Set content shadow
     *
     * @param left   left shadow drawable
     * @param top    top shadow drawable
     * @param right  right shadow drawable
     * @param bottom bottom shadow drawable
     */
    public void setShadowDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mLeftShadowDrawable = left;
        mTopShadowDrawable = top;
        mRightShadowDrawable = right;
        mBottomShadowDrawable = bottom;
    }

    /**
     * Set content shadow
     *
     * @param left   left shadow drawable
     * @param top    top shadow drawable
     * @param right  right shadow drawable
     * @param bottom bottom shadow drawable
     */
    public void setShadowDrawable(int left, int top, int right, int bottom) {
        setShadowDrawable(getDrawableSafety(left), getDrawableSafety(top), getDrawableSafety(right), getDrawableSafety(bottom));
    }

    /**
     * Set content shadow width
     *
     * @param width width
     */
    public void setShadowWidth(int width) {
        mShadowWidth = width;
    }

    private Drawable getDrawableSafety(int resourceId) {
        return resourceId == 0 ? null : getResources().getDrawable(resourceId);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mSlidingCloseMode != SLIDING_CLOSE_MODE_NONE
                && mScrollState != SCROLL_STATE_IDLE
                && mShadowWidth > 0 && getChildCount() == 1) {
            View view = getChildAt(0);
            if (view.getVisibility() == View.VISIBLE) {
                drawShadow(view, canvas);
                if (drawViewCache(view, canvas)) {
                    return;
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    private boolean drawViewCache(View view, Canvas canvas) {
        if (mChildCacheBitmap == null) {
            mChildCacheBitmap = view.getDrawingCache();
        }
        Bitmap cache = mChildCacheBitmap;
        if (cache != null && !cache.isRecycled()) {
            canvas.drawBitmap(cache, view.getLeft(), view.getTop(), null);
            return true;
        }
        return false;
    }

    /**
     * draw shadow
     *
     * @param content content view
     * @param canvas  canvas
     */
    public void drawShadow(View content, Canvas canvas) {
        int left = content.getLeft(), top = content.getTop(), right = content.getRight(), bottom = content.getBottom();
        int shadowLeft = left - mShadowWidth;
        int shadowRight = right + mShadowWidth;
        int shadowTop = top - mShadowWidth;
        int shadowBottom = bottom + mShadowWidth;
        if (isSlidingCloseModeHorizontal()) {
            if (mLeftShadowDrawable != null) {
                mLeftShadowDrawable.setBounds(shadowLeft, top, left, bottom);
                mLeftShadowDrawable.draw(canvas);
            }
            if (mRightShadowDrawable != null) {
                mRightShadowDrawable.setBounds(right, top, shadowRight, bottom);
                mRightShadowDrawable.draw(canvas);
            }
        }
        if (isSlidingCloseModeVertical()) {
            if (mTopShadowDrawable != null) {
                mTopShadowDrawable.setBounds(left, shadowTop, right, top);
                mTopShadowDrawable.draw(canvas);
            }

            if (mBottomShadowDrawable != null) {
                mBottomShadowDrawable.setBounds(left, bottom, right, shadowBottom);
                mBottomShadowDrawable.draw(canvas);
            }
        }
    }

    /**
     * Callback invoked when the drawer is opened.
     */
    public static interface OnSlidingOpenListener {
        /**
         * Invoked when the drawer becomes fully open.
         */
        public void onSlidingOpened();
    }

    /**
     * Callback invoked when the drawer is closed.
     */
    public static interface OnSlidingCloseListener {
        /**
         * Invoked when the drawer becomes fully closed.
         */
        public void onSlidingClosed();
    }

    /**
     * Callback invoked when the drawer is scrolled.
     */
    public static interface OnSlidingScrollListener {
        /**
         * Invoked when the user starts dragging/flinging the drawer's handle.
         */
        public void onScrollStarted();

        /**
         * Invoked when the user stops dragging/flinging the drawer's handle.
         */
        public void onScrollEnded();
    }
}
