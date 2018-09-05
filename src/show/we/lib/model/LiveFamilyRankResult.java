package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CG on 13-12-1.
 *
 * @author ll
 * @version 3.2.0
 */
public class LiveFamilyRankResult extends BaseListResult<LiveFamilyRankResult.Data> {

    public static class Data {
        @SerializedName("_id")
        private long mId;
        @SerializedName("badge_name")
        private String mBadgeName;
        @SerializedName("family_name")
        private String mFamilyName;
        @SerializedName("family_pic")
        private String mFamilyPic;
        @SerializedName("timestamp")
        private long mTimeStamp;
        @SerializedName("rank")
        private int mRank;
        @SerializedName("total_cost")
        private long mTotalCost;

        public long getId() {
            return mId;
        }

        public String getBadgeName() {
            return mBadgeName;
        }

        public String getFamilyName() {
            return mFamilyName;
        }

        public String getFamilyPic() {
            return mFamilyPic;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public int getRank() {
            return mRank;
        }

        public long getTotalCost() {
            return mTotalCost;
        }
    }
}
