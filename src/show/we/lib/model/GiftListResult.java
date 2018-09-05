package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 礼物列表数据
 *
 * @author ll
 * @version 1.0.1
 */
public class GiftListResult extends BaseResult implements Serializable {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("categories")
        private List<Category> mCategoryList;

        @SerializedName("gifts")
        private List<Gift> mGiftList;

        public List<Gift> getGiftList() {
            return mGiftList == null ? new ArrayList<Gift>() : mGiftList;
        }

        public List<Category> getCategoryList() {
            return mCategoryList == null ? new ArrayList<Category>() : mCategoryList;
        }
    }

    public static class Category implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("name")
        private String mName;

        public long getId() {
            return mId;
        }

        public String getName() {
            return mName;
        }
    }

    public static class Gift implements Serializable {
        @SerializedName("_id")
        private long mId;

        @SerializedName("category_id")
        private long mCategoryId;

        @SerializedName("name")
        private String mName;

        @SerializedName("coin_price")
        private long mCoinPrice;

        @SerializedName("pic_url")
        private String mPicUrl;

        @SerializedName("pic_pre_url")
        private String mPicPreUrl;

        @SerializedName("order")
        private int mOrder;

        @SerializedName("star")
        private boolean mIsStar;

        @SerializedName("isHot")
        private boolean mIsHot;

        @SerializedName("isNew")
        private boolean mIsNew;

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

        public String getPicPreUrl() {
            return mPicPreUrl;
        }

        public int getOrder() {
            return mOrder;
        }

        public boolean isStar() {
            return mIsStar;
        }

        public boolean isHot() {
            return mIsHot;
        }

        public boolean isNew() {
            return mIsNew;
        }
    }
}
