package show.we.lib.ui;

import show.we.sdk.request.BaseResult;

/**
 * 登录完成的回调函数
 * Created by CG on 13-11-6.
 *
 * @author ll
 * @version 3.0.0
 */
public interface OnLoginFinishListener {
    /**
     * 整个登录过程完成的通知接口 包括授权和获取用户信息
     * 授权包含：自己的用户系统和第三方登录系统
     *
     * @param result 登录成功，参数传递获取的用户信息，登录失败，返回原因（账号或者密码错误）
     */
    void onLoginFinished(BaseResult result);

    /**
     * 由于网络原因，登录失败的通知接口
     */
    void onAuthorizeFailure();
}
