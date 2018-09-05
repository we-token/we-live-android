package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;

import java.io.Serializable;

/**
 * Created by Administrator on 2014/5/28.
 * @version all
 * @author ll
 */

public class FamilyMemberApplyRecord extends BaseListResult<FamilyMemberApplyRecord.MemberApplyRecord> {

    public class MemberApplyRecord implements Serializable {
        @SerializedName("_id")
        private String mApplyId;

        @SerializedName("family_id")
        private long mFamilyId;

        @SerializedName("lastmodif")
        private long mLastModify;

        @SerializedName("status")
        private int mStatus;

        @SerializedName("xy_user_id")
        private long mXYUserId;

        @SerializedName("family_pic")
        private String mFamilyPic;

        @SerializedName("family_name")
        private String mFamilyName;

        public String getFamilyName() {
            return mFamilyName;
        }

        public void setFamilyName(String familyName) {
            this.mFamilyName = familyName;
        }

        public String getApplyId() {
            return mApplyId;
        }

        public void setmApplyId(String applyId) {
            this.mApplyId = applyId;
        }

        public long getFamilyId() {
            return mFamilyId;
        }

        public void setmFamilyId(long familyId) {
            this.mFamilyId = familyId;
        }

        public long getLastModify() {
            return mLastModify;
        }

        public void setLastModify(long lastModify) {
            this.mLastModify = lastModify;
        }

        private int getStatus() {
            return mStatus;
        }

        public void setStatus(int status) {
            this.mStatus = status;
        }

        public long getXYUserId() {
            return mXYUserId;
        }

        public void setXYUserId(long xYUserId) {
            this.mXYUserId = xYUserId;
        }

        public String getFamilyPic() {
            return mFamilyPic;
        }

        public void setFamilyPic(String familyPic) {
            this.mFamilyPic = familyPic;
        }

        public Enums.MemerApplyStatus getApplyStatus() {
            for (Enums.MemerApplyStatus status : Enums.MemerApplyStatus.values()) {
                if (status.getValue() == mStatus) {
                    return status;
                }
            }
            return Enums.MemerApplyStatus.NONE;
        }
    }
}
