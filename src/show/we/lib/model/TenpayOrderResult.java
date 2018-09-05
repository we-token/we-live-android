package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * Created by CG on 13-11-14.
 *
 * @author ll
 * @version 3.1.0
 */
public class TenpayOrderResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("token_id")
        private String mTokenId;

        @SerializedName("order_id")
        private String mOrderId;

        @SerializedName("bargainor_id")
        private String mBargainorId;

        public String getOrderId() {
            return mOrderId;
        }

        public String getTokenId() {
            return mTokenId;
        }

        public String getBargainorId() {
            return mBargainorId;
        }
    }
}
