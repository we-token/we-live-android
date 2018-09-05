package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 充值记录的数据
 *
 * @author ll
 * @version 2.0.0
 */
public class RechargeRecordListResult extends BaseListResult<RechargeRecordListResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("cny")
        private double mCny;

        @SerializedName("coin")
        private long mCoin;

        @SerializedName("timestamp")
        private String mTimestamp;

        @SerializedName("via_desc")
        private String mVia;

        /**
         * @return double
         */
        public double getCny() {
            return mCny;
        }

        /**
         * @return long
         */
        public long getCoin() {
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

        /**
         * @return String
         */
        public String getVia() {
            return mVia;
        }

        /**
         * @param via String
         */
        public void setVia(String via) {
            this.mVia = via;
        }
    }
}
