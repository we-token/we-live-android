package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Config;
import show.we.lib.utils.UserInfoUtils;

import java.io.Serializable;

/**
 * Created by CG on 13-11-18.
 *
 * @author ll
 * @version 3.1.0
 */
public class Finance implements Serializable {
    @SerializedName("bean_count")
    private long mBeanCount;

    @SerializedName("bean_count_total")
    private long mBeanCountTotal;

    @SerializedName("coin_count")
    private long mCoinCount;

    @SerializedName("coin_spend_total")
    private long mCoinSpendTotal;

    @SerializedName("spend_star_level")
    private long mSpendStarLevel;

    @SerializedName("feather_count")
    private int mFeatherCount;

    @SerializedName("feather_receive_total")
    private long mFeatherReceiveTotal;

    public void setBeanCount(long beanCount) {
        this.mBeanCount = beanCount;
    }

    public long getBeanCount() {
        return mBeanCount;
    }

    public void setBeanCountTotal(long beanCountTotal) {
        mBeanCountTotal = beanCountTotal;
    }

    public long getBeanCountTotal() {
        return mBeanCountTotal;
    }

    public void setCoinCount(long coinCount) {
        this.mCoinCount = coinCount;
    }

    public long getCoinCount() {
        return mCoinCount;
    }

    public void setCoinSpendTotal(long coinSpendTotal, boolean checkUpgrade) {
        long old = mCoinSpendTotal;
        mCoinSpendTotal = coinSpendTotal;
        if (checkUpgrade) {
            UserInfoUtils.checkUserUpgrade(old, coinSpendTotal);
        }
    }

    public long getCoinSpendTotal() {
        return mCoinSpendTotal;
    }

    public long getSpendStarLevel() {
        return mSpendStarLevel;
    }

    public int getFeatherCount() {
        return mFeatherCount;
    }

    public void setFeatherCount(int featherCount) {
        this.mFeatherCount = Math.min(featherCount, Config.MAX_FEATHER_COUNT);
    }

    public long getFeatherReceiveTotal() {
        return mFeatherReceiveTotal;
    }
}
