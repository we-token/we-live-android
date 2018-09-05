package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * 历史奇迹礼物数据
 *
 * @author ll
 * @version 1.0.0
 */
public class HistoryWonderGiftResult extends BaseListResult<HistoryWonderGiftResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mId;

        @SerializedName("stime")
        private long mStartTime;

        @SerializedName("etime")
        private long mEndTime;

        @SerializedName("gift")
        private GiftListResult.Gift mGift;

        @SerializedName("star1")
        private WonderStar mStar;

        @SerializedName("fan1")
        private WonderFan mFan;

        public String getId() {
            return mId;
        }

        public long getStartTime() {
            return mStartTime;
        }

        public long getEndTime() {
            return mEndTime;
        }

        public GiftListResult.Gift getGift() {
            return mGift;
        }

        public WonderStar getStar() {
            return mStar;
        }

        public WonderFan getFan() {
            return mFan;
        }
    }

    public static class WonderStar implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("count")
        private long mCount;

        @SerializedName("earned")
        private long mEarned;

        @SerializedName("bonus")
        private long mBonus;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPic;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("live")
        private boolean mIsLive;

        @SerializedName("visiter_count")
        private int mVisiterCount;

        @SerializedName("followers")
        private int mFollowers;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("bonus_time")
        private long mBonusTime;

        @SerializedName("timestamp")
        private long mTimeStamp;

        public long getId() {
            return mId;
        }

        public long getCount() {
            return mCount;
        }

        public long getEarned() {
            return mEarned;
        }

        public long getBonus() {
            return mBonus;
        }

        public long getBonusTime() {
            return mBonusTime;
        }

        public void setBonusTime(long bonusTime) {
            this.mBonusTime = bonusTime;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPic() {
            return mPic;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public boolean isLive() {
            return mIsLive;
        }

        public int getVisiterCount() {
            return mVisiterCount;
        }

        public void setId(long id) {
            this.mId = id;
        }

        public void setCount(long count) {
            this.mCount = count;
        }

        public void setEarned(long earned) {
            this.mEarned = earned;
        }

        public void setBonus(long bonus) {
            this.mBonus = bonus;
        }

        public void setNickName(String nickName) {
            this.mNickName = nickName;
        }

        public void setPic(String pic) {
            this.mPic = pic;
        }

        public void setFinance(Finance finance) {
            this.mFinance = finance;
        }

        public void setLive(boolean isLive) {
            this.mIsLive = isLive;
        }

        public void setVisiterCount(int visiterCount) {
            this.mVisiterCount = visiterCount;
        }

        public int getFollowers() {
            return mFollowers;
        }

        public void setFollowers(int followers) {
            this.mFollowers = followers;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public void setPicUrl(String picUrl) {
            this.mPicUrl = picUrl;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public void setTimeStamp(long timeStamp) {
            this.mTimeStamp = timeStamp;
        }
    }

    public static class WonderFan implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("count")
        private long mCount;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPic;

        @SerializedName("finance")
        private Finance mFinance;

        public long getId() {
            return mId;
        }

        public long getCount() {
            return mCount;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPic() {
            return mPic;
        }

        public Finance getFinance() {
            return mFinance;
        }
    }
}
