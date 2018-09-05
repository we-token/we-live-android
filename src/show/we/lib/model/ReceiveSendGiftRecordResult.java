package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 消费、收礼记录
 *
 * @author ll
 * @version 3.8.0
 */
public class ReceiveSendGiftRecordResult extends BaseListResult<ReceiveSendGiftRecordResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mId;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("session")
        private Session mSession;

        @SerializedName("type")
        private String mType;

        @SerializedName("cost")
        private long mCost;

        @SerializedName("room")
        private long mRoomId;

        @SerializedName("qd")
        private String mChannel;

        public String getId() {
            return mId;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public Session getSession() {
            return mSession;
        }

        public String getType() {
            return mType;
        }

        public long getCost() {
            return mCost;
        }

        public long getRoomId() {
            return mRoomId;
        }

        public String getChannel() {
            return mChannel;
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

    public static class Session implements Serializable {
        @SerializedName("_id")
        private String mUserId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("priv")
        private String mPriv;

        @SerializedName("spend")
        private String mSpend;

        @SerializedName("data")
        private GiftData mGiftData;

        public String getUserId() {
            return mUserId;
        }

        public String getNickName() {
            return mNickName;
        }

        public String getPriv() {
            return mPriv;
        }

        public String getSpend() {
            return mSpend;
        }

        public GiftData getGiftData() {
            return mGiftData;
        }

        public static class GiftData implements Serializable {
            @SerializedName("_id")
            private long mGiftId;

            @SerializedName("category_id")
            private int mCategoryId;

            @SerializedName("coin_price")
            private int mCoinPrice;

            @SerializedName("name")
            private String mGiftName;

            @SerializedName("count")
            private int mCount;

            @SerializedName("xy_star_id")
            private long mXyStarId;

            @SerializedName("earned")
            private int mEarned;

            @SerializedName("xy_nick")
            private String mStarName;

            public long getGiftId() {
                return mGiftId;
            }

            public int getCategoryId() {
                return mCategoryId;
            }

            public int getCoinPrice() {
                return mCoinPrice;
            }

            public String getGiftName() {
                return mGiftName;
            }

            public int getCount() {
                return mCount;
            }

            public long getXyStarId() {
                return mXyStarId;
            }

            public int getEarned() {
                return mEarned;
            }

            public String getStarName() {
                return mStarName;
            }
        }
    }
}
