package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 禁言数据结构
 *
 * @author ll
 * @version 1.0.0
 */
public class TTLResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("ttl")
        private long mTTL;

        public long getTTL() {
            return mTTL;
        }
    }
}
