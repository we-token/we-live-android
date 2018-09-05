package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ll
 * @version 3.9.0
 * Date: 2014.05.04
 */
public class UsersStatusResult extends BaseListResult<UsersStatusResult.Data> {
    public static class Data {
        public long getmStarId() {
            return mStarId;
        }

        public String getmNickName() {
            return mNickName;
        }

        public long getmRoomId() {
            return mRoomId;
        }

        public String getmPicUrl() {
            return mPicUrl;
        }

        public boolean ismIsLive() {
            return mIsLive;
        }

        public int getmVisitorCount() {
            return mVisitorCount;
        }

        public int getmLevel() {
            return mLevel;
        }

        public int getmFollowers() {
            return mFollowers;
        }

        @SerializedName("_id")
        private long mStarId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("room_ids")
        private long mRoomId;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("live")
        private boolean mIsLive;

        @SerializedName("visiter_count")
        private int mVisitorCount;

        @SerializedName("L")
        private int mLevel;

        @SerializedName("followers")
        private int mFollowers;



    }
}
