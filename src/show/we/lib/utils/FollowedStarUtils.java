package show.we.lib.utils;

import show.we.lib.config.CacheObjectKey;
import show.we.lib.model.FavStarListResult;
import show.we.lib.model.RecentlyViewStarListResult;
import show.we.lib.model.RoomListResult;

/**
 * Created by CG on 13-10-28.
 *
 * @author ll
 * @version 3.0.0
 */
public class FollowedStarUtils {

    /**
     * @param starId 主播Id
     * @return 返回是否为我关注的主播
     */
    public static boolean isMyFavStar(long starId) {
        return Utils.getFavStarIdList().contains(starId);
    }

    /**
     * 更新关注主播列表开播状态
     *
     * @param roomListResult 最新的房间列表信息
     */
    public static void updateFavStarList(RoomListResult roomListResult) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.FAV_STAR_LIST_KEY) && roomListResult != null) {
            for (RoomListResult.Data data : roomListResult.getDataList()) {
                for (FavStarListResult.StarInfo starInfo : ((FavStarListResult) CacheUtils.getObjectCache().
                        get(CacheObjectKey.FAV_STAR_LIST_KEY)).getData().getStarInfoList()) {
                    if (starInfo.getUser().getId() == data.getId()) {
                        starInfo.getRoom().setIsLive(data.getIsLive());
                        starInfo.getRoom().setVisitorCount(data.getVisitorCount());
                    }
                }
            }
        }
    }

    /**
     * updateFavStarAndRecentlyViewStar
     *
     * @param starId starId
     * @param isLive isLive
     */
    public static void updateFavStarAndRecentlyViewStar(long starId, boolean isLive) {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.FAV_STAR_LIST_KEY)) {
            for (FavStarListResult.StarInfo starInfo : ((FavStarListResult) CacheUtils.getObjectCache()
                    .get(CacheObjectKey.FAV_STAR_LIST_KEY)).getData().getStarInfoList()) {
                if (starInfo.getRoom().getId() == starId) {
                    starInfo.getRoom().setIsLive(isLive);
                }
            }
        }
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY)) {
            for (RecentlyViewStarListResult.User starInfo : ((RecentlyViewStarListResult) CacheUtils.getObjectCache()
                    .get(CacheObjectKey.RECENTLY_VIEW_STAR_LIST_KEY)).getUsers()) {
                if (starInfo.getStarId() == starId) {
                    starInfo.setIsLive(isLive);
                    break;
                }
            }
        }
    }
}
