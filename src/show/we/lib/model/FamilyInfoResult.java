package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG on 13-11-14.
 *
 * @author ll
 * @version 3.1.0
 */
public class FamilyInfoResult extends BaseResult {

    @SerializedName("family_info")
    private Data mData;

    @SerializedName("family_members")
    private List<FamilyMemberData> mFamilyMembers;

    @SerializedName("family_stars")
    private List<FamilyStarData> mFamilyStars;

    public Data getData() {
        return mData;
    }

    public List<FamilyMemberData> getFamilyMembers() {
        return mFamilyMembers == null ? new ArrayList<FamilyMemberData>() : mFamilyMembers;
    }

    public List<FamilyStarData> getFamilyStars() {
        return mFamilyStars == null ? new ArrayList<FamilyStarData>() : mFamilyStars;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("badge_name")
        private String mBadgeName;

        @SerializedName("family_name")
        private String mFamilyName;

        @SerializedName("family_notice")
        private String mFamilyNotice;

        @SerializedName("family_pic")
        private String mFamilyPic;

        @SerializedName("lastmodif")
        private long mLastModify;

        @SerializedName("leader_id")
        private long mLeaderId;

        @SerializedName("member_count")
        private int mMemberCount;

        @SerializedName("rank_cost")
        private int mRankCost;

        @SerializedName("rank_num")
        private int mRankCount;

        @SerializedName("rank_support")
        private int mRankSupport;

        @SerializedName("star_count")
        private int mStarCount;

        @SerializedName("status")
        private int mStatus;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("big_leaders")
        private FamilyMemberData mBigLeader;

        @SerializedName("leaders")
        private List<FamilyMemberData> mLeaders;

        @SerializedName("topic_count")
        private int mTopicCount;

        public long getId() {
            return mId;
        }

        public String getBadgeName() {
            return Utils.html2String(mBadgeName);
        }

        public String getFamilyName() {
            return Utils.html2String(mFamilyName);
        }

        public String getFamilyNotice() {
            return Utils.html2String(mFamilyNotice);
        }

        public String getFamilyPic() {
            return mFamilyPic;
        }

        public void setFamilyPic(String familyPic) {
            this.mFamilyPic = familyPic;
        }

        public void setFamilyNotice(String familyNotice) {
            this.mFamilyNotice = familyNotice;
        }

        public long getLastModify() {
            return mLastModify;
        }

        public long getLeaderId() {
            return mLeaderId;
        }

        public int getMemberCount() {
            return mMemberCount;
        }

        public void setMemberCount(int memberCount) {
            this.mMemberCount = memberCount;
        }

        public int getRankCost() {
            return mRankCost;
        }

        public int getRankCount() {
            return mRankCount;
        }

        public int getRankSupport() {
            return mRankSupport;
        }

        public int getStarCount() {
            return mStarCount;
        }

        public int getStatus() {
            return mStatus;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public FamilyMemberData getBigLeader() {
            return mBigLeader;
        }

        public List<FamilyMemberData> getLeaders() {
            return mLeaders == null ? new ArrayList<FamilyMemberData>() : mLeaders;
        }

        public int getTopicCount() {
            return mTopicCount;
        }

        public void setTopicCount(int topicCount) {
            this.mTopicCount = topicCount;
        }

        public void setStarCount(int starCount) {
            this.mStarCount = starCount;
        }
    }
}
