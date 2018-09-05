package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.RequestCallback;
import show.we.lib.utils.PromptUtils;

/**
 * 检查Token是否失效的回调虚类
 *
 * @param <R> 继承自BaseResult的类型
 * @author ll
 * @version 1.0.0
 */
public abstract class BaseRequestCallback<R extends BaseResult> implements RequestCallback<R> {

    @Override
    public void onRequestSuccess(R result) {
        onRequestSucceed(result);
    }

    @Override
    public void onRequestFailure(R result) {
        if (result.getCode() == ErrorCode.ACCESS_TOKEN_EXPIRED) {
            PromptUtils.showTokenExpiredDialog();
        }
        onRequestFailed(result);
    }

    /**
     * 请求成功回调
     *
     * @param result result
     */
    public abstract void onRequestSucceed(R result);
    /**
     * 请求失败回调
     *
     * @param result result
     */
    public abstract void onRequestFailed(R result);
}
