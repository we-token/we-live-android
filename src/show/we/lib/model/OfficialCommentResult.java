package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class OfficialCommentResult extends BaseListResult<OfficialCommentResult.Data> {

    public static class Data {
        @SerializedName("_id")
        private String mId;
        @SerializedName("content")
        private String mContent;
        @SerializedName("uid")
        private long mUid;
        @SerializedName("type")
        private int mType;
        @SerializedName("timestamp")
        private long mTimeStamp;
        @SerializedName("r_nick_name")
        private String mReplyNickName;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;
        @SerializedName("pic")
        private String mPic;
        @SerializedName("priv")
        private int mPriv;
        @SerializedName("official_uid")
        private String mOfficialIds;

        public String getId() {
            return mId;
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

        public String getReplyNickName() {
            return Utils.html2String(mReplyNickName);
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public String getContent() {
            return Utils.html2String(mContent);
        }

        public String getOfficialIds() {
            return mOfficialIds;
        }

        public long getUserId() {
            return mUid;
        }

        public int getPrivType() {
            return mPriv;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;

            Data data = (Data) o;

            if (mId != null ? !mId.equals(data.mId) : data.mId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mId != null ? mId.hashCode() : 0;
        }
    }
}
