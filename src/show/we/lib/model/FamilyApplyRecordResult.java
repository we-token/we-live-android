package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/4/24.
 * @author ll
 * @version all
 */
public class FamilyApplyRecordResult extends BaseListResult<FamilyApplyRecordResult.Data> {

    public class Data implements Serializable {
        @SerializedName("_id")
        private String mRecordId;

        @SerializedName("xy_user_id")
        private long mXYUserId;

        @SerializedName("status")
        private int mStatu;

        @SerializedName("family_id")
        private long mFamilyId;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("lastmodif")
        private long mLastModif;

        public String getRecordId() {
            return mRecordId;
        }

        public void setRecordId(String recordId) {
            this.mRecordId = recordId;
        }

        public long getXYUserId() {
            return mXYUserId;
        }

        public void setXYUserId(long xyUserId) {
            this.mXYUserId = xyUserId;
        }

        public int getStatus() {
            return mStatu;
        }

        public void setStatu(int statu) {
            this.mStatu = statu;
        }

        public long getFamilyId() {
            return mFamilyId;
        }

        public void setFamilyId(long familyId) {
            this.mFamilyId = familyId;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.mTimeStamp = timeStamp;
        }

        public long getLastModif() {
            return mLastModif;
        }

        public void setLastModif(long lastModif) {
            this.mLastModif = lastModif;
        }
    }
}
