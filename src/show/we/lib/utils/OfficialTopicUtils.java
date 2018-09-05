package show.we.lib.utils;

import show.we.sdk.util.StringUtils;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Enums;
import show.we.lib.model.RemindListResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG on 14-1-7.
 *
 * @author ll
 * @version 3.4.0
 */
public class OfficialTopicUtils {

    /**
     * isOfficial
     *
     * @param officialUserIds officialUserIds
     * @param userId          userId
     * @return isOfficial
     */
    public static boolean isOfficial(String officialUserIds, String userId) {
        if (StringUtils.isEmpty(officialUserIds)) {
            officialUserIds = (String)CacheUtils.getObjectCache().get(CacheObjectKey.OFFICIAL_USER_ID);
        } else {
            CacheUtils.getObjectCache().add(CacheObjectKey.OFFICIAL_USER_ID, officialUserIds);
        }
        if (!StringUtils.isEmpty(officialUserIds)) {
            String[] officialIds = officialUserIds.split("_");
            for (String id : officialIds) {
                if (id.equals(userId)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * saveRemind
     *
     * @param result result
     */
    public static void saveRemind(RemindListResult result) {
        List<RemindListResult.Data> officialData = new ArrayList<RemindListResult.Data>();
        List<RemindListResult.Data> familyData = new ArrayList<RemindListResult.Data>();
        for (RemindListResult.Data data : result.getDataList()) {
            if (data.getTopicType() == Enums.TopicType.OFFICIAL_TOPIC) {
                officialData.add(data);
            } else {
                familyData.add(data);
            }
        }
        RemindListResult officialResult = new RemindListResult();
        officialResult.setDataList(officialData);
        CacheUtils.getObjectCache().add(CacheObjectKey.OFFICIAL_REMIND, officialResult);
        RemindListResult familyResult = new RemindListResult();
        familyResult.setDataList(familyData);
        CacheUtils.getObjectCache().add(CacheObjectKey.FAMILY_REMIND, familyResult);
    }
}
