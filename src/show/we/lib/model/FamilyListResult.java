package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * Created by CG on 13-11-14.
 *
 * @author ll
 * @version 3.1.0
 */
public class FamilyListResult extends BaseListResult<FamilyListResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("lastmodif")
        private long mLastModify;

        @SerializedName("status")
        private int mStatus;

        @SerializedName("family_name")
        private String mFamilyName;

        @SerializedName("badge_name")
        private String mBadgeName;

        @SerializedName("family_pic")
        private String mFamilyPic;

        @SerializedName("family_notice")
        private String mFamilyNotice;

        @SerializedName("member_count")
        private int mMemberCount;

        @SerializedName("star_count")
        private int mStarCount;

        @SerializedName("rank_cost")
        private int mRankCost;

        @SerializedName("rank_support")
        private int mRankSupport;

        @SerializedName("rank_num")
        private int mRankNum;

        @SerializedName("week_support")
        private int mWeekSupport;

        @SerializedName("leader_id")
        private long mLeaderId;

        @SerializedName("leader_nick_name")
        private String mLeaderName;

        @SerializedName("leaders")
        private Leaders mLeaders;

        @SerializedName("topic_count")
        private int mTopicCount;

        public long getId() {
            return mId;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public long getLastModify() {
            return mLastModify;
        }

        public int getStatus() {
            return mStatus;
        }

        public String getFamilyName() {
            return Utils.html2String(mFamilyName);
        }

        public String getBadgeName() {
            return Utils.html2String(mBadgeName);
        }

        public String getFamilyPic() {
            return mFamilyPic;
        }

        public String getFamilyNotice() {
            return Utils.html2String(mFamilyNotice);
        }

        public int getMemberCount() {
            return mMemberCount;
        }

        public int getStarCount() {
            return mStarCount;
        }

        public int getRankCost() {
            return mRankCost;
        }

        public int getRankSupport() {
            return mRankSupport;
        }

        public int getRankNum() {
            return mRankNum;
        }

        public int getWeekSupport() {
            return mWeekSupport;
        }

        public long getLeaderId() {
            return mLeaderId;
        }

        public String getLeaderName() {
            return Utils.html2String(mLeaderName);
        }

        public Leaders getLeaders() {
            return mLeaders == null ? new Leaders() : mLeaders;
        }

        public int getTopicCount() {
            return mTopicCount;
        }

        public void setTopicCount(int topicCount) {
            this.mTopicCount = mTopicCount;
        }
    }

    public static class Leaders implements Serializable {
        @SerializedName("count")
        private int mCount;

        public int getCount() {
            return mCount;
        }
    }
}
