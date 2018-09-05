package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * Created by ll on 14-2-13.
 *
 * @author ll
 * @version 3.5.0
 */
public class YeepayStatusResult extends BaseResult {
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
        @SerializedName("callback")
        private Callback mCallback;

        /**
         * getCallback
         *
         * @return Callback
         */
        public Callback getCallback() {
            return mCallback;
        }
    }

    /**
     * class Callback
     */
    public static class Callback {
        @SerializedName("p8_cardStatus")
        private String mCardStatus;

        /**
         * getCardStatus
         *
         * @return String
         */
        public String getCardStatus() {
            return mCardStatus;
        }
    }
}
