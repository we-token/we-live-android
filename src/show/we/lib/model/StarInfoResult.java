package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 主播信息数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class StarInfoResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("user")
        private User mUser;

        @SerializedName("room")
        private Room mRoom;

        public User getUser() {
            return mUser == null ? new User() : mUser;
        }

        public Room getRoom() {
            return mRoom == null ? new Room() : mRoom;
        }
    }

    public static class User {
        @SerializedName("_id")
        private long mId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("star")
        private Star mStar;

        @SerializedName("family")
        private StarFamily mStarFamily;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("vip")
        private int mVip;

        @SerializedName("tag")
        private Map<String, Integer> mTag;

        @SerializedName("constellation")
        private int mConstellation;

        @SerializedName("sex")
        private int mSex;

        @SerializedName("location")
        private String mLocation;

        public long getId() {
            return mId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public Star getStar() {
            return mStar == null ? new Star() : mStar;
        }

        public StarFamily getFamily() {
            return mStarFamily;
        }

        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public Enums.VipType getVipType() {
            for (Enums.VipType vipType : Enums.VipType.values()) {
                if (vipType.getValue() == mVip) {
                    return vipType;
                }
            }
            return Enums.VipType.NONE;
        }

        public void setVipType(Enums.VipType vipType) {
            mVip = vipType.getValue();
        }

        public Map<String, Integer> getTag() {
            if (mTag == null) {
                mTag = new HashMap<String, Integer>();
            }
            return mTag;
        }

        public int getConstellation() {
            return mConstellation;
        }

        public int getSex() {
            return mSex;
        }

        public String getLocation() {
            return mLocation;
        }
    }

    public static class StarFamily {
        @SerializedName("_id")
        private long mId;

        @SerializedName("badge_name")
        private String mBadgeName;

        @SerializedName("family_name")
        private String mFamilyName;

        @SerializedName("family_notice")
        private String mFamilyNotice;

        @SerializedName("family_pic")
        private String mFamilyPic;

        @SerializedName("lastmodif")
        private long mLastModify;

        @SerializedName("leader_id")
        private long mLeaderId;

        @SerializedName("leaders")
        private Leaders mLeaders;

        @SerializedName("member_count")
        private int mMemberCount;

        @SerializedName("rank_cost")
        private int mRankCost;

        @SerializedName("rank_num")
        private int mRankNum;

        @SerializedName("rank_support")
        private int mRankSupport;

        @SerializedName("star_count")
        private int mStarCount;

        @SerializedName("status")
        private int mStatus;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("week_support")
        private int mWeekSupport;

        public long getId() {
            return mId;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public long getLastModify() {
            return mLastModify;
        }

        public int getStatus() {
            return mStatus;
        }

        public String getFamilyName() {
            return Utils.html2String(mFamilyName);
        }

        public String getBadgeName() {
            return Utils.html2String(mBadgeName);
        }

        public String getFamilyPic() {
            return mFamilyPic;
        }

        public String getFamilyNotice() {
            return Utils.html2String(mFamilyNotice);
        }

        public int getMemberCount() {
            return mMemberCount;
        }

        public int getStarCount() {
            return mStarCount;
        }

        public int getRankCost() {
            return mRankCost;
        }

        public int getRankSupport() {
            return mRankSupport;
        }

        public int getRankNum() {
            return mRankNum;
        }

        public int getWeekSupport() {
            return mWeekSupport;
        }

        public long getLeaderId() {
            return mLeaderId;
        }

        public Leaders getLeaders() {
            return mLeaders == null ? new Leaders() : mLeaders;
        }
    }

    public static class Leaders implements Serializable {
        @SerializedName("count")
        private int mCount;

        public int getCount() {
            return mCount;
        }
    }

    public static class Star {
        @SerializedName("day_rank")
        private long mDayRank;

        @SerializedName("room_id")
        private long mRoomId;

        @SerializedName("gift_week")
        private long[] mGiftWeek;

        public long getDayRank() {
            return mDayRank;
        }

        public long[] getGiftWeek() {
            return mGiftWeek;
        }

        public long getRoomId() {
            return mRoomId;
        }
    }

    public static class Room {
        @SerializedName("live_id")
        private String mLiveId;

        @SerializedName("message")
        private String mMessage;

        @SerializedName("live_end_time")
        private long mLeaveMessageTime;

        @SerializedName("greetings")
        private String mGreetings;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("song_price")
        private long mSongPrice;

        @SerializedName("nick_format")
        private String mNickFormat;

        public String getLiveId() {
            return mLiveId;
        }

        public String getMessage() {
            return Utils.html2String(mMessage);
        }

        public long getLeaveMessageTime() {
            return mLeaveMessageTime;
        }

        public String getGreetings() {
            return Utils.html2String(mGreetings);
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public long getSongPrice() {
            return mSongPrice;
        }

        public String getNickFormat() {
            return mNickFormat;
        }
    }
}
