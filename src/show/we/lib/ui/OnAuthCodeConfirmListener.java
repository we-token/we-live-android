package show.we.lib.ui;

/**
 * 验证码确认接口
 *
 * @author ll
 * @version 2.4.0
 */
public interface OnAuthCodeConfirmListener {
    /**
     * 验证码输入确认回调
     *
     * @param authCode 验证码
     */
    public void onAuthCodeConfirmed(String authCode);
}
