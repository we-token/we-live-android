package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 本周礼物周星据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class RankGiftStarResult extends BaseResult {
    @SerializedName("data")
    private List<Data> mData;

    @SerializedName("gift_week")
    private List<Data> mLastWeekData;

    public List<Data> getLastWeekData() {
        return mLastWeekData == null ? new ArrayList<Data>() : mLastWeekData;
    }

    public List<Data> getData() {
        return mData == null ? new ArrayList<Data>() : mData;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("name")
        private String mName;

        @SerializedName("pic_url")
        private String mGiftPicUrl;

        @SerializedName("star_limit")
        private long mStarLimit;

        @SerializedName("rank")
        private List<Rank> mRankList;

        public long getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }

        public String getGiftPicUrl() {
            return mGiftPicUrl;
        }

        public long getStarLimit() {
            return mStarLimit;
        }

        public List<Rank> getRankList() {
            return mRankList == null ? new ArrayList<Rank>() : mRankList;
        }
    }

    public static class Rank implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("user_id")
        private long mUserId;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("count")
        private long mCount;

        @SerializedName("pic_url")
        private String mGiftPicUrl;

        @SerializedName("name")
        private String mName;

        @SerializedName("pic")
        private String mStarPicUrl;

        @SerializedName("live")
        private boolean mIsLive;

        public long getId() {
            return mId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public long getUserId() {
            return mUserId;
        }

        public Finance getFinance() {
            return mFinance;
        }

        public long getCount() {
            return mCount;
        }

        public String getGiftPicUrl() {
            return mGiftPicUrl;
        }

        public String getName() {
            return mName;
        }

        public boolean isLive() {
            return mIsLive;
        }

        public String getStarPicUrl() {
            return mStarPicUrl;
        }
    }
}

