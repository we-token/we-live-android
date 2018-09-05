package show.we.lib.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ll
 * @version 2.4.0
 */
public class RecentlyViewStarListResult implements Serializable {

    private List<User> mUsers;

    public List<User> getUsers() {
        return mUsers == null ? new ArrayList<User>() : mUsers;
    }

    public void setUsers(List<User> users) {
        this.mUsers = users;
    }

    public static class User implements Serializable {
        private long mStarId;
        private long mRoomId;
        private String mNickName;
        private String mPicUrl;
        private String mCoverUrl;
        private boolean mIsLive;
        private int mVisitorCount;
        private int mLevel;

        private int mFollowers;

        public int getFollowers() {
            return mFollowers;
        }

        public void setFollowers(int mFollowers) {
            this.mFollowers = mFollowers;
        }

        public long getStarId() {
            return mStarId;
        }

        public void setStarId(long id) {
            this.mStarId = id;
        }

        public void setRoomId(long roomId) {
            mRoomId = roomId;
        }

        public long getRoomId() {
            return mRoomId;
        }

        public String getNickName() {
            return mNickName;
        }

        public void setNickName(String nickName) {
            this.mNickName = nickName;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public void setPicUrl(String picUrl) {
            this.mPicUrl = picUrl;
        }

        public String getCoverUrl() {
            return mCoverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            mCoverUrl = coverUrl;
        }

        public boolean getIsLive() {
            return mIsLive;
        }

        public void setIsLive(boolean isLive) {
            this.mIsLive = isLive;
        }

        public void setVisitorCount(int visitorCount) {
            mVisitorCount = visitorCount;
        }

        public int getVisitorCount() {
            return mVisitorCount;
        }

        public int getLevel() {
            return mLevel;
        }

        public void setLevel(int level) {
            this.mLevel = level;
        }
    }
}
