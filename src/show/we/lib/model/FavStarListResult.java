package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.text.Collator;
import java.util.*;

/**
 * 获取关注主播列表
 *
 * @author ll
 * @version 1.0.1
 */
public class FavStarListResult extends BaseResult implements Serializable {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public static class Data implements Serializable {
        @SerializedName("users")
        private List<User> mUserList;

        @SerializedName("rooms")
        private List<Room> mRoomList;

        private List<StarInfo> mStarInfoList;

        public List<Room> getRoomList() {
            return mRoomList == null ? new ArrayList<Room>() : mRoomList;
        }

        public List<StarInfo> getStarInfoList() {
            return mStarInfoList == null ? new ArrayList<StarInfo>() : mStarInfoList;
        }

        public void sort() {
            if (mStarInfoList == null) {
                mStarInfoList = new ArrayList<StarInfo>();
                if (mUserList != null && mRoomList != null) {
                    for (User user : mUserList) {
                        StarInfo starInfo = new StarInfo();
                        starInfo.mUser = user;
                        for (Room room : mRoomList) {
                            if (room.getXyStarId() == user.getId()) {
                                starInfo.mRoom = room;
                                break;
                            }
                        }
                        mStarInfoList.add(starInfo);
                    }
                }

                Collections.sort(mStarInfoList, new Comparator<StarInfo>() {
                    @Override
                    public int compare(StarInfo lhs, StarInfo rhs) {
                        if (lhs.getRoom().isLive() == rhs.getRoom().isLive()) {
                            return Collator.getInstance(Locale.CHINESE).compare(lhs.getUser().getNickName(), rhs.getUser().getNickName());
                        } else {
                            return lhs.getRoom().isLive() ? -1 : 1;
                        }
                    }
                });
            }
        }
    }

    public static class User implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("star")
        private Star mStar;

        public long getId() {
            return mId;
        }

        public void setId(long mId) {
            this.mId = mId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public void setNickName(String mNickName) {
            this.mNickName = mNickName;
        }

        public void setPicUrl(String mPicUrl) {
            this.mPicUrl = mPicUrl;
        }

        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }

        public Star getStar() {
            return mStar == null ? new Star() : mStar;
        }

        public static class Star implements Serializable {

            @SerializedName("gift_week")
            private List<Integer> mGiftWeek;

			public List<Integer> getGiftWeek() {
				return mGiftWeek;
			}
        }
    }

    public static class Room implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("live")
        private boolean mIsLive;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("visiter_count")
        private int mVisitorCount;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("xy_star_id")
        private long mXyStarId;

		@SerializedName("followers")
		private int mFollowers;

		@SerializedName("found_time")
		private long mFoundTime;

		public long getId() {
            return mId;
        }

        public void setId(long mId) {
            this.mId = mId;
        }

        public void setIsLive(boolean mIsLive) {
            this.mIsLive = mIsLive;
        }

        public boolean isLive() {
            return mIsLive;
        }

        public void setPicUrl(String picUrl) {
            mPicUrl = picUrl;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public void setVisitorCount(int visitorCount) {
            mVisitorCount = visitorCount;
        }

        public int getVisitorCount() {
            return mVisitorCount;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public long getXyStarId() {
            return mXyStarId;
        }

        public void setXyStarId(long mXyStarId) {
            this.mXyStarId = mXyStarId;
        }

		public int getFollowers() {
			return mFollowers;
		}

		public long getFoundTime() {
			return mFoundTime;
		}
	}

    public static class StarInfo implements Serializable {
        private User mUser;
        private Room mRoom;

        public User getUser() {
            return mUser;
        }

        public Room getRoom() {
            return mRoom;
        }

        public void setUser(User user) {
            this.mUser = user;
        }

        public void setRoom(Room room) {
            this.mRoom = room;
        }
    }
}

