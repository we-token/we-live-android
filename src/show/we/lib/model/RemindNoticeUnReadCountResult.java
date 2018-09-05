package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

/**
 * 未读消息\提醒数量
 * @author ll
 * @version 1.0.0
 */
public class RemindNoticeUnReadCountResult extends BaseResult {

    @SerializedName("data")
    private int mUnReadCount;

    public int getUnReadCount() {
        return mUnReadCount;
    }
}
