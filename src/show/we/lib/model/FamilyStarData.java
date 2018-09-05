package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;
/**
 * Created by CG on 13-11-18.
 *
 * @author ll
 * @version 3.1.0
 */
public class FamilyStarData implements Serializable {

    @SerializedName("_id")
    private long mId;

    @SerializedName("star")
    private Star mStar;

    public long getId() {
        return mId;
    }

    public Star getStar() {
        return mStar;
    }

    public static class Star implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("level")
        private int mLevel;

        @SerializedName("live")
        private boolean mIsLive;

        @SerializedName("greetings")
        private String mGreetings;

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

        public boolean isLive() {
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

        public int getFollowers() {
            return mFollowers;
        }

        public long getFoundTime() {
            return mFoundTime;
        }

        public int getLevel() {
            return mLevel;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Star)) return false;

            Star star = (Star) o;

            if (mId != star.mId) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return (int) (mId ^ (mId >>> 32));
        }
    }
}
