package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * 观众与管理员数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class AudienceResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("count")
        private long mCount;

        @SerializedName("list")
        private List<User> mUsers;

        private long mFakedCount;

        public long getFakedCount() {
            return mFakedCount;
        }

        public void setFakedCount(long fakedCount) {
            mFakedCount = fakedCount;
        }

        public long getCount() {
            return mCount;
        }

        public List<User> getUsers() {
            return mUsers == null ? new ArrayList<User>() : mUsers;
        }
    }

    public static class User {
        @SerializedName("_id")
        private long mId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("finance")
        private Finance mFinance;

        @SerializedName("family")
        private Family mFamily;

        @SerializedName("coin_spend")
        private long mCoinSpend;

        @SerializedName("vip")
        private int mVip;

        @SerializedName("priv")
        private int mType;

        @SerializedName("s")
        private String mDeviceType;

        public long getId() {
            return mId;
        }

        public String getNickName() {
            return Utils.html2String(mNickName);
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
        }

        public Family getFamily() {
            return mFamily;
        }

        public long getCoinSpend() {
            return mCoinSpend;
        }

        public Enums.VipType getVipType() {
            for (Enums.VipType vipType : Enums.VipType.values()) {
                if (vipType.getValue() == mVip) {
                    return vipType;
                }
            }
            return Enums.VipType.NONE;
        }

        public int getType() {
            return mType;
        }

        public String getDeviceType() {
            return mDeviceType;
        }
    }
}
