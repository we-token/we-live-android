package show.we.lib.model;

/**
 * Created by Administrator on 13-7-29.
 *
 * @author ll
 * @version 1.0.0
 */
public class BagGift extends GiftListResult.Gift {

    private long mNum;
    private GiftListResult.Gift mGift;

    /**
     * BagGift
     *
     * @param gift gift
     */
    public BagGift(GiftListResult.Gift gift) {
        mGift = gift;
    }

    @Override
    public long getId() {
        return mGift.getId();
    }

    @Override
    public long getCategoryId() {
        return mGift.getCategoryId();
    }

    @Override
    public String getName() {
        return mGift.getName();
    }

    @Override
    public long getCoinPrice() {
        return mGift.getCoinPrice();
    }

    @Override
    public String getPicUrl() {
        return mGift.getPicUrl();
    }

    @Override
    public String getPicPreUrl() {
        return mGift.getPicPreUrl();
    }

    @Override
    public int getOrder() {
        return mGift.getOrder();
    }

    @Override
    public boolean isStar() {
        return mGift.isStar();
    }

    /**
     * 获取礼物数
     *
     * @return 礼物数
     */
    public long getNum() {
        return mNum;
    }

    /**
     * 设置礼物数
     *
     * @param num 礼物数
     */
    public void setNum(long num) {
        this.mNum = num;
    }
}
