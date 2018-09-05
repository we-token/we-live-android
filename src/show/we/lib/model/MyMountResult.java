package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.util.HashMap;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-6-18
 *          Time: 上午11:05
 *          To change this template use File | Settings | File Templates.
 */
public class MyMountResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data {
        @SerializedName("_id")
        private long mUserId;

        @SerializedName("car")
        private HashMap<String, Long> mCars;

        public long getUserId() {
            return mUserId;
        }

        public HashMap<String, Long> getCars() {
            return mCars;
        }
    }
}
