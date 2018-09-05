package show.we.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import show.we.sdk.util.ConstantUtils;
import show.we.lib.utils.DisplayUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 1. padding 无效
 * 2. wrap_content 无效
 * 3. 缓存一个对象列表
 * 4. 提供水平方向 - 左移和右移
 *
 * @author ll
 * @version 1.0.0
 */
public class SmoothBubbingLayout extends ViewGroup {

    /**
     * 构建子view的回调
     */
    public static interface BuildChildViewListener {
        /**
         * 需要提供一个显示的view
         *
         * @param object 请求添加内容时，传递的对象 {@link #requestAddContent}
         * @param view   已经回收的view，或者为null
         * @return 提供显示的view，不能为null
         */
        public View onBuildChildViewEvent(Object object, View view);
    }

    /**
     * 卷动完成的回调
     */
    public static interface OnScrollCompletedListener {
        /**
         * 卷动完成的回调
         */
        public void onScrollCompleted();
    }

    private final class WaitObject {
        private Object mObject;

        private WaitObject(Object o) {
            mObject = o;
        }
    }

    private static final int VELOCITY_DIP = 60;
    private static final int QUEUE_MAX_LENGTH = 0x7fffffff;

    private final float mDensity;

    private BuildChildViewListener mBuildChildViewListener;
    private OnScrollCompletedListener mScrollCompletedListener;
    private List<View> mRecycleViews;
    private Scroller mScroller;
    private int mVelocity;
    private List<WaitObject> mWaitShowList;
    private int mQueueMaxLength;
    private boolean mIsHorizontal;
    private boolean mFromLeft;
    private boolean mIsScrolling;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public SmoothBubbingLayout(Context context) {
        this(context, null);
    }

    /**
     * 构造函数
     *
     * @param context 上下文对象
     * @param attrs   属性
     */
    public SmoothBubbingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        final float mFloatNumber = 0.5f;
        mRecycleViews = new LinkedList<View>();
        mScroller = new Scroller(context, new LinearInterpolator());
        mDensity = context.getResources().getDisplayMetrics().density;
        mVelocity = (int) (VELOCITY_DIP * mDensity + mFloatNumber);
        mWaitShowList = new LinkedList<WaitObject>();
        mQueueMaxLength = QUEUE_MAX_LENGTH;
    }

    /**
     * 设置卷动速度(单位dip）
     *
     * @param velocity 速度
     */
    public void setVelocityInDip(int velocity) {
		if (velocity != 0) {
			mVelocity = (int) (velocity * mDensity);
		}
    }

    /**
     * 设置队列的最大长度
     *
     * @param maxLength 队列的最大长度
     */
    public void setQueueMaxLength(int maxLength) {
        mQueueMaxLength = maxLength;
    }

    /**
     * 设置水平方向
     */
    public void setHorizontal() {
        mIsHorizontal = true;
    }

    /**
     * 设置从左边滑出
     */
    public void setFromLeft() {
        mFromLeft = true;
    }

    /**
     * 设置构建对象的监听器
     *
     * @param listener 构建对象的监听器
     */
    public void setBuildChildViewListener(BuildChildViewListener listener) {
        mBuildChildViewListener = listener;
    }

    /**
     * 设置卷动完成的监听器
     *
     * @param listener 卷动完成的监听器
     */
    public void setOnScrollCompletedListener(OnScrollCompletedListener listener) {
        mScrollCompletedListener = listener;
    }

    /**
     * 请求增加内容
     *
     * @param object 增加内容
     */
    public void requestAddContent(Object object) {
        if (!addWaitList(object)) {
            recycleChildrenView();

            addNewView(object);

            runAnimation();
        }
    }

	private int getInnerWidth() {
		int width = getMeasuredWidth();
		if (width == 0) {
			LayoutParams lp = getLayoutParams();
			if (lp != null && lp.width > 0) {
				width = lp.width;
			} else {
				width = DisplayUtils.getWidthPixels();
			}
		}
		return width;
	}

	private int getInnerHeight() {
		int height = getMeasuredHeight();
		if (height == 0) {
			LayoutParams lp = getLayoutParams();
			if (lp != null && lp.height > 0) {
				height = lp.height;
			} else {
				final int defaultHeight = 40;
				height = DisplayUtils.dp2px(defaultHeight);
			}
		}
		return height;
	}

	private boolean addWaitList(Object object) {
		boolean addWaitList = getChildCount() > 1;
		if (addWaitList) {
			if (mIsHorizontal) {
				if (mFromLeft) {
					addWaitList = getChildAt(getChildCount() - 1).getRight() < getScrollX();
				} else {
					addWaitList = getChildAt(getChildCount() - 1).getLeft() > getInnerWidth() + getScrollX();
				}
			} else {
				addWaitList = getChildAt(getChildCount() - 1).getTop() > getInnerHeight() + getScrollY();
			}
		}
		if (addWaitList) {
			mWaitShowList.add(new WaitObject(object));
			if (mWaitShowList.size() > mQueueMaxLength) {
				mWaitShowList.remove(0);
			}
		}
		return addWaitList;
	}

    private void recycleChildrenView() {
        int count = getChildCount();
        int index = 0;
        for (; index < count; index++) {
            if (!checkIsRecycleView(index)) {
                break;
            }
        }

        int recycleCount = index;
        while (recycleCount > 0) {
            View recycleView = getChildAt(0);
            removeViewInLayout(recycleView);
            mRecycleViews.add(recycleView);
            recycleCount--;
        }
    }

    private boolean checkIsRecycleView(int index) {
        boolean isRecycle;
        if (mIsHorizontal) {
            if (mFromLeft) {
                isRecycle = checkIsRecycleViewFromLeft(index);
            } else {
                isRecycle = checkIsRecycleViewFromRight(index);
            }
        } else {
            isRecycle = checkIsRecycleViewInVertical(index);
        }
        return isRecycle;
    }

    private boolean checkIsRecycleViewInVertical(int index) {
        return getChildAt(index).getBottom() < getScrollY();
    }

    private boolean checkIsRecycleViewFromLeft(int index) {
        return getChildAt(index).getLeft() > getScrollX() + getInnerWidth();
    }

    private boolean checkIsRecycleViewFromRight(int index) {
        return getChildAt(index).getRight() < getScrollX();
    }

    private void addNewView(Object object) {
        if (mBuildChildViewListener == null) {
            throw new IllegalArgumentException("need a listener of BuildChildViewListener");
        }
        View recycleView = mRecycleViews.size() > 0 ? mRecycleViews.remove(0) : null;
        View animationView = mBuildChildViewListener.onBuildChildViewEvent(object, recycleView);
        LayoutParams layoutParams = animationView.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = createLayoutParams();
        }
        addViewInLayout(animationView, getChildCount(), layoutParams);

        int widthMeasureSpec = getWidthMeasureSpec(getInnerWidth(), animationView);
        int heightMeasureSpec = getHeightMeasureSpec(getInnerHeight(), animationView);
        animationView.measure(widthMeasureSpec, heightMeasureSpec);
        int width = animationView.getMeasuredWidth();
        int height = animationView.getMeasuredHeight();

        if (mIsHorizontal) {
            if (mFromLeft) {
                layoutViewFromLeft(animationView, width, height);
            } else {
                layoutViewFromRight(animationView, width, height);
            }
        } else {
            layoutViewInVertical(animationView, width, height);
        }
    }

    private LayoutParams createLayoutParams() {
        LayoutParams layoutParams;
        if (mIsHorizontal) {
            layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        } else {
            layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        }
        return layoutParams;
    }

    private int getWidthMeasureSpec(int width, View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            if (layoutParams.width > 0) {
                return MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY);
            }
        }
        return MeasureSpec.makeMeasureSpec(width, mIsHorizontal ? MeasureSpec.UNSPECIFIED : MeasureSpec.EXACTLY);
    }

    private int getHeightMeasureSpec(int height, View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            if (layoutParams.height > 0) {
                return MeasureSpec.makeMeasureSpec(layoutParams.height, MeasureSpec.EXACTLY);
            }
        }
        return MeasureSpec.makeMeasureSpec(height, mIsHorizontal ? MeasureSpec.EXACTLY : MeasureSpec.AT_MOST);
    }

    private void layoutViewFromLeft(View view, int width, int height) {
        int right = computeRight();
        int left = right - width;
        int top = 0;
        int bottom = top + height;
        view.layout(left, top, right, bottom);
    }

    private int computeRight() {
        int count = getChildCount();
        int right = getScrollX();
        if (count > 1) {
            right = Math.min(right, getChildAt(count - 2).getLeft());
        }
        return right;
    }

    private void layoutViewFromRight(View view, int width, int height) {
        int left = computeLeft();
        int right = left + width;
        int top = 0;
        int bottom = top + height;
        view.layout(left, top, right, bottom);
    }

    private int computeLeft() {
        int count = getChildCount();
        int left = getScrollX() + getInnerWidth();
        if (count > 1) {
            left = Math.max(left, getChildAt(count - 2).getRight());
        }
        return left;
    }

    private void layoutViewInVertical(View view, int width, int height) {
        int left = 0;
        int top = computeTop();
        int right = left + width;
        int bottom = top + height;
        view.layout(left, top, right, bottom);
    }

    private int computeTop() {
        int count = getChildCount();
        int top = getInnerHeight() + getScrollY();
        if (count > 1) {
            top = Math.max(top, getChildAt(count - 2).getBottom());
        }
        return top;
    }

    private void runAnimation() {
        if (mIsHorizontal) {
            if (mFromLeft) {
                runAnimationInHorizontal(getChildAt(getChildCount() - 1).getLeft() - getInnerWidth());
            } else {
                runAnimationInHorizontal(getChildAt(getChildCount() - 1).getRight());
            }
        } else {
            runAnimationInVertical();
        }
        invalidate();
    }

    private void runAnimationInHorizontal(int finalX) {
        if (mScroller.isFinished()) {
            int move = finalX - mScroller.getFinalX();
            int duration = Math.abs(move * ConstantUtils.THOUSAND / mVelocity);
            mScroller.startScroll(mScroller.getFinalX(), 0, move, 0, duration);
        } else {
            mScroller.setFinalX(finalX);

            int move = finalX - mScroller.getStartX();
            int duration = Math.abs(move * ConstantUtils.THOUSAND / mVelocity);
            mScroller.extendDuration(duration - mScroller.timePassed());
        }
    }

    private void runAnimationInVertical() {
        int finalY = getChildAt(getChildCount() - 1).getBottom();
        if (mScroller.isFinished()) {
            int move = finalY - mScroller.getFinalY();
            int duration = move * ConstantUtils.THOUSAND / mVelocity;
            mScroller.startScroll(0, mScroller.getFinalY(), 0, move, duration);
        } else {
            mScroller.setFinalY(finalY);

            int move = finalY - mScroller.getStartY();
            int duration = move * ConstantUtils.THOUSAND / mVelocity;
            mScroller.extendDuration(duration - mScroller.timePassed());
        }
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mIsScrolling = true;

            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            checkWaitShowObject();

            postInvalidate();
        } else {
            if (mIsScrolling) {
                mIsScrolling = false;
                if (mScrollCompletedListener != null) {
                    mScrollCompletedListener.onScrollCompleted();
                }
            }
        }
        super.computeScroll();
    }

    private void checkWaitShowObject() {
        if (mWaitShowList.size() > 0 && enableInsertWaitObject()) {
            recycleChildrenView();

            addNewView(mWaitShowList.remove(0).mObject);

            runAnimation();
        }
    }

    private boolean enableInsertWaitObject() {
        boolean isEnable;
        if (mIsHorizontal) {
            if (mFromLeft) {
                isEnable = getChildAt(getChildCount() - 1).getRight() > getScrollX();
            } else {
                isEnable = getChildAt(getChildCount() - 1).getLeft() < getInnerWidth() + getScrollX();
            }
        } else {
            isEnable = getChildAt(getChildCount() - 1).getTop() < getInnerHeight() + getScrollY();
        }
        return isEnable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }
}
