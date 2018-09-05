package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;

/**
 * Created by CG on 13-11-8.
 *
 * @author ll
 * @version 3.0.0
 */
public class MissionNumResult extends BaseResult {

    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData == null ? new Data() : mData;
    }

    public class Data implements Serializable {

        @SerializedName("mission_num")
        private int mMissionCount;

        public int getMissionCount() {
            return mMissionCount;
        }
    }
}
