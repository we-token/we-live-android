package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.model.RemindListResult;

/**
 * Created by CG on 14-1-9.
 *
 * @author ll
 * @version 3.4.0
 */
public class RemindAPI {

    private static final String METHOD_REMIND_LIST = "remind/list";
    private static final String METHOD_REMIND_DEL = "remind/del";
    private static final String METHOD_REMIND_MARK_READ = "remind/mark_read";
    private static final String METHOD_AUTH_CODE = "ttus/authcode";
    
    /**
     * 生成验证码
     *
     * @param id1
     * @return Request
     */
    public static Request<RemindListResult> authCode(String id1) {
        return new GetMethodRequest<RemindListResult>(RemindListResult.class, Config.getAPIHost(), METHOD_AUTH_CODE)
                .addUrlArgument(id1)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }


    /**
     * remindList
     *
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<RemindListResult> remindList(String accessToken) {
        return new GetMethodRequest<RemindListResult>(RemindListResult.class, Config.getAPIHost(), METHOD_REMIND_LIST)
                .addUrlArgument(accessToken)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * remindDel
     *
     * @param accessToken accessToken
     * @param remindId    remindId
     * @return Request
     */
    public static Request<BaseResult> remindDel(String accessToken, String remindId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_REMIND_DEL)
                .addUrlArgument(accessToken)
                .addUrlArgument(remindId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * remindDel
     *
     * @param accessToken accessToken
     * @param remindId    remindId
     * @return Request
     */
    public static Request<BaseResult> markRead(String accessToken, String remindId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_REMIND_MARK_READ)
                .addUrlArgument(accessToken)
                .addUrlArgument(remindId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }
}
