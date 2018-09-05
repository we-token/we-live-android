package show.we.lib.widget.abc_pull_to_refresh.listeners;

/**
 * 数据是否加载完毕接口
 * @author ll
 * @version 3.8.0
 */
public interface OnAllDataLoadedListener {
    /**
     * 数据是否加载
     *
     * @return 数据是否加载
     */
    public boolean isAllLoaded();
}