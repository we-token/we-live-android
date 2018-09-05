package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * 用户喇叭数据
 *
 * @author ll
 * @version 2.2.0
 */
public class HornResult extends BaseResult implements Serializable {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("horn")
        private long mHorn;

        public long getId() {
            return mId;
        }

        public long getHorn() {
            return mHorn;
        }

        public void setHorn(long mHorn) {
            this.mHorn = mHorn;
        }
    }
}
