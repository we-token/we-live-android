package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * 首页banner
 *
 * @author ll
 * @version 1.2.0
 */
public class BannerResult extends BaseListResult<BannerResult.Data> {

    public static class Data {
        @SerializedName("_id")
        private long mId;

        @SerializedName("click_url")
        private String mClickUrl;

        @SerializedName("order")
        private int mOrder;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("status")
        private boolean mStatus;

        @SerializedName("timestamp")
        private long mTimeStamp;

        @SerializedName("title")
        private String mTitle;

        @SerializedName("type")
        private int mType;

        public long getId() {
            return mId;
        }

        public String getClickUrl() {
            return mClickUrl;
        }

        public int getOrder() {
            return mOrder;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public boolean getStatus() {
            return mStatus;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        public String getTitle() {
            return mTitle;
        }

        public int getType() {
            return mType;
        }
    }
}
