package show.we.lib.cloudapi;

import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.lib.config.Config;
import show.we.lib.model.RankGiftStarResult;
import show.we.lib.model.RankStarResult;
import show.we.lib.model.RankWealthResult;

/**
 * 排行帮
 *
 * @author ll
 * @version 1.0.0
 */
public final class RankAPI {

    /**
     * 请求数据的范围，一天主播排行榜
     */
    public static final String RANGE_DAY_ID = "day";
    /**
     * 请求数据的范围，一周主播排行榜
     */
    public static final String RANGE_WEEK_ID = "week";
    /**
     * 请求数据的范围，上周排行榜
     */
    public static final String RANGE_LAST_WEEK_ID = "last_week";
    /**
     * 请求数据的范围，一月主播排行榜
     */
    public static final String RANGE_MONTH_ID = "month";
    /**
     * 请求数据的范围，总计主播排行榜
     */
    public static final String RANGE_TOTAL_ID = "total";

    private static final String URL_SLAVE = "http://www.51weibo.com";

    private static final String METHOD_RANK = "rank";
    private static final String METHOD_GIFT_RANK = "gift_rank/latest.js";
    private static final String KEY_V = "v";

    /**
     * 财富排行榜异步请求
     *
     * @param rangeId 选择范围， 可选值： RANGE_DAY_ID   RANGE_WEEK_ID  RANGE_MONTH_ID
     * @param size    请求数据的条数
     * @return Http请求实例
     */
    public static Request<RankWealthResult> wealthRank(String rangeId, int size) {
        checkRangeId(rangeId);

        return new GetMethodRequest<RankWealthResult>(RankWealthResult.class, Config.getAPIHost(), METHOD_RANK)
                .addUrlArgument("user_" + rangeId)
                .addUrlArgument(size);
    }

    /**
     * 主播排行榜异步请求
     *
     * @param rangeId 选择范围， 可选值： RANGE_DAY_ID   RANGE_WEEK_ID  RANGE_MONTH_ID
     * @param size    请求数据的条数
     * @return Http请求实例
     */
    public static Request<RankStarResult> starRank(String rangeId, int size) {
        checkRangeId(rangeId);

        return new GetMethodRequest<RankStarResult>(RankStarResult.class, Config.getAPIHost(), METHOD_RANK)
                .addUrlArgument("star_" + rangeId)
                .addUrlArgument(size);
    }

    /**
     * 点播排行榜异步请求
     *
     * @param rangeId 选择范围， 可选值： RANGE_DAY_ID   RANGE_WEEK_ID  RANGE_MONTH_ID
     * @param size    请求数据的条数
     * @return Http请求实例
     */
    public static Request<RankStarResult> songOrderRank(String rangeId, int size) {
        checkRangeId(rangeId);

        return new GetMethodRequest<RankStarResult>(RankStarResult.class, Config.getAPIHost(), METHOD_RANK)
                .addUrlArgument("song_" + rangeId)
                .addUrlArgument(size);
    }

    /**
     * 魅力排行榜异步请求
     *
     * @param rangeId 选择范围， 可选值： RANGE_DAY_ID   RANGE_WEEK_ID  RANGE_MONTH_ID
     * @param size    请求数据的条数
     * @return Http请求实例
     */
    public static Request<RankStarResult> featherRank(String rangeId, int size) {
        checkRangeId(rangeId);

        return new GetMethodRequest<RankStarResult>(RankStarResult.class, Config.getAPIHost(), METHOD_RANK)
                .addUrlArgument("feather_" + rangeId)
                .addUrlArgument(size);
    }

    /**
     * 本周礼物之星异步请求
     *
     * @param size 请求数据的条数
     * @return Http请求实例
     */
    public static Request<RankGiftStarResult> giftStarRank(int size) {
        return new GetMethodRequest<RankGiftStarResult>(RankGiftStarResult.class, Config.getAPIHost(), METHOD_RANK)
                .addUrlArgument("gift_week")
                .addUrlArgument(size);
    }

    private static void checkRangeId(String rangeId) {
        if (!RANGE_DAY_ID.equals(rangeId) && !RANGE_WEEK_ID.equals(rangeId)
                && !RANGE_MONTH_ID.equals(rangeId) && !RANGE_TOTAL_ID.equals(rangeId)) {
            throw new IllegalArgumentException("rangeId must be RANGE_DAY_ID, RANGE_WEEK_ID, RANGE_MONTH_ID, RANGE_TOTAL_ID");
        }
    }

    /**
     * 异步请求一个最新的快照
     *
     * @return Request 实例
     */
    public static Request<RankGiftStarResult> latestGiftStarRank() {
        return new GetMethodRequest<RankGiftStarResult>(RankGiftStarResult.class, URL_SLAVE, METHOD_GIFT_RANK).addArgument(KEY_V, System.currentTimeMillis());
    }
}
