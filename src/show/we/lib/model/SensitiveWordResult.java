package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * Created by tiger on 14-4-24.
 * @author ll
 * @version 3.8.0
 */
public class SensitiveWordResult extends BaseResult implements Serializable {
    public String[] getData() {
        return mData;
    }

    @SerializedName("data")
    private String[] mData;

}
