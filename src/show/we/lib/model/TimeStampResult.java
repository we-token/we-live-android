package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 时间戳数据结构
 *
 * @author ll
 * @version 1.0.0
 */
public class TimeStampResult extends BaseResult {
    @SerializedName("data")
    private String mData;

    public String getData() {
        return mData;
    }
}
