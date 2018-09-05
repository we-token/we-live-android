package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 联系人私信会话数据结构
 *
 * @author ll
 * @version 2.4.0
 */
public class MailConversationResult extends BaseListResult<MailConversationResult.Data> {

    private static final int FINISHED = 1;

    @SerializedName("from")
    private From mFrom;

    public From getFrom() {
        return mFrom;
    }

    public void setFrom(From from) {
        this.mFrom = from;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mId;

        @SerializedName("f")
        private long mSenderId;

        @SerializedName("fdel")
        private int mSenderDeleteFlag;

        @SerializedName("t")
        private long mReciverId;

        @SerializedName("tdel")
        private int mReceiverDeleteFlag;

        @SerializedName("tread")
        private int mReceiverReadFlag;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("msg")
        private String mMessage;

        public String getId() {
            return mId;
        }

        public long getSenderId() {
            return mSenderId;
        }

        public boolean isMsgDeletedBySender() {
            return mSenderDeleteFlag == FINISHED;
        }

        public long getReciverId() {
            return mReciverId;
        }

        public boolean isMsgDeletedByReceiver() {
            return mReceiverDeleteFlag == FINISHED;
        }

        public boolean isMsgReadedByReceiver() {
            return mReceiverReadFlag == FINISHED;
        }

        public long getTimeInMillis() {
            return mTimeStamp;
        }

        public String getTimeStamp() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return simpleDateFormat.format(new Date(mTimeStamp));
        }

        public String getMessage() {
            return mMessage;
        }

        public void setId(String mId) {
            this.mId = mId;
        }

        public void setSenderId(long mSenderId) {
            this.mSenderId = mSenderId;
        }

        public void setSenderDeleteFlag(int mSenderDeleteFlag) {
            this.mSenderDeleteFlag = mSenderDeleteFlag;
        }

        public void setReciverId(long mReciverId) {
            this.mReciverId = mReciverId;
        }

        public void setMsgDeleteByReceiver(boolean isMsgDeleteByReceiver) {
            mReceiverDeleteFlag = isMsgDeleteByReceiver ? FINISHED : 0;
        }

        public void setMsgReadedByReceiver(boolean isMsgReadedByReceiver) {
            mReceiverReadFlag = isMsgReadedByReceiver ? FINISHED : 0;
        }

        public void setTimeStamp(long mTimeStamp) {
            this.mTimeStamp = mTimeStamp;
        }

        public void setMessage(String mMessage) {
            this.mMessage = mMessage;
        }
    }

    public static class From implements Serializable {

        @SerializedName("_id")
        private long mId;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPic;

        @SerializedName("priv")
        private long mPriv;

        public long getId() {
            return mId;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public String getNickName() {
            return mNickName;
        }

        public String getPic() {
            return mPic;
        }

        public long getPriv() {
            return mPriv;
        }

        public void setId(long mId) {
            this.mId = mId;
        }

        public void setNickName(String mNickName) {
            this.mNickName = mNickName;
        }
    }
}
