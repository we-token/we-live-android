package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.Utils;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class ActiveRankResult extends BaseResult {

    @SerializedName("data")
    private List<Data> mData;

    public List<Data> getData() {
        return mData == null ? new ArrayList<Data>() : mData;
    }

    public static class Data implements Serializable {
        @SerializedName("value")
        private int mValue;

        @SerializedName("_id")
        private long mId;

        @SerializedName("nick_name")
        private String mNickname;

        @SerializedName("pic")
        private String mPic;

        @SerializedName("priv")
        private int mPriv;

		@SerializedName("vip")
		private int mVip;

        @SerializedName("finance")
        private Finance mFinance;

        public int getValue() {
            return mValue;
        }

        public long getId() {
            return mId;
        }

        public String getNickname() {
            return Utils.html2String(mNickname);
        }

        public String getPic() {
            return mPic;
        }

		public Enums.VipType getVipType() {
			for (Enums.VipType vipType : Enums.VipType.values()) {
				if (vipType.getValue() == mVip) {
					return vipType;
				}
			}
			return Enums.VipType.NONE;
		}

        public int getPriv() {
            return mPriv;
        }

        public Finance getFinance() {
            return mFinance;
        }
    }
}
