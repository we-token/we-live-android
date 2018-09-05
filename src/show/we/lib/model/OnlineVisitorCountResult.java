package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * Created by Administrator on 13-7-4.
 *
 * @author ll
 * @version 2.1.0
 */
public class OnlineVisitorCountResult extends BaseResult implements Serializable {

    @SerializedName("data")
    private Data mData;

    /**
     * getData
     *
     * @return Data
     */
    public Data getData() {
        return mData;
    }

    /**
     * OnlineVisitorCountResult : Data
     */
    public static class Data implements Serializable {
        @SerializedName("count")
        private long mOnlineVisitorCount;

        /**
         * getOnlineVisitorCount
         *
         * @return OnlineVisitorCount
         */
        public long getOnlineVisitorCount() {
            return mOnlineVisitorCount;
        }

        /**
         * setOnlineVisitorCount
         *
         * @param onlineVisitorCount onlineVisitorCount
         */
        public void setOnlineVisitorCount(long onlineVisitorCount) {
            mOnlineVisitorCount = onlineVisitorCount;
        }
    }
}
