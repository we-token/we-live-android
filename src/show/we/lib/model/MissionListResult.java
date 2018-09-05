package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务数据结构
 *
 * @author ll
 * @version 1.0.1
 */
public class MissionListResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("complete_mission")
        private Map<String, Integer> mCompetedMission;

        @SerializedName("all_mission")
        private List<Mission> mMissions;

        /**
         * 未完成的数量
         */
        private int mUncompletedCount;
        /**
         * 可领取金币的数量
         */
        private int mAvailableCount;
        /**
         * 任务结束的数量
         */
        private int mFinishCount;

        public static class Mission implements Serializable {
            @SerializedName("_id")
            private String mId;

            @SerializedName("title")
            private String mTitle;

            @SerializedName("coin_count")
            private long mCoin;

            @SerializedName("pic_url")
            private String mPicUrl;

            @SerializedName("icon_url")
            private String mIconUrl;

            public String getId() {
                return mId;
            }

            public String getTitle() {
                return mTitle;
            }

            public long getCoin() {
                return mCoin;
            }

            public String getPicUrl() {
                return mPicUrl;
            }

            public String getIconUrl() {
                return mIconUrl;
            }
        }

        public Map<String, Integer> getCompletedMission() {
            return mCompetedMission == null ? new HashMap<String, Integer>() : mCompetedMission;
        }

        public void setCompletedMission(Map<String, Integer> competedMission) {
            mCompetedMission = competedMission;
        }

        public List<Mission> getMissions() {
            return mMissions == null ? new ArrayList<Mission>() : mMissions;
        }

        public void setUncompletedCount(int uncompletedCount) {
            mUncompletedCount = uncompletedCount;
        }

        public int getUncompletedCount() {
            return mUncompletedCount;
        }

        public void setAvailableCount(int availableCount) {
            mAvailableCount = availableCount;
        }

        public int getAvailableCount() {
            return mAvailableCount;
        }

        public void setFinishCount(int finishCount) {
            mFinishCount = finishCount;
        }

        public int getFinishCount() {
            return mFinishCount;
        }
    }
}
