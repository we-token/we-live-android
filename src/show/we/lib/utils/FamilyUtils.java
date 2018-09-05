package show.we.lib.utils;

import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Enums;
import show.we.lib.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by CG on 13-11-15.
 *
 * @author ll
 * @version 3.1.0
 */
public final class FamilyUtils {

    private static final int FAMILY_WEALTH_RANK_REQUEST_TYPE = 5;
    private static final int FAMILY_ALL_RANK_REQUEST_TYPE = 2;
    private static final int FAMILY_FOUND_TIME_REQUEST_TYPE = 3;
    private static final int FAMILY_STAR_RANK_REQUEST_TYPE = 1;

    /**
     * getFamilyListObjectKey
     *
     * @param type type
     * @return FamilyListObjectKey
     */
    public static String getFamilyListObjectKey(Enums.FamilyListType type) {
        String key;
        switch (type) {
            default:
            case ALL_FAMILY:
                key = CacheObjectKey.ALL_FAMILY_LIST;
                break;
            case SUPPORT_RANK_FAMILY:
                key = CacheObjectKey.SUPPORT_RANK_FAMILY_LIST;
                break;
            case STAR_RANK_FAMILY:
                key = CacheObjectKey.STAR_RANK_FAMILY_LIST;
                break;
            case WEALTH_RANK_FAMILY:
                key = CacheObjectKey.WEALTH_RANK_FAMILY_LIST;
                break;
        }
        return key;
    }

    /**
     * getFamilyListRequestType
     *
     * @param type type
     * @return type
     */
    public static int getFamilyListRequestType(Enums.FamilyListType type) {
        int requestType;
        switch (type) {
            default:
            case ALL_FAMILY:
                requestType = FAMILY_FOUND_TIME_REQUEST_TYPE;
                break;
            case SUPPORT_RANK_FAMILY:
                requestType = FAMILY_ALL_RANK_REQUEST_TYPE;
                break;
            case STAR_RANK_FAMILY:
                requestType = FAMILY_STAR_RANK_REQUEST_TYPE;
                break;
            case WEALTH_RANK_FAMILY:
                requestType = FAMILY_WEALTH_RANK_REQUEST_TYPE;
                break;
        }
        return requestType;
    }

    /**
     * getFamilyListResult
     *
     * @param type type
     * @return FamilyListResult
     */
    public static FamilyListResult getFamilyListResult(Enums.FamilyListType type) {
        return (FamilyListResult) CacheUtils.getObjectCache().get(getFamilyListObjectKey(type));
    }

    /**
     * getFamilyListResultCacheTime
     *
     * @param type type
     * @return FamilyListResult
     */
    public static long getFamilyListResultCacheTime(Enums.FamilyListType type) {
        return CacheUtils.getObjectCache().getLastCacheTime(getFamilyListObjectKey(type));
    }

    /**
     * cacheFamilyList
     *
     * @param type   type
     * @param result result
     */
    public static void cacheFamilyList(Enums.FamilyListType type, FamilyListResult result) {
        CacheUtils.getObjectCache().add(getFamilyListObjectKey(type), result);
    }

    /**
     * combineFamilyListResult
     *
     * @param result    原result
     * @param newResult newResult
     */
    public static void combineFamilyListResult(FamilyListResult result, FamilyListResult newResult) {
        List<FamilyListResult.Data> cacheList = result.getDataList();
        List<Long> familyIdSet = new ArrayList<Long>();
        for (FamilyListResult.Data data : cacheList) {
            familyIdSet.add(data.getId());
        }

        List<FamilyListResult.Data> subList = newResult.getDataList();
        for (FamilyListResult.Data data : subList) {
            if (!familyIdSet.contains(data.getId())) {
                cacheList.add(data);
            }
        }
    }

    /**
     * combineFamilyStarListResult
     *
     * @param result    result
     * @param newResult newResult
     */
    public static void combineFamilyStarListResult(FamilyStarListResult result, FamilyStarListResult newResult) {
        List<FamilyStarData> dataList = result.getDataList();
        Set<Long> starIdList = new HashSet<Long>();
        for (FamilyStarData data : dataList) {
            starIdList.add(data.getStar().getId());
        }
        List<FamilyStarData> newDataList = newResult.getDataList();
        for (FamilyStarData data : newDataList) {
            if (!starIdList.contains(data.getStar().getId())) {
                dataList.add(data);
            }
        }
    }

    /**
     * combineFamilyMemberListResult
     *
     * @param result    result
     * @param newResult newResult
     */
    public static void combineFamilyMemberListResult(FamilyMemberListResult result, FamilyMemberListResult newResult) {
        List<FamilyMemberData> dataList = result.getDataList();
        Set<Long> starIdList = new HashSet<Long>();
        for (FamilyMemberData data : dataList) {
            starIdList.add(data.getId());
        }
        List<FamilyMemberData> newDataList = newResult.getDataList();
        for (FamilyMemberData data : newDataList) {
            if (!starIdList.contains(data.getId())) {
                dataList.add(data);
            }
        }
    }

    /**
     * combineFamilyTopicListResult
     *
     * @param result    result
     * @param newResult newResult
     */
    public static void combineFamilyTopicListResult(FamilyTopicListResult result, FamilyTopicListResult newResult) {
        List<FamilyTopicListResult.Data> dataList = result.getDataList();
        List<String> topicIdList = new ArrayList<String>();
        for (FamilyTopicListResult.Data data : dataList) {
            topicIdList.add(data.getId());
        }
        List<FamilyTopicListResult.Data> newDataList = newResult.getDataList();
        for (FamilyTopicListResult.Data data : newDataList) {
            if (!topicIdList.contains(data.getId())) {
                dataList.add(data);
            }
        }
    }
}
