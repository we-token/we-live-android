package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * 砸金蛋
 *
 * @author ll
 * @version 1.2.0
 */
public class SlotWinResult extends BaseResult implements Serializable {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("cost")
        private int mCost;

        @SerializedName("egg")
        private String mRewardKey;

        @SerializedName("room_id")
        private long mRoomId;

        @SerializedName("t")
        private long mTime;

        @SerializedName("user")
        private User mUser;

        public static class User implements Serializable {
            @SerializedName("_id")
            private long mId;

            @SerializedName("nick_name")
            private String mNickName;

            @SerializedName("finance")
            private Finance mFinance;

            public long getId() {
                return mId;
            }

            public String getNickName() {
                return mNickName;
            }

            public Finance getFinance() {
                return mFinance;
            }
        }

        public int getCost() {
            return mCost;
        }

        public String getRewardKey() {
            return mRewardKey;
        }

        public long getRoomId() {
            return mRoomId;
        }

        public long getTime() {
            return mTime;
        }

        public User getUser() {
            return mUser;
        }
    }
}
