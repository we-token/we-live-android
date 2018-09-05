package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 订单数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class UnionpayOrderNumberResult extends BaseResult {
    @SerializedName("data")  //data 为银联返回的tn
    private String mOrderId;

    public String getOrderId() {
        return mOrderId;
    }
}
