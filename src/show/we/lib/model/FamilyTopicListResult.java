package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * Created by CG on 13-12-2.
 *
 * @author ll
 * @version 3.2.0
 */
public class FamilyTopicListResult extends BaseListResult<FamilyTopicListResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mId;
        @SerializedName("replyCount")
        private int mReplyCount;
        @SerializedName("replyTime")
        private long mReplyTime;
        @SerializedName("title")
        private String mTitle;
        @SerializedName("top")
        private int mTop;
        @SerializedName("uid")
        private long mUserId;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mNickName;
        @SerializedName("priv")
        private int mPriv;

        public String getId() {
            return mId;
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

        public Finance getFinance() {
            return mFinance;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }
    }
}
