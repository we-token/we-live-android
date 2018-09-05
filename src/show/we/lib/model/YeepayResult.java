package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * Created by ll on 14-2-13.
 *
 * @author ll
 * @version 3.5.0
 */
public class YeepayResult extends BaseResult {
    @SerializedName("order_id")
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
