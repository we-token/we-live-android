package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * Created by ktv12 on 14-5-16.
 */
public class FirstRechargeAwardResult extends BaseResult {
    @SerializedName("data")
    private Data mData;
    /**
     * getdata
     *
     * @return Data
     */
    public Data getData() {
        return mData;
    }

    /**
     * class Data
     */
    public static class Data {
        @SerializedName("flag")
        private boolean mFlag;

        @SerializedName("info")
        private String[] mInfo;

        @SerializedName("url")
        private String mUrl;

        public String getmUrl() {
            return mUrl;
        }

        public boolean ismFlag() {
            return mFlag;
        }

        public String[] getmInfo() {
            return mInfo;
        }
    }
}
