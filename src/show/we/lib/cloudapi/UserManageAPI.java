package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;

/**
 * 用户管理相关API 如将用户剔除房间等
 *
 * @author ll
 * @version 1.0.0
 */
public final class UserManageAPI {

    private static final String METHOD_SHUT_UP = "manage/shutup";
    private static final String METHOD_RECOVER = "manage/recover";
    private static final String METHOD_KICK = "manage/kick";
    private static final String METHOD_ADD_ADMIN = "manage/add_admin";
    private static final String METHOD_DEL_ADMIN = "manage/del_admin";
    private static final String METHOD_ONLINE_MANAGER = "live/online_admins";

    private static final String KEY_MINUTE = "minute";

    
    /**
     * 在线管理列表
     *
     * @param accessToken accessToken
     * @return Request
     */
    public static Request<BaseResult> onLIneManager(String accessToken) {
    	String URL = METHOD_ONLINE_MANAGER + "/" + accessToken;
    	return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), URL)
    			.addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }
    
    /**
     * 请求对某人禁言
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param userId      用户id
     * @param minute      禁言时间
     * @return Request
     */
    public static Request<BaseResult> shutUp(String accessToken, long roomId, long userId, int minute) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_SHUT_UP)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(userId)
                .addArgument(KEY_MINUTE, minute)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }


    /**
     * 请求恢复某人禁言
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param userId      用户id
     * @return Request
     */
    public static Request<BaseResult> recover(String accessToken, long roomId, long xyUserId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_RECOVER)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(xyUserId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求添加某个房间的管理员
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param userId      用户id
     * @return Request
     */
    public static Request<BaseResult> addAdmin(String accessToken, long roomId, long userId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_ADD_ADMIN)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(userId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 踢人
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param userId      用户id
     * @param minute      踢出房间的时间
     * @return Request
     */
    public static Request<BaseResult> kick(String accessToken, long roomId, long userId, int minute) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_KICK)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(userId)
                .addArgument(KEY_MINUTE, minute)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求删除某个房间的管理员
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param userId      用户id
     * @return Request
     */
    public static Request<BaseResult> delAdmin(String accessToken, long roomId, long userId) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_DEL_ADMIN)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addUrlArgument(userId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }
}
