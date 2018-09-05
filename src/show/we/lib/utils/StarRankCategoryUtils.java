package show.we.lib.utils;

import show.we.sdk.request.Request;
import show.we.sdk.util.ConstantUtils;
import show.we.lib.cloudapi.RankAPI;
import show.we.lib.config.Enums;
import show.we.lib.model.RankStarResult;

/**
 * Created by CG on 13-10-29.
 *
 * @author ll
 * @version 3.0.0
 */
public class StarRankCategoryUtils {

    private static final int TOP_10 = 10;

    /**
     * getStarRankRequest
     *
     * @param type   人气、点歌、魅力
     * @param rankId 日榜、周榜、月榜、总榜
     * @return Request
     */
    public static Request<RankStarResult> getStarRankRequest(Enums.StarRankType type, String rankId) {
        Request<RankStarResult> request;
        switch (type) {
            default:
            case RANK_STAR_TOP:
                request = RankAPI.starRank(rankId, TOP_10);
                break;
            case RANK_FEATHER_TOP:
                request = RankAPI.featherRank(rankId, TOP_10);
                break;
            case RANK_SONG_ORDER:
                request = RankAPI.songOrderRank(rankId, TOP_10);
                break;
        }
        return request;
    }

    /**
     * getObjectCacheKey
     *
     * @param type   type
     * @param rankId rankId
     * @return ObjectCacheKey
     */
    public static String getObjectCacheKey(Enums.StarRankType type, String rankId) {
        return type.getValue() + rankId;
    }

    /**
     * getStarRankResult
     *
     * @param type   type
     * @param rankId rankId
     * @return RankStarResult
     */
    public static RankStarResult getStarRankResult(Enums.StarRankType type, String rankId) {
        return (RankStarResult) CacheUtils.getObjectCache().get(getObjectCacheKey(type, rankId));
    }

    /**
     * getStarRankResultCacheTime
     *
     * @param type   type
     * @param rankId rankId
     * @return RankStarResult
     */
    public static long getStarRankResultCacheTime(Enums.StarRankType type, String rankId) {
        return CacheUtils.getObjectCache().getLastCacheTime(getObjectCacheKey(type, rankId));
    }

    /**
     * saveStarRankResult
     *
     * @param type   type
     * @param rankId rankId
     * @param result result
     */
    public static void saveStarRankResult(Enums.StarRankType type, String rankId, RankStarResult result) {
        CacheUtils.getObjectCache().add(getObjectCacheKey(type, rankId), result, ConstantUtils.MILLS_PER_DAY);
    }
}
