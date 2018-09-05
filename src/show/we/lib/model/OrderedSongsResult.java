package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

/**
 * 用户点歌的数据
 *
 * @author ll
 * @version 1.0.1
 */
public class OrderedSongsResult extends BaseListResult<OrderedSongsResult.Data> {

    public static final int STATUS_AGREE = 1;
    public static final int STATUS_REFUSAL = 2;
    public static final int STATUS_WAIT = 3;

    public static class Data {
        @SerializedName("_id")
        private String mSongId;

        @SerializedName("song_name")
        private String mSongName;

        @SerializedName("status")
        private int mStatus;

        @SerializedName("xy_user_id")
        private long mUserId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("cost")
        private long mCost;

        public String getId() {
            return mSongId;
        }

        public String getSongName() {
            return mSongName;
        }

        public int getStatus() {
            return mStatus;
        }

        public long getUserId() {
            return mUserId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public long getCost() {
            return mCost;
        }
    }
}
