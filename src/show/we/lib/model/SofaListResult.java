package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * 沙发列表
 *
 * @author ll
 * @version 1.2.0
 */
public class SofaListResult extends BaseResult implements Serializable {
    private static final int MIN_INCREASE_COIN = 100;

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("sofa")
        private List<Sofa> mSofaList;

        @SerializedName("user")
        private List<User> mUserList;

        public List<Sofa> getSofaList() {
            return mSofaList;
        }

        public List<User> getUserList() {
            return mUserList;
        }

        public static class Sofa implements Serializable {
            @SerializedName("i")
            private int mId;

            @SerializedName("xy_user_id")
            private long mUserId;

            @SerializedName("coin_spend")
            private long mCoinSpend;

            public int getId() {
                return mId;
            }

            public long getUserId() {
                return mUserId;
            }

            public long getBaseCoinSpend() {
                return mCoinSpend + MIN_INCREASE_COIN;
            }
        }

        public static class User implements Serializable {
            @SerializedName("_id")
            private long mId;

            @SerializedName("nick_name")
            private String mNickName;

            @SerializedName("pic")
            private String mPic;

            @SerializedName("finance")
            private Finance mFinance;

            @SerializedName("vip")
            private int mVip;

            @SerializedName("vip_expires")
            private long mVipExpires;

            public long getId() {
                return mId;
            }

            public String getNickName() {
                return Utils.html2String(mNickName);
            }

            public String getPic() {
                return mPic;
            }

            public Finance getFinance() {
                return mFinance;
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
        }
    }
}
