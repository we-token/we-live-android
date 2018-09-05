package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;
import java.util.List;

/**
 * 直播房间数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class RoomListResult extends BaseListResult<RoomListResult.Data> {

    public static class Data implements Serializable {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;

            Data data = (Data) o;

            if (mId != data.mId) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return (int) (mId ^ (mId >>> 32));
        }

        @SerializedName("_id")
        private long mId;

        @SerializedName("greetings")
        private String mGreetings;

        @SerializedName("live")
        private boolean mIsLive;

        @SerializedName("message")
        private String mMessage;

        @SerializedName("pic_url")
        private String mCoverUrl;

        @SerializedName("visiter_count")
        private int mVisitorCount;

        @SerializedName("xy_star_id")
        private long mXyStarId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("bean")
        private long mBean;

        @SerializedName("L")
        private int mL;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("star")
        private Star mStar;

		@SerializedName("followers")
		private int mFollowers;

		@SerializedName("found_time")
		private long mFoundTime;

        public long getId() {
            return mId;
        }

        public String getGreetings() {
            return Utils.html2String(mGreetings);
        }

        public boolean getIsLive() {
            return mIsLive;
        }

        public String getMessage() {
            return Utils.html2String(mMessage);
        }

        public String getCoverUrl() {
            return mCoverUrl;
        }

        public int getVisitorCount() {
            return mVisitorCount;
        }

        public void setVisitorCount(int visitorCount) {
            this.mVisitorCount = visitorCount;
        }

        public long getXyStarId() {
            return mXyStarId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public long getBean() {
            return mBean;
        }

        public int getL() {
            return mL;
        }

        public void setL(int l) {
            this.mL = l;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public void setFinance(Finance finance) {
            this.mFinance = finance;
        }

        public Star getStar() {
            return mStar == null ? new Star() : mStar;
        }

        public void setIsLive(boolean isLive) {
            this.mIsLive = isLive;
        }

        public void setRoomId(long id) {
            this.mId = id;
        }

        public void setStarId(long starId) {
            this.mXyStarId = starId;
        }

        public void setNickName(String nickName) {
            this.mNickName = nickName;
        }

        public void setPicUrl(String picUrl) {
            this.mPicUrl = picUrl;
        }

        public void setFollowers(int followers) {
            this.mFollowers = followers;
        }

        public void setCoverUrl(String coverUrl) {
            this.mCoverUrl = coverUrl;
        }

        public static class Star implements Serializable {
            @SerializedName("gift_week")
            private List<Long> mGiftWeek;

            public List<Long> getGiftWeek() {
                return mGiftWeek;
            }
        }

		public int getFollowers() {
			return mFollowers;
		}

		public long getFoundTime() {
			return mFoundTime;
		}
    }
}
