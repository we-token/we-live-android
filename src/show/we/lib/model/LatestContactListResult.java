package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * 最近联系人数据结构
 *
 * @author ll
 * @version 2.4.0
 */
public class LatestContactListResult extends BaseListResult<LatestContactListResult.Data> {

    private static final int FINISHED = 1;

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mFromUserId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("msg")
        private String mLatestMsg;

        @SerializedName("count")
        private long mReceivedMsgCount;

        @SerializedName("timestamp")
        private long mSendTime;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("priv")
        private long mPriv;

        @SerializedName("tread")
        private int mMsgReceiverReadFlag;

        public long getFromUserId() {
            return mFromUserId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public String getLatestMsg() {
            return mLatestMsg;
        }

        public long getReceivedMsgCount() {
            return mReceivedMsgCount;
        }

        public long getSendTime() {
            return mSendTime;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public long getPriv() {
            return mPriv;
        }

        public boolean isMessageReaded() {
            return mMsgReceiverReadFlag == FINISHED;
        }

        public void readMsg() {
            mMsgReceiverReadFlag = FINISHED;
        }
    }
}
