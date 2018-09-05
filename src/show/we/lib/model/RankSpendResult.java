package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.Utils;

import java.io.Serializable;

/**
 * 消费排行数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class RankSpendResult extends BaseListResult<RankSpendResult.Data> {

    public static class Data implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("pic")
        private String mPicUrl;

        @SerializedName("coin_spend")
        private long mCoinSpend;

        @SerializedName("finance")
        private Finance mFinance;

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

        public long getCoinSpend() {
            return mCoinSpend;
        }

        public Finance getFinance() {
            return mFinance == null ? new Finance() : mFinance;
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
