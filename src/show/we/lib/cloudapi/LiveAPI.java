package show.we.lib.cloudapi;

import show.we.sdk.request.BaseResult;
import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.sdk.util.EnvironmentUtils;
import show.we.lib.config.Config;
import show.we.lib.config.Enums;
import show.we.lib.model.*;


/**
 * 直播相关API
 *
 * @author ll
 * @version 1.0.0
 */
public final class LiveAPI {
    private static final String METHOD_AUDIENCE = "public/room_viewer";
    private static final String METHOD_KICK_TTL = "user/kick_ttl";
    private static final String METHOD_SHUT_UP_TTL = "user/shutup_ttl";
    private static final String METHOD_EXPENSE_RANK = "rank";
    private static final String ACTIVE_RANK = "activevalue/active_rank";
    private static final String METHOD_STAR_INFO = "public/room_star";
    private static final String METHOD_ADMIN_INFO = "public/room_admin";
    private static final String METHOD_GIFT_MARQUEE = "public/gift_marquee";
    private static final String METHOD_JIGSAW = "live/puzzle_win";
    

    private static final String KEY_PAGE = "page";
    private static final String KEY_SIZE = "size";

    /**
     * 房间观众异步请求
     *
     * @param roomId 房间id
     * @param page   页号（从0开始）
     * @param size   页的大小
     * @return Request
     */
    public static Request<AudienceResult> audience(long roomId, int page, int size) {
        return new GetMethodRequest<AudienceResult>(AudienceResult.class, Config.getAPIHost(), METHOD_AUDIENCE)
                .addUrlArgument(roomId)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }
    
  

    /**
     * 消费排行异步请求
     *
     * @param expenseType 消费类型（本场消费、本周消费、本月消费）
     * @param roomId      房间id
     * @param size        请求的（粉丝）最大数量
     * @return Request
     */
    public static Request<RankSpendResult> expenseRank(Enums.ExpenseType expenseType, long roomId, int size) {
        return new GetMethodRequest<RankSpendResult>(RankSpendResult.class, Config.getAPIHost(), METHOD_EXPENSE_RANK)
                .addUrlArgument("room_user_" + expenseType.getValue())
                .addUrlArgument(roomId)
                .addArgument(KEY_SIZE, size);
    }

    /**
     * 管理员列表
     * 哈哈
     * @param roomId 房间id
     * @return Request
     */
    public static Request<AudienceResult> adminInfo(long roomId) {
        return new GetMethodRequest<AudienceResult>(AudienceResult.class, Config.getAPIHost(), METHOD_ADMIN_INFO)
                .addUrlArgument(roomId);
    }

    /**
     * 主播信息异步请求
     *
     * @param roomId 房间id
     * @return Request
     */
    public static Request<StarInfoResult> starInfo(long roomId) {
        return new GetMethodRequest<StarInfoResult>(StarInfoResult.class, Config.getAPIHost(), METHOD_STAR_INFO)
                .addUrlArgument(roomId);
    }

    /**
     * 获取最新的踢出房间列表
     *
     * @param accessToken accessToken
     * @param roomId      房间列表
     * @return Request
     */
    public static Request<TTLResult> kickTTL(String accessToken, long roomId) {
        return new GetMethodRequest<TTLResult>(TTLResult.class, Config.getAPIHost(), METHOD_KICK_TTL)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求禁言的用户列表
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @return Request
     */
    public static Request<TTLResult> shutUpTTL(String accessToken, long roomId) {
        return new GetMethodRequest<TTLResult>(TTLResult.class, Config.getAPIHost(), METHOD_SHUT_UP_TTL)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 请求拼图完成
     *
     * @param accessToken accessToken
     * @param roomId      房间id
     * @param step        用了多少步
     * @return Request
     */
    public static Request<BaseResult> jigsawCompleted(String accessToken, long roomId, int step) {
        return new GetMethodRequest<BaseResult>(BaseResult.class, Config.getAPIHost(), METHOD_JIGSAW)
                .addUrlArgument(accessToken)
                .addUrlArgument(roomId)
                .addArgument("step", step)
                .addArgument("qd", EnvironmentUtils.GeneralParameters.parameters().get("f"));
    }

    /**
     * 最近跑马灯
     *
     * @param size 大小
     * @return BaseResult
     */
    public static Request<GiftMarqueeResult> giftMarquee(int size) {
        return new GetMethodRequest<GiftMarqueeResult>(GiftMarqueeResult.class, Config.getAPIHost(), METHOD_GIFT_MARQUEE)
                .addUrlArgument(size);
    }

    /**
     * 房间活跃排行
     *
     * @param roomId roomId
     * @param size   size
     * @return Request
     */
    public static Request<ActiveRankResult> activeRank(long roomId, int size) {
        return new GetMethodRequest<ActiveRankResult>(ActiveRankResult.class, Config.getAPIHost(), ACTIVE_RANK)
                .addUrlArgument(roomId)
                .addArgument(KEY_SIZE, size);
    }
}
