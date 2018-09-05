package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * 经纪人的数据
 *
 * @author ll
 * @version 1.2.0
 */
public class BrokerListResult extends BaseListResult<BrokerListResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mBrokerId;

        @SerializedName("nick_name")
        private String mBrokerName;

        public long getBrokerId() {
            return mBrokerId;
        }

        public String getBrokerName() {
            return Utils.html2String(mBrokerName);
        }
    }
}
