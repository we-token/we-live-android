/**
 * @file XListView.java
 * @package me.maxwin.view
 * @create Mar 18, 2012 6:28:41 PM
 * @author Maxwin
 * @description An ListView support (a) Pull down to refresh, (b) Pull up to load more.
 * 		Implement IXListViewListener, and see stopRefresh() / stopLoad().
 */
package show.we.lib.widget.abc_pull_to_refresh;

import show.we.lib.widget.abc_pull_to_refresh.listeners.OnAllDataLoadedListener;
import show.we.lib.widget.abc_pull_to_refresh.listeners.OnLoadListener;

/**
 * 统一下拉刷新控件和LoadListView行为的接口
 * @author ll
 * @version 1.0.0
 */
public interface AbsLoadListView {

    /**
     * 设置加载回调接口
     *
     * @param listener listener
     */
    public void setOnLoadListener(OnLoadListener listener);

    /**
     * 开始加载
     */
    public void startLoad();

    /**
     * stop load more, reset footer view.
     * @param isRequestSuccess isRequestSuccess
     */
    public void stopLoad(boolean isRequestSuccess);

    /**
     * 是否正在加载
     *
     * @return 是否正在加载
     */
    public boolean isLoading();

    /**
     * 允许下拉加载更多
     * @param listener listener
     */
    public void enableLoad(OnAllDataLoadedListener listener);

    /**
     * resetFooter
     */
    public void resetFooter();

    /**
     * hideEmptyView
     */
    public void hideEmptyView();

    /**
     * hideLoadingBottom
     */
    public void hideLoadingBottom();

    /**
     * 设置加载文字提示
     * @param resId resId
     */
    public void setLoadingMoreText(int resId);

    /**
     * 设置加载文字提示
     *
     * @param text 加载文字
     */
    public void setLoadingMoreText(String text);

    /**
     * 设置所有数据加载完毕文字
     *
     * @param resId 所有数据加载完毕文字
     */
    public void setAllDataLoadedText(int resId);

    /**
     * 设置所有数据加载完毕文字
     *
     * @param text 所有数据加载完毕文字
     */
    public void setAllDataLoadedText(String text);

    /**
     * 请求是否成功
     * @return true - 成功
     */
    public boolean isRequestSuccess();

    /**
     * 是否需要登录
     * @return true - 需要
     */
    public boolean isNeedLogin();

    /**
     * 设置是否需要登录
     * @param needLogin needLogin
     */
    public void setNeedLogin(boolean needLogin);

    /**
     * 设置是否隐藏掉EmptyView
     */
    public void forceHideEmptyView();

}
