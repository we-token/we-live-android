package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author ll
 * @version 3.9.0
 * Date: 2014.05.04
 */
public class UserBadgeResult extends BaseListResult<UserBadgeResult.Data> {
    public static class Data {
        public long getmId() {
            return mId;
        }

        public String getmDesc() {
            return mDesc;
        }

        public String getmSmallPic() {
            return mSmallPic;
        }

        public long getmExpire() {
            return mExpire;
        }

        public boolean ismAward() {
            return mAward;
        }

        public String getmGreyPic() {
            return mGreyPic;
        }

        public String getmExpiryDay() {
            return mExpiryDay;
        }

        public String getmPicUrl() {
            return mPicUrl;
        }

        public String getmMedalType() {
            return mMedalType;
        }

        public String getmCoins() {
            return mCoins;
        }

        public String getmSumCost() {
            return mSumCost;
        }

        public String getmName() {
            return mName;
        }

        @SerializedName("_id")
        private long mId;

        @SerializedName("desc")
        private String mDesc;

        @SerializedName("grey_pic")
        private String mGreyPic;

        @SerializedName("small_pic")
        private String mSmallPic;

        @SerializedName("expire")
        private long mExpire;

        @SerializedName("award")
        private boolean mAward;

        @SerializedName("expiry_days")
        private String mExpiryDay;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("name")
        private String mName;

        @SerializedName("medal_type")
        private String mMedalType;

        @SerializedName("coins")
        private String mCoins;

        @SerializedName("sum_cost")
        private String mSumCost;
    }
}
