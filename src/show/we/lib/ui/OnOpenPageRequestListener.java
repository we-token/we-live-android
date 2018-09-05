package show.we.lib.ui;

import show.we.lib.config.Enums;

/**
 * Created by Administrator on 13-7-22.
 * 当打开页面请求发生的监听器
 *
 * @author ll
 * @version 1.0.0
 */
public interface OnOpenPageRequestListener {
    /**
     * 当打开页面请求发生
     *
     * @param pageType 请求的pageID
     */
    public void onOpenPageRequested(Enums.PageType pageType);
}
