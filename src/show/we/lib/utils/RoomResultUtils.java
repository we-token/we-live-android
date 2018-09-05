package show.we.lib.utils;

import show.we.sdk.util.ConstantUtils;
import show.we.lib.config.Enums;
import show.we.lib.model.RoomListResult;

import java.util.List;
import java.util.Random;
/**
 * 处理的业务过多，所以抽离
 * Created by CG on 13-10-24.
 *
 * @author ll
 * @version 3.0.0
 */
public class RoomResultUtils {

    private static final int RANDOM_SEED_ONE = 100;
    private static final int RANDOM_SEED_TWO = 50;

    /**
     * 伪造房间人数数据
     *
     * @param roomListResult 房间列表
     */
    public static void fakeAudienceCount(final RoomListResult roomListResult) {
        List<RoomListResult.Data> rooms = roomListResult.getDataList();
        for (RoomListResult.Data room : rooms) {
            int count = room.getVisitorCount();
            if (room.getIsLive()) {
                count = fakeCount(count);
                room.setVisitorCount(count);
            }
        }
    }

    /**
     * 随机人数
     *
     * @param srcCount 实际人数
     * @return 伪造后人数
     */
    public static int fakeCount(int srcCount) {
        Random random = new Random(System.currentTimeMillis());
        int randNum = random.nextInt() % RANDOM_SEED_ONE;
        randNum = randNum < 0 ? (RANDOM_SEED_ONE + randNum) : randNum;

        int fakeCount;
        final int factor1 = 68;
        final int factor2 = 80;
        final int baseLine = 20;
        if (srcCount >= RANDOM_SEED_TWO) {
            fakeCount = srcCount * factor1;
        } else if (srcCount >= baseLine) {
            fakeCount = srcCount * factor2;
        } else {
            fakeCount = srcCount * ConstantUtils.HUNDRED;
        }

        return fakeCount + randNum;
    }

    /**
     * cacheRoomListResult
     *
     * @param type           type
     * @param roomListResult roomListResult
     */
    public static void cacheRoomListResult(Enums.RoomListType type, RoomListResult roomListResult) {
        switch (type) {
            case ALL:
                CacheUtils.getObjectCache().add(type.getValue(), roomListResult);
                break;
            case RECOMMEND:
            case NEW:
            case LATEST:
            case ALL_RANK_STAR:
            case SUPER_STAR:
            case GIANT_STAR:
            case BRIGHT_STAR:
            case RED_STAR:
                CacheUtils.getObjectCache().add(type.getValue(), roomListResult, ConstantUtils.MILLS_PER_DAY);
                break;
            default:
                break;
        }
    }

    /**
     * getRoomList
     *
     * @param type type
     * @return RoomListResult
     */
    public static RoomListResult getRoomList(Enums.RoomListType type) {
        return (RoomListResult) CacheUtils.getObjectCache().get(type.getValue());
    }

    /**
     * getRoomListCacheTime
     *
     * @param type type
     * @return RoomListResult
     */
    public static long getRoomListCacheTime(Enums.RoomListType type) {
        return CacheUtils.getObjectCache().getLastCacheTime(type.getValue());
    }
}
