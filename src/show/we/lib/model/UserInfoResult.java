package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.LevelUtils;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微播用户信息数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class UserInfoResult extends BaseResult {

    private static final int COMMON_VIP = 1;
    private static final int SUPER_VIP = 2;
    private static final int VIP_HIDING = 1;

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

        @SerializedName("province")
        private String mProvince;
        
        @SerializedName("city")
        private String mCity;
        
        @SerializedName("county")
        private String mCounty;
        
        @SerializedName("real_name")
        private String mRealName;
        
        @SerializedName("telephone")
        private String mTelephone;
        
        @SerializedName("bank_account")
        private String mBankAccount;
        
        @SerializedName("bank_name")
        private String mBankName;
        
        @SerializedName("bank_no")
        private String mBankNo;
        
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

        @SerializedName("star")
        private Star mStar;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("gift_list")
        private List<Gift> mGifts;

        @SerializedName("user_name")
        private String mEmailAddress;

        @SerializedName("address")
        private String mAddress;

        @SerializedName("coordinate")
        private Coordinate mCoordinate;

        /**
         * 0：普通用户   1：普通vip   2：至尊vip
         */
        @SerializedName("vip")
        private int mVip;

        @SerializedName("vip_expires")
        private long mVipExpires;

        @SerializedName("vip_hiding")
        private int mVipHiding;

        @SerializedName("family")
        private Family mFamily;

        public long getId() {
            return mId;
        }

        public String getLocation() {
            return mLocation;
        }

        public void setLocation(String location) {
            this.mLocation = location;
        }
        
        public String getProvince(){
        	return mProvince;
        }
        
        public void setProvince(String province){
        	this.mProvince = province;
        }
        
        public String getCity(){
        	return mCity;
        }
        
        public void setCity(String city){
        	this.mCity = city;
        }

        public String getCounty(){
        	return mCounty;
        }
        
        public void setCounty(String county){
        	this.mCounty = county;
        }
        
        public String getRealName(){
        	return Utils.html2String(mRealName);
        }
        
        public void setRealName(String realName){
        	this.mRealName = realName;
        }
        
        public String getTelephone(){
        	return mTelephone;
        }
        
        public void setTelephone(String telephone){
        	this.mTelephone = telephone;
        }
        
        public String getBankAccount(){
        	return Utils.html2String(mBankAccount);
        }
        
        public void setBankAccount(String bankAccount){
        	this.mBankAccount = bankAccount;
        }
        
        public String getBankName(){
        	return Utils.html2String(mBankName);
        }
        
        public void setBankName(String bankName){
        	this.mBankName = bankName;
        }
        
        public String getBankNo(){
        	return Utils.html2String(mBankNo);
        }
        
        public void setBankNo(String bankNo){
        	this.mBankNo = bankNo;
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

        public Star getStar() {
            return mStar == null ? new Star() : mStar;
        }

        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }

        public void setFinance(Finance finance) {
            this.mFinance = finance;
        }

        public List<Gift> getGifts() {
            return mGifts == null ? new ArrayList<Gift>() : mGifts;
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

        public Enums.UserRolePriv getPrivType() {
            for (Enums.UserRolePriv privType : Enums.UserRolePriv.values()) {
                if (privType.getValue() == mPriv) {
                    return privType;
                }
            }
            return Enums.UserRolePriv.NONE;
        }

        public void setVipType(Enums.VipType vipType) {
            mVip = vipType.getValue();
        }

        public boolean isVipHiding() {
            return mVipHiding == VIP_HIDING;
        }

        public void setVipHiding(boolean isVipHiding) {
            mVipHiding = isVipHiding ? VIP_HIDING : 0;
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

        public Coordinate getCoordinate() {
            return mCoordinate;
        }

        public String getAddress() {
            return mAddress;
        }
    }

    public static class Star implements Serializable {
        @SerializedName("room_id")
        private long mRoomId;

        @SerializedName("timestamp")
        private long mTimeStamp;

        public long getRoomId() {
            return mRoomId;
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }
    }

    public static class Gift implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("name")
        private String mName;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("coin_price")
        private long mCoinPrice;

        @SerializedName("category_id")
        private long mCategoryId;

        @SerializedName("count")
        private long mCount;

        public long getId() {
            return mId;
        }

        public long getCategoryId() {
            return mCategoryId;
        }

        public String getName() {
            return mName;
        }

        public long getCoinPrice() {
            return mCoinPrice;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public long getCount() {
            return mCount;
        }
    }

    public static class Coordinate implements Serializable {
        @SerializedName("x")
        private String mLongitude;

        @SerializedName("y")
        private String mLatitude;

        public String getLongitude() {
            return mLongitude;
        }

        public String getLatitude() {
            return mLatitude;
        }
    }
}
