/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoad().
 */
package show.we.lib.widget.abc_pull_to_refresh;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;

import show.we.lib.widget.abc_pull_to_refresh.listeners.OnAllDataLoadedListener;
import show.we.lib.widget.abc_pull_to_refresh.listeners.OnLoadListener;

/**
 * @author ll
 * @version 1.0.0
 */
public class LoadListView extends ListView implements AbsLoadListView, OnScrollListener {

    /**
     * 刷新状态
     */
    public enum State {
        /**
         * 初始状态 *
         */
        NORMAL,
        /**
         * 释放刷新 *
         */
        READY,
        /**
         * 正在刷新 *
         */
        LOADING
    }

    private final static int SCROLLBACK_FOOTER = 1;

    private final static int PULL_LOAD_MORE_DELTA = 0;

    private final static float OFFSET_RADIO = 1.0f; //support iOS like pull feature.

    private final static int ADJUST_MILLS_PER_DIP = 3;

    private static final int DELAY = 250;

    private OnLoadListener mLoadListener;
    private OnAllDataLoadedListener mAllDataLoadedListener;
    private OnListViewScrollListener mScrollListener;
    private float mLastY = -1;
    private Scroller mScroller;

    private LoadViewFooter mFooterView;
    private boolean mEnablePullLoad = false;
    private boolean mIsFooterReady = false;

    private int mTotalItemCount;

    private int mScrollBack;

    private int mScrollState = SCROLL_STATE_IDLE;

    private boolean mRequestSuccess = false;
    private boolean mForceHideEmptyView = false;
    private boolean mNeedLogin = false;

    /**
     * @param context context
     */
    public LoadListView(Context context) {
        this(context, null);
    }

    /**
     * @param context context
     * @param attrs   attrs
     */
    public LoadListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public LoadListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (getContext() instanceof Activity) {
            mScroller = new Scroller(context, new DecelerateInterpolator());
            super.setOnScrollListener(this);
            mFooterView = new LoadViewFooter(context);
        } else {
            throw new IllegalArgumentException("LoadListView context must be instanceof Activity!");
        }
    }

    /**
     * setScrollListener
     * @param listener listener
     */
    public void setScrollListener(OnListViewScrollListener listener) {
        this.mScrollListener = listener;
    }

    /**
     * notifyAllDataLoaded
     */
    public void notifyAllDataLoaded() {
        mFooterView.notifyAllDataLoaded();
    }

    @Override
    public void setOnItemClickListener(final OnItemClickListener listener) {
        super.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mEnablePullLoad && position >= parent.getAdapter().getCount() - 1) {
                    return;
                }
                listener.onItemClick(parent, view, position, id);
            }
        });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        if (!mIsFooterReady) {
            mIsFooterReady = true;
            addFooterView(mFooterView);
            mFooterView.hide();
        }
        super.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                postDelayed(new Runnable() { //由于notifyDataSetChanged以后ListView的Count数没有立即变过来，这时状态可能不对，故延迟处理
                    @Override
                    public void run() {
                        ViewParent parent = getParent();
                        if (parent instanceof RefreshLoadLayout) {
                            ((RefreshLoadLayout) parent).updateRequestState();
                        }
                        if (isAllDataLoaded()) {
                            notifyAllDataLoaded();
                        }
                    }
                }, DELAY);
            }
        });
    }

    @Override
    public void setOnLoadListener(OnLoadListener listener) {
        mLoadListener = listener;
    }

    @Override
    public void startLoad() {
        hideEmptyView();
        if (!isLoading() && mEnablePullLoad && !isAllDataLoaded()) {
            mFooterView.setState(State.LOADING);
            if (mLoadListener != null) {
                mLoadListener.onLoadStarted(this);
            }
        }
    }

    @Override
    public void stopLoad(boolean isRequestSuccess) {
        setRequestSuccess(isRequestSuccess);
        if (isLoading()) {
            mFooterView.setState(State.NORMAL);
        }
    }

    @Override
    public boolean isLoading() {
        return mFooterView.getState() == State.LOADING;
    }

    @Override
    public void enableLoad(OnAllDataLoadedListener listener) {
        mEnablePullLoad = true;
        mFooterView.show();
        mAllDataLoadedListener = listener;
    }

    @Override
    public void resetFooter() {
        if (mEnablePullLoad && !isAllDataLoaded()) {
            mFooterView.reset();
        }
    }

    @Override
    public void hideEmptyView() {
        setVisibility(View.VISIBLE);
        View emptyView = getEmptyView();
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideLoadingBottom() {
        mFooterView.hide();
    }

    @Override
    public void setLoadingMoreText(int resId) {
        mFooterView.setLoadingText(getContext().getString(resId));
    }

    @Override
    public void setLoadingMoreText(String text) {
        mFooterView.setLoadingText(text);
    }

    @Override
    public void setAllDataLoadedText(int resId) {
        setAllDataLoadedText(getContext().getString(resId));
    }

    @Override
    public void setAllDataLoadedText(String text) {
        mFooterView.setAllDataLoadedText(text);
    }

    @Override
    public boolean isRequestSuccess() {
        return mRequestSuccess;
    }

    @Override
    public boolean isNeedLogin() {
        return mNeedLogin;
    }

    @Override
    public void setNeedLogin(boolean needLogin) {
        mNeedLogin = needLogin;
    }

    @Override
    public void forceHideEmptyView() {
        mForceHideEmptyView = true;
        hideEmptyView();
    }

    /**
     * 设置是否请求成功
     * @param success success
     */
    public void setRequestSuccess(boolean success) {
        mRequestSuccess = success;
    }

    /**
     * updateEmptyStatus
     * @return true - 显示EmptyView
     */
    public boolean updateEmptyStatus() {
        boolean isEmpty = false;
        View emptyView = getEmptyView();
        if (emptyView != null) {
            isEmpty = !mForceHideEmptyView && (getAdapter() == null
                    || getCount() - getFooterViewsCount() - getHeaderViewsCount() <= 0);
            emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
            setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        }
        return isEmpty;
    }

    /**
     * ListView数据是否为空
     * @return ListView数据是否为空
     */
    public boolean isEmpty() {
        boolean isEmpty = false;
        View emptyView = getEmptyView();
        if (emptyView != null) {
            isEmpty = !mForceHideEmptyView && (getAdapter() == null
                    || getCount() - getFooterViewsCount() - getHeaderViewsCount() <= 0);
        }
        return isEmpty;
    }

    @Override
    public void setEmptyView(View emptyView) {
        if (!mForceHideEmptyView) {
            super.setEmptyView(emptyView);
        }
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !isLoading()) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load more.
                mFooterView.setState(State.READY);
            } else {
                mFooterView.setState(State.NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight() {
        int bottomMargin = mFooterView.getBottomMargin();
        if (bottomMargin > 0) {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                    ADJUST_MILLS_PER_DIP * Math.abs(bottomMargin));
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0) && mEnablePullLoad) {
                    updateFooterHeight(-deltaY / OFFSET_RADIO);
                }
                break;
            default:
                mLastY = -1; // reset
                if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    resetFooterHeight();
                }
                break;
        }
        /**
         * 临时解决小米机型崩溃问题:java.lang.IndexOutOfBoundsException: Invalid index 5, size is 2
         at java.util.ArrayList.throwIndexOutOfBoundsException(ArrayList.java:251)
         at java.util.ArrayList.get(ArrayList.java:304)
         at android.widget.HeaderViewListAdapter.isEnabled(HeaderViewListAdapter.java:164)
         at android.widget.AbsListView.onTouchEvent(AbsListView.java:3520)
         */
        try {
            return super.onTouchEvent(ev);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return true;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_FOOTER) {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;
        if (getLastVisiblePosition() == mTotalItemCount - 1
                && mFooterView.getState() != State.LOADING) {
            startLoad();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onListViewScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    private boolean isAllDataLoaded() {
       return mAllDataLoadedListener != null && mAllDataLoadedListener.isAllLoaded();
    }
}
