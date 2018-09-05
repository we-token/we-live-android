package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

/**
 * Created by CG on 14-1-8.
 *
 * @author ll
 * @version 3.4.0
 */
public class OfficialTopicInfoResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("_id")
        private String mId;
        @SerializedName("content")
        private String mContent;
        @SerializedName("label")
        private String mLabel;
        @SerializedName("official_uid")
        private String mOfficialIds;
        @SerializedName("replyCount")
        private int mReplyCount;
        @SerializedName("replyTime")
        private long mReplyTime;
        @SerializedName("timestamp")
        private long mTimeStamp;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("uid")
        private long mUserId;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;
        @SerializedName("pic")
        private String mPic;

        public String getId() {
            return mId;
        }

        public String getContent() {
            return Utils.html2String(mContent);
        }

        public String getLabel() {
            return mLabel;
        }

        public String getOfficialIds() {
            return mOfficialIds;
        }

        public int getReplyCount() {
            return mReplyCount;
        }

        public long getReplyTime() {
            return mReplyTime;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public String getTitle() {
            return Utils.html2String(mTitle);
        }

        public long getUserId() {
            return mUserId;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPic() {
            return mPic;
        }
    }
}
