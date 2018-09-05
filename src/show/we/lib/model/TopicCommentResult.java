package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class TopicCommentResult extends BaseListResult<TopicCommentResult.Data> {

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
        @SerializedName("family")
        private Family mFamily;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;
        @SerializedName("pic")
        private String mPic;
        @SerializedName("priv")
        private int mPriv;
        @SerializedName("quote_content")
        private String mQuoteContent;

        /**
         * @return mQuoteContent
         */
        public String getmQuoteContent() {
            return mQuoteContent;
        }

        /**
         * @return mId
         */
        public String getId() {
            return mId;
        }

        /**
         * @return mPic
         */
        public String getPic() {
            return mPic;
        }

        /**
         * @return mFinance
         */
        public Finance getFinance() {
            return mFinance;
        }

        /**
         * @return mNickName
         */
        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        /**
         * @return mReplyNickName
         */
        public String getReplyNickName() {
            return Utils.html2String(mReplyNickName);
        }

        /**
         * @return mTimeStamp
         */
        public long getTimeStamp() {
            return mTimeStamp;
        }

        /**
         * @return Content
         */
        public String getContent() {
            return Utils.html2String(mContent);
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
