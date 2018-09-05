package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-6-17
 *          Time: 下午2:24
 *          To change this template use File | Settings | File Templates.
 */
public class MountListResult extends BaseListResult<MountListResult.MountItem> {

    public static class MountItem implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("cat")
        private String mCat;

        @SerializedName("name")
        private String mName;

        @SerializedName("coin_price")
        private Long mPrice;

        @SerializedName("pic_url")
        private String mPicUrl;

        private long mEffectiveTime;

        public long getEffectiveTime() {
            return mEffectiveTime;
        }

        public void setEffectiveTime(long effectiveTime) {
            this.mEffectiveTime = effectiveTime;
        }

        public long getId() {
            return mId;
        }

        public String getCat() {
            return mCat;
        }

        public String getName() {
            return mName;
        }

        public Long getPrice() {
            return mPrice;
        }

        public String getPicUrl() {
            return mPicUrl;
        }

        public boolean isVipMount() {
            return mCat.equals("1");
        }
    }
}
