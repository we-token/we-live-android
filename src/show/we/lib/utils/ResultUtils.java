package show.we.lib.utils;

import show.we.lib.model.BaseListResult;

import java.util.List;

/**
 * Result工具
 * @author ll
 * @version 1.0.0
 */
public class ResultUtils {

    /**
     * 合并两个result，去重
     * @param cacheResult cacheResult
     * @param newResult newResult
     * @return BaseListResult
     */
    public static BaseListResult combineResult(BaseListResult cacheResult, BaseListResult newResult) {
        if (cacheResult == null && newResult == null) {
            throw new IllegalArgumentException("combineResult failed!Both result are null!CacheResult:[" + cacheResult + "] newResult:[" + newResult + "]");
        }
        if (newResult != null && cacheResult != null) {
            List<Object> cacheList = cacheResult.getDataList();
            List<Object> newList = newResult.getDataList();
            cacheList.removeAll(newList);
            cacheList.addAll(newList);
            cacheResult.setPage(newResult.getPage());
            cacheResult.setSize(newResult.getSize());
            cacheResult.setDataList(cacheList);
            return cacheResult;
        } else {
            return newResult != null ? newResult : cacheResult;
        }
    }

    /**
     * 获取当前result的页码
     * @param result result
     * @param curSize curSize
     * @return 当前result的页码
     */
    public static int getCurPage(BaseListResult result, int curSize) {
        return result == null ? 1 : result.getPage() * result.getSize() / curSize + 1;
    }
}
