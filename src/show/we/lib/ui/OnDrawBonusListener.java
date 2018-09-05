package show.we.lib.ui;

/**
 * 领取奖励的回调通知
 * Created by CG on 13-11-6.
 *
 * @author ll
 * @version 3.0.0
 */
public interface OnDrawBonusListener {
    /**
     * 领取奖励失败的通知
     *
     * @param code 失败的返回码
     */
    void onDrawBonusError(int code);

    /**
     * 领取奖励成功的通知
     */
    void onDrawBonusSuccess();
}
