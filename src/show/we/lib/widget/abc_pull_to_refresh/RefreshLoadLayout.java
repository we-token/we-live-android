package show.we.lib.widget.abc_pull_to_refresh;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.utils.LoginUtils;
import show.we.lib.widget.abc_pull_to_refresh.actionbarcompat.AbcPullToRefreshLayout;
import show.we.lib.widget.abc_pull_to_refresh.base.ActionBarPullToRefresh;
import show.we.lib.widget.abc_pull_to_refresh.hint.AbsHintView;
import show.we.lib.widget.abc_pull_to_refresh.hint.RequestHintView;
import show.we.lib.widget.abc_pull_to_refresh.hint.RequestStateController;
import show.we.lib.widget.abc_pull_to_refresh.listeners.OnAllDataLoadedListener;
import show.we.lib.widget.abc_pull_to_refresh.listeners.OnLoadListener;
import show.we.lib.widget.abc_pull_to_refresh.listeners.OnRefreshListener;
import show.we.lib.widget.abc_pull_to_refresh.viewdelegates.ViewDelegate;

/**
 * @author ll
 * @version 1.0.0
 */
public class RefreshLoadLayout extends AbcPullToRefreshLayout implements AbsLoadListView, RequestHintView.OnScreenTapedListener, RequestStateController {

    private static final int DEFAULT_DELAY = 250;

    private LoadListView mLoadListView;
    private RequestStateController mRequestStateController;
    private boolean mShowLoadingHintWhenInit = true; //列表刚显示的时候是没有数据的，这时会显示加载失败的EmptyView，这时应该强制显示加载中转圈

    /**
     * context context
     * @param context context
     */
    public RefreshLoadLayout(Context context) {
        super(context);
        init(context, false);
    }

    /**
     *
     * @param context context
     * @param attrs attrs
     */
    public RefreshLoadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, false);
    }

    /**
     *
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public RefreshLoadLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, false);
    }

    /**
     *
     * @param context context
     * @param forceHideEmptyView forceHideEmptyView
     */
    public RefreshLoadLayout(Context context, boolean forceHideEmptyView) {
        super(context);
        init(context, forceHideEmptyView);
    }

    private void init(Context context, boolean forceHideEmptyView) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLoadListView = (LoadListView) View.inflate(context, R.layout.load_list_view, null);
        addView(mLoadListView, params);

        RequestHintView stateView = new RequestHintView(getContext());
        stateView.setOnScreenTapedListener(this);
        if (!forceHideEmptyView) {
            addView(stateView, params);
            mLoadListView.setEmptyView(stateView);
        }

        mRequestStateController = stateView;

        if (context instanceof Activity) {
            ActionBarPullToRefresh.from((Activity) context).allChildrenArePullable().
                    useViewDelegate(FrameLayout.class, new ViewDelegate() {
                        @Override
                        public boolean isReadyForPull(View view, float v, float v2) {
                            return Boolean.TRUE;
                        }
                    }).
                    useViewDelegate(LinearLayout.class, new ViewDelegate() {
                        @Override
                        public boolean isReadyForPull(View view, float v, float v2) {
                            return Boolean.TRUE;
                        }
                    }).
                    useViewDelegate(ImageView.class, new ViewDelegate() {
                        @Override
                        public boolean isReadyForPull(View view, float v, float v2) {
                            return Boolean.TRUE;
                        }
                    }).
                    useViewDelegate(TextView.class, new ViewDelegate() {
                        @Override
                        public boolean isReadyForPull(View view, float v, float v2) {
                            return Boolean.TRUE;
                        }
                    }).
                    useViewDelegate(Button.class, new ViewDelegate() {
                        @Override
                        public boolean isReadyForPull(View view, float v, float v2) {
                            return Boolean.TRUE;
                        }
                    }).setup(this);
        } else {
            throw new IllegalArgumentException("RefreshLoadLayout context must be activity!");
        }
    }

    /**
     * 设置列表刚显示的时候就显示加载中转圈
     * @param show 列表刚显示的时候就显示加载中转圈
     */
    public void setShowLoadingHintWhenInit(boolean show) {
        mShowLoadingHintWhenInit = show;
    }

    // extends from PullToRefreshLayout
    @Override
    public void startRefresh() {
        hideLoadingBottom();
        super.startRefresh();
        updateRequestState();
        mShowLoadingHintWhenInit = false;
    }

    @Override
    public void stopRefresh(boolean isRequestSuccess) {
        if (mShowLoadingHintWhenInit) {
            mShowLoadingHintWhenInit = false;
        }
        mLoadListView.setRequestSuccess(isRequestSuccess);
        if (isRefreshing()) {
            super.stopRefresh(isRequestSuccess);
        }
        resetFooter();
        updateRequestState();
    }

    /**
     *
     * @return listView
     */
    public ListView getListView() {
        return mLoadListView;
    }

    /**
     * setOnListViewScrollListener
     * @param listener listener
     */
    public void setOnListViewScrollListener(OnListViewScrollListener listener) {
        mLoadListView.setScrollListener(listener);
    }

    /**
     * 设置刷新监听
     * @param refreshListener refreshListener
     */
    public void setOnRefreshListener(OnRefreshListener refreshListener) {
        getPullToRefreshAttacher().setOnRefreshListener(refreshListener);
    }

    /**
     * 延迟刷新
     */
    public void postRefreshDelay() {
        postRefreshDelay(DEFAULT_DELAY);
    }

    /**
     * 延迟刷新
     *
     * @param delay delay
     */
    public void postRefreshDelay(long delay) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                startRefresh();
            }
        }, delay);
    }

    /**
     * 延迟刷新(不显示刷新bar)
     */
    public void postRefreshDelayWithoutTitle() {
        postRefreshDelayWithoutTitle(DEFAULT_DELAY);
    }

    /**
     * 延迟刷新(不显示刷新bar)
     *
     * @param delay delay
     */
    public void postRefreshDelayWithoutTitle(long delay) {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadListView.updateEmptyStatus();
                hideLoadingBottom();
                setRefreshing(false);
            }
        }, delay);
    }

    /**
     * 刷新，不显示actionbar
     */
    public void startRefreshWithoutTitle() {
        hideLoadingBottom();
        setRefreshing(false);
        updateRequestState();
        mShowLoadingHintWhenInit = false;
    }

    @Override
    public void updateState(Enums.HintState state) {
        mRequestStateController.updateState(state);
    }

    // implements from RequestStateController start
    @Override
    public void setNoDataHint(String hint) {
        mRequestStateController.setNoDataHint(hint);
    }

    @Override
    public void setRequestingHint(String defaultRequestingHint) {
        mRequestStateController.setRequestingHint(defaultRequestingHint);
    }

    @Override
    public void setWifiOffHint(String defaultWifiOffHint) {
        mRequestStateController.setWifiOffHint(defaultWifiOffHint);
    }

    @Override
    public void setRequestFailedHint(String defaultRequestFailedHint) {
        mRequestStateController.setRequestFailedHint(defaultRequestFailedHint);
    }

    @Override
    public void setUnLoginHint(String defaultUnloginHint) {
        mRequestStateController.setUnLoginHint(defaultUnloginHint);
    }

    @Override
    public void setNoDataHint(int resId) {
        mRequestStateController.setNoDataHint(resId);
    }

    @Override
    public void setRequestingHint(int resId) {
        mRequestStateController.setRequestingHint(resId);
    }

    @Override
    public void setWifiOffHint(int resId) {
        mRequestStateController.setWifiOffHint(resId);
    }

    @Override
    public void setRequestFailedHint(int resId) {
        mRequestStateController.setRequestFailedHint(resId);
    }

    @Override
    public void setUnloginHint(int resId) {
        mRequestStateController.setUnloginHint(resId);
    }

    @Override
    public void replaceNoDataHintView(AbsHintView noDataHintView) {
        mRequestStateController.replaceNoDataHintView(noDataHintView);
    }

    @Override
    public void replaceRequestFailHintView(AbsHintView requestFailHintView) {
        mRequestStateController.replaceRequestFailHintView(requestFailHintView);
    }

    @Override
    public void replaceUnLoginHintView(AbsHintView unLoginView) {
        mRequestStateController.replaceUnLoginHintView(unLoginView);
    }

    @Override
    public void replaceWifiOffHintView(AbsHintView wifiOffHintView) {
        mRequestStateController.replaceWifiOffHintView(wifiOffHintView);
    }

    // implements from RequestStateController end

    // implements from interface AbsLoadListView start
    @Override
    public void setOnLoadListener(OnLoadListener listener) {
        mLoadListView.setOnLoadListener(listener);
    }

    @Override
    public void startLoad() {
        mLoadListView.startLoad();
    }

    @Override
    public void stopLoad(boolean isRequestSuccess) {
        mLoadListView.stopLoad(isRequestSuccess);
    }

    @Override
    public boolean isLoading() {
        return mLoadListView.isLoading();
    }

    @Override
    public void enableLoad(OnAllDataLoadedListener listener) {
        mLoadListView.enableLoad(listener);
    }

    @Override
    public void resetFooter() {
        mLoadListView.resetFooter();
    }

    @Override
    public void hideEmptyView() {
        mLoadListView.hideEmptyView();
    }

    @Override
    public void hideLoadingBottom() {
        mLoadListView.hideLoadingBottom();
    }

    @Override
    public void setLoadingMoreText(String text) {
        mLoadListView.setLoadingMoreText(text);
    }

    @Override
    public void setLoadingMoreText(int resId) {
        mLoadListView.setLoadingMoreText(resId);
    }

    @Override
    public void setAllDataLoadedText(int resId) {
        mLoadListView.setAllDataLoadedText(resId);
    }

    @Override
    public void setAllDataLoadedText(String text) {
        mLoadListView.setAllDataLoadedText(text);
    }

    @Override
    public boolean isRequestSuccess() {
        return mLoadListView.isRequestSuccess();
    }

    @Override
    public boolean isNeedLogin() {
        return mLoadListView.isNeedLogin();
    }

    @Override
    public void setNeedLogin(boolean needLogin) {
        mLoadListView.setNeedLogin(needLogin);
    }

    @Override
    public void forceHideEmptyView() {
        mLoadListView.forceHideEmptyView();
    }

    // implements from interface AbsLoadListView end

    /**
     * 更新加载状态
     */
    public void updateRequestState() {
        if (mLoadListView.isEmpty()) {
            if (mShowLoadingHintWhenInit || (isRefreshing() && !isStopping())) {
                mRequestStateController.updateState(Enums.HintState.LOADING);
            } else if (!EnvironmentUtils.Network.isNetWorkAvailable()) {
                mRequestStateController.updateState(Enums.HintState.WIFI_OFF);
            } else if (isNeedLogin() && !LoginUtils.isAlreadyLogin()) {
                mRequestStateController.updateState(Enums.HintState.ACCESS_RESTRICT);
            } else if (isRequestSuccess()) {
                mRequestStateController.updateState(Enums.HintState.NO_DATA);
            } else {
                mRequestStateController.updateState(Enums.HintState.FAILED);
            }
        }
    }

    @Override
    public void onScreenTaped() {
        if (canRefreshFromTouch()) {
            startRefresh();
        } else {
            startRefreshWithoutTitle();
        }
    }
}
