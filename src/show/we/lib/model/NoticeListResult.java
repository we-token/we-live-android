package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 消息列表
 * @author ll
 * @version 1.0.1
 */
public class NoticeListResult extends BaseListResult<NoticeListResult.Data> {
    private static final int READ = 1;

    public class Data implements Serializable {
        @SerializedName("_id")
        private String mNoticeId;

        @SerializedName("title")
        private String mTitle;

        @SerializedName("content")
        private String mContent;

        @SerializedName("t")
        private long mToId;

        @SerializedName("tdel")
        private int mTdel;

        @SerializedName("tread")
        private int mReadFlag;

        @SerializedName("type")
        private int mType;

        @SerializedName("timestamp")
        private long mTimeStamp;

        public String getNoticeId() {
            return mNoticeId;
        }

        public String getTitle() {
            return mTitle;
        }

        public String getContent() {
            return mContent;
        }

        public long getToId() {
            return mToId;
        }

        public int getTdel() {
            return mTdel;
        }

        public boolean isAlreadyRead() {
            return mReadFlag == READ;
        }

        public int getType() {
            return mType;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public void markReaded() {
            mReadFlag = READ;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;

            Data data = (Data) o;

            if (mNoticeId != null ? !mNoticeId.equals(data.mNoticeId) : data.mNoticeId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mNoticeId != null ? mNoticeId.hashCode() : 0;
        }
    }
}
