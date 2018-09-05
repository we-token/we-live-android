package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ll
 * @version 3.8.0
 * @param <T> T
 */
public class BaseListResult<T> extends BaseResult {

    @SerializedName("count")
    private int mCount;

    @SerializedName("all_page")
    private int mAllPage;

    @SerializedName("data")
    private List<T> mDataList;

    private int mPage;
    private int mSize;

    /**
     * 是否全都加载完成
     * @return 是否全部加载完成
     */
    public boolean isAllDataLoaded() {
        return getDataList().size() < getSize();
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getPage() {
        return mPage;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public int getCount() {
        return mCount;
    }

    public int getAllPage() {
        return mAllPage;
    }

    public List<T> getDataList() {
        if (mDataList == null) {
            mDataList = new ArrayList<T>();
        }
        return mDataList;
    }

    public void setDataList(List<T> dataList) {
        mDataList = dataList;
    }
}
