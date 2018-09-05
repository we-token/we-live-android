package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 订单数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class AlipayOrderNumberResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("user_id")
        private int mUserId;

        @SerializedName("order_id")
        private String mOrderId;

        @SerializedName("order_info")
        private String mOrderInfo;

        public long getUserId() {
            return mUserId;
        }

        public String getOrderId() {
            return mOrderId;
        }

        public String getOrderInfo() {
            return mOrderInfo;
        }
    }
}
