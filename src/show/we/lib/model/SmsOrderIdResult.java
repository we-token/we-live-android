package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 短信支付订单数据结构
 *
 * @author ll
 * @version 1.2.0
 */
public class SmsOrderIdResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("url")
        private String mUrl;

        public String getUrl() {
            return mUrl;
        }
    }
}
