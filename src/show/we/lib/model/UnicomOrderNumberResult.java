package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 订单数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class UnicomOrderNumberResult extends BaseResult {
    @SerializedName("data")
    private String mOrderId;

    /**
     * getOrderId
     *
     * @return String
     */
    public String getOrderId() {
        return mOrderId;
    }
}
