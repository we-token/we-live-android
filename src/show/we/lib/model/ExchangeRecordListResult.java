package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * 充值记录的数据
 *
 * @author ll
 * @version 2.0.0
 */
public class ExchangeRecordListResult extends BaseListResult<ExchangeRecordListResult.Data> {

    public class Data implements Serializable {

        @SerializedName("exchange")
        private long mCoin;

        @SerializedName("timestamp")
        private String mTimestamp;

        /**
         * @return long
         */
        public long getmCoin() {
            return mCoin;
        }

        /**
         * @return String
         */
        public String getTimestamp() {
            return mTimestamp;
        }

        /**
         * @param timestamp String
         */
        public void setTimestamp(String timestamp) {
            this.mTimestamp = timestamp;
        }
    }
}
