package show.we.lib.cloudapi;

import show.we.sdk.request.GetMethodRequest;
import show.we.sdk.request.Request;
import show.we.lib.config.Config;
import show.we.lib.model.HistoryWonderGiftResult;
import show.we.lib.model.WonderGiftResult;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-6-27
 *          Time: 下午1:49
 *          奇迹礼物相关的API
 */
public final class WonderGiftAPI {

    private static final String METHOD_TODAY_GIFT = "show/today_special_gift";
    private static final String METHOD_YESTERDAY_GIFT = "show/yesterday_special_gift";
    private static final String METHOD_HISTORY_GIFT = "show/history_special_gift";

    private static final String KEY_PAGE = "page";
    private static final String KEY_SIZE = "size";

    /**
     * @param mRoomId 房间号
     * @return 今日奇迹礼物数据请求
     */
    public static Request<WonderGiftResult> todayGiftResult(long mRoomId) {
        return new GetMethodRequest<WonderGiftResult>(WonderGiftResult.class, Config.getAPIHost(), METHOD_TODAY_GIFT)
                .addUrlArgument(mRoomId);
    }

    /**
     * @return 昨日奇迹礼物请求
     */
    public static Request<WonderGiftResult> yesterdayGiftResult() {
        return new GetMethodRequest<WonderGiftResult>(WonderGiftResult.class, Config.getAPIHost(), METHOD_YESTERDAY_GIFT);
    }

    /**
     * 奇迹礼物排行榜请求
     *
     * @param page page
     * @param size size
     * @return 奇迹礼物排行榜请求
     */
    public static Request<HistoryWonderGiftResult> weekWonderGiftRankResult(int page, int size) {
        return new GetMethodRequest<HistoryWonderGiftResult>(HistoryWonderGiftResult.class, Config.getAPIHost(), METHOD_HISTORY_GIFT)
                .addArgument(KEY_PAGE, page)
                .addArgument(KEY_SIZE, size);
    }
}
