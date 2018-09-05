package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 主播照片墙数据结构
 * @author ll
 * @version 2.3.0
 */
public class StarPhotoListResult extends BaseListResult<StarPhotoListResult.Data> {

    private static final String STAR_PHOTO_URL = "http://showphoto.b0.upaiyun.com";
    private static final String THUMBNAIL = "!w160h160";
    private static final String BIG = "!w800h600";

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mShortUrl;

        @SerializedName("user_id")
        private long mUserId;

        @SerializedName("type")
        private int mType;

        @SerializedName("title")
        private String mTitle;

        @SerializedName("praise")
        private long mPraise;

        @SerializedName("timestamp")
        private long mTimestamp;

        public String getShortUrl() {
            return mShortUrl;
        }

        public String getPicUrl() {
            return STAR_PHOTO_URL + mShortUrl + BIG;
        }

        public String getThumbnail() {
            return STAR_PHOTO_URL + mShortUrl + THUMBNAIL;
        }

        public long getUserId() {
            return mUserId;
        }

        public int getType() {
            return mType;
        }

        public long getPraise() {
            return mPraise;
        }

        public void setPraise(long praise) {
            mPraise = praise;
        }

        public String getTitle() {
            return mTitle;
        }

        public long getTimestamp() {
            return mTimestamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;

            Data data = (Data) o;

            if (mShortUrl != null ? !mShortUrl.equals(data.mShortUrl) : data.mShortUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mShortUrl != null ? mShortUrl.hashCode() : 0;
        }
    }
}
