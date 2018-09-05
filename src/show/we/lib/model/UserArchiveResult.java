package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.LevelUtils;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 天天星愿用户（非当前登录用户）档案数据结构
 *
 * @author ll
 * @version 4.0.0
 */
public class UserArchiveResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("location")
        private String mLocation;

        @SerializedName("rank")
        private String mRank;

        public String getmRank() {
            return mRank;
        }

        @SerializedName("constellation")
        private int mConstellation = -1;

        @SerializedName("stature")
        private int mStature = -1;

        /**
         * 1：运营人员   2：主播   3：普通用户  4. 客服人员  5. 经纪人
         */
        @SerializedName("priv")
        private int mPriv;

        @SerializedName("sex")
        private int mSex = 1; //男

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("user_name")
        private String mEmailAddress;

        /**
         * 0：普通用户   1：普通vip   2：至尊vip
         */
        @SerializedName("vip")
        private int mVip;

        @SerializedName("vip_expires")
        private long mVipExpires;

        @SerializedName("family")
        private Family mFamily;


        @SerializedName("car")
        private HashMap<String, Long> mCars;

        public HashMap<String, Long> getCars() {
            return mCars;
        }

        public long getId() {
            return mId;
        }

        public String getLocation() {
            return mLocation;
        }

        public void setLocation(String location) {
            this.mLocation = location;
        }

        public int getConstellation() {
            return mConstellation;
        }

        public void setConstellation(int constellation) {
            this.mConstellation = constellation;
        }

        public int getStature() {
            return mStature;
        }

        public void setStature(int stature) {
            this.mStature = stature;
        }

        public int getPriv() {
            return mPriv;
        }

        public int getSex() {
            return mSex;
        }

        public void setSex(int sex) {
            this.mSex = sex;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public void setNickName(String nickName) {
            this.mNickName = nickName;
        }


        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }

        public void setFinance(Finance finance) {
            this.mFinance = finance;
        }


        public String getPicUrl() {
            return mPicUrl;
        }

        public void setPicUrl(String picUrl) {
            this.mPicUrl = picUrl;
        }

        public String getEmailAddress() {
            return mEmailAddress;
        }

        public long getVipExpires() {
            return mVipExpires;
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

        public Family getFamily() {
            return mFamily;
        }

        public void exitFamily() {
            mFamily = null;
        }

        public void setFamily(Family family) {
            mFamily = family;
        }

        public int getMaxMessageLength() {
            long spendCoins = getFinance().getCoinSpendTotal();
            LevelUtils.UserLevelInfo userLevelInfo = LevelUtils.getUserLevelInfo(spendCoins, getVipType());

            return userLevelInfo.getMaxMessageLength();
        }
    }
}
