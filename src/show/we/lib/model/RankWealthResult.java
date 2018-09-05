package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * 财富排行榜数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class RankWealthResult extends BaseListResult<RankWealthResult.Data> {
    public static class Data implements Serializable {
        @SerializedName("rank")
        private long mRank;

        @SerializedName("_id")
        private long mId;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("finance")
        private Finance mFinance;

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

        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }
    }
}
