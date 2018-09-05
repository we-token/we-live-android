package show.we.lib.widget.slotgame;

import java.io.Serializable;

/**
 * @author ll
 * @version 1.0.0
 */
public class SlotRewardItem implements Serializable {

    private int mRewardNum;
    private String mRewardKey;
    private String mRewardName;
    private String mRewardPicUrl;
    private String mGiftOrCarName;

    /**
     *
     * @param rewardNum rewardNum
     * @param rewardKey rewardKey
     * @param rewardName rewardName
     * @param giftOrCarName giftOrCarName
     * @param rewardPicUrl rewardPicUrl
     */
    public SlotRewardItem(int rewardNum, String rewardKey, String rewardName, String giftOrCarName, String rewardPicUrl) {
        this.mRewardNum = rewardNum;
        this.mRewardKey = rewardKey;
        this.mRewardName = rewardName;
        this.mGiftOrCarName = giftOrCarName;
        this.mRewardPicUrl = rewardPicUrl;
    }

    /**
     * @return mRewardKey
     */
    public String getRewardKey() {
        return mRewardKey;
    }

    /**
     * @return mRewardName
     */
    public String getRewardName() {
        return mRewardName;
    }

    /**
     * @return mRewardPicUrl
     */
    public String getRewardPicUrl() {
        return mRewardPicUrl;
    }

    /**
     * @param rewardPicUrl rewardPicUrl
     */
    public void setRewardPicUrl(String rewardPicUrl) {
        this.mRewardPicUrl = rewardPicUrl;
    }

    /**
     * @return mRewardNum
     */
    public int getRewardNum() {
        return mRewardNum;
    }

    /**
     * @return mGiftOrCarName
     */
    public String getGiftOrCarName() {
        return mGiftOrCarName;
    }
}
