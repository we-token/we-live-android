package show.we.lib.widget.abc_pull_to_refresh;

import android.widget.AbsListView;

/**
 * @author ll
 * @version 3.8.0
 *          Date: 14-4-21
 *          Time: 下午5:21
 */
public interface OnListViewScrollListener {
    /**
     * onListViewScroll
     * @param view view
     * @param firstVisibleItem firstVisibleItem
     * @param visibleItemCount visibleItemCount
     * @param totalItemCount totalItemCount
     */
    public void onListViewScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount);

    /**
     * onScrollStateChanged
     * @param view view
     * @param scrollState scrollStated
     */
    public void onScrollStateChanged(AbsListView view, int scrollState);
}
