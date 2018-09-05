package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * Created by CG on 14-1-7.
 *
 * @author ll
 * @version 3.4.0
 */
public class OfficialTopicListResult extends BaseListResult<OfficialTopicListResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mId;
        @SerializedName("label")
        private String mLabel;
        @SerializedName("replyCount")
        private int mReplyCount;
        @SerializedName("replyTime")
        private long mReplyTime;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("uid")
        private long mUserId;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;
        @SerializedName("priv")
        private int mPriv;
        @SerializedName("official_uid")
        private String mOfficialUserIds;

        public String getId() {
            return mId;
        }

        public String getLabel() {
            return mLabel;
        }

        public int getReplyCount() {
            return mReplyCount;
        }

        public long getReplyTime() {
            return mReplyTime;
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

        public int getPriv() {
            return mPriv;
        }

        public String getOfficialUserIds() {
            return mOfficialUserIds;
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
