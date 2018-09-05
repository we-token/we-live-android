package show.we.lib.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author ll
 * @version Date: 14-1-21
 *          Time: 下午3:25
 */
public class SearchTag implements Serializable {

    @SerializedName("tag_id")
    private int mTagID;

    @SerializedName("tag_name")
    private String mTagName;

    public int getTagID() {
        return mTagID;
    }

    public void setTagID(int mTagID) {
        this.mTagID = mTagID;
    }

    public String getTagName() {
        return mTagName;
    }

    public void setTagName(String mTagName) {
        this.mTagName = mTagName;
    }
}
