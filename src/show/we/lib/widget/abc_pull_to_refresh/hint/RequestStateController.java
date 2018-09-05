package show.we.lib.widget.abc_pull_to_refresh.hint;

import show.we.lib.config.Enums;

/**
 * @author ll
 * @version 1.0.0
 */
public interface RequestStateController {

    /**
     * 更新状态
     * @param state state
     */
    public void updateState(Enums.HintState state);

    /**
     * 设置无数据提示文字
     * @param hint hint
     */
    public void setNoDataHint(String hint);

    /**
     * 设置加载中提示文字
     * @param hint hint
     */
    public void setRequestingHint(String hint);

    /**
     * 设置无网络提示文字
     * @param hint hint
     */
    public void setWifiOffHint(String hint);

    /**
     * 设置请求失败提示文字
     * @param hint hint
     */
    public void setRequestFailedHint(String hint);

    /**
     * 设置未登录提示文字
     * @param hint hint
     */
    public void setUnLoginHint(String hint);

    /**
     * 设置无数据提示文字
     * @param resId resId
     */
    public void setNoDataHint(int resId);

    /**
     * 设置加载中提示文字
     * @param resId resId
     */
    public void setRequestingHint(int resId);

    /**
     * 设置无网络提示文字
     * @param resId resId
     */
    public void setWifiOffHint(int resId);

    /**
     * 设置请求失败提示文字
     * @param resId resId
     */
    public void setRequestFailedHint(int resId);

    /**
     * 设置未登录提示文字
     * @param resId resId
     */
    public void setUnloginHint(int resId);

    /**
     *
     * @param noDataHintView noDataHintView
     */
    public void replaceNoDataHintView(AbsHintView noDataHintView);

    /**
     *
     * @param requestFailHintView requestFailHintView
     */
    public void replaceRequestFailHintView(AbsHintView requestFailHintView);

    /**
     *
     * @param unLoginView unLoginView
     */
    public void replaceUnLoginHintView(AbsHintView unLoginView);

    /**
     *
     * @param wifiOffHintView wifiOffHintView
     */
    public void replaceWifiOffHintView(AbsHintView wifiOffHintView);
}
