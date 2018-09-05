package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class TopicInfoResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("_id")
        private String mId;
        @SerializedName("badge_name")
        private String mBadgeName;
        @SerializedName("content")
        private String mContent;
        @SerializedName("family_name")
        private String mFamilyName;
        @SerializedName("feeds")
        private int mFeeds;
        @SerializedName("fid")
        private long mFid;
        @SerializedName("replyCount")
        private int mReplyCount;
        @SerializedName("replyTime")
        private long mReplyTime;
        @SerializedName("status")
        private int mStatus;
        @SerializedName("timestamp")
        private long mTimeStamp;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("top")
        private int mTop;
        @SerializedName("uid")
        private long mUid;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;
        @SerializedName("pic")
        private String mPic;
        @SerializedName("priv")
        private int mPriv;
        @SerializedName("week_support")
        private int mWeekSupport;

        public String getTitle() {
            return Utils.html2String(mTitle);
        }

        public String getPic() {
            return mPic;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public String getContent() {
            return Utils.html2String(mContent);
        }
    }
}
