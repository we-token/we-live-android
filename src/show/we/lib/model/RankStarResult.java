package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 主播排行榜数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class RankStarResult extends BaseResult implements Serializable {

    @SerializedName("data")
    private List<Data> mData;

    /**
     * @return Data
     */
    public List<Data> getData() {
        return mData == null ? new ArrayList<Data>() : mData;
    }

    /**
     * Data
     */
    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("star")
        private Star mStar;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("live")
        private boolean mIsLive;

        @SerializedName("rank")
        private long mRank;

        @SerializedName("num")
        private long mNum;

        /**
         * @return mRank
         */
        public long getRank() {
            return mRank;
        }

        /**
         * @return mId
         */
        public long getId() {
            return mId;
        }

        /**
         * @return mPicUrl
         */
        public String getPicUrl() {
            return mPicUrl;
        }

        /**
         * @return mNickName
         */
        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        /**
         * @return Star
         */
        public Star getStar() {
            return mStar == null ? new Star() : mStar;
        }

        /**
         * @return Finance
         */
        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }

        /**
         * @return true -正在直播
         */
        public boolean isLive() {
            return mIsLive;
        }

        public long getNum() {
            return mNum;
        }
    }

    /**
     * Star
     */
    public static class Star implements Serializable {
        @SerializedName("room_id")
        private long mRoomId;

        /**
         * @return mRoomId
         */
        public long getRoomId() {
            return mRoomId;
        }
    }
}
