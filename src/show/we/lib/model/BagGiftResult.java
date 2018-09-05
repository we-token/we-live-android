package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.Map;

/**
 * 背包礼物数据
 *
 * @author ll
 * @version 1.0.1
 */
public class BagGiftResult extends BaseResult implements Serializable {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("bag")
        private Map<Object, Object> mBagMap;

        public long getId() {
            return mId;
        }

        public Map<Object, Object> getBagMap() {
            return mBagMap;
        }
    }
}
