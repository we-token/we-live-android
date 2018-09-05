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
public class TaskListResult extends BaseResult {
    @SerializedName("data")
    private Data mData;

    public Data getData() {
        return mData;
    }

    public static class Data implements Serializable {
        @SerializedName("complete_mission")
        private Map<String, Integer> mCompetedMission;

        @SerializedName("tasks")
        private List<Task> mTask;

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

        public static class Task implements Serializable {
            @SerializedName("_id")
            private int mId;

            @SerializedName("task_name")
            private String mTitle;

            @SerializedName("award_name")
            private String mInfo;

            @SerializedName("task_pic_light_url")
            private String mPicUrl;
            
            @SerializedName("task_status")
            private int mTaskStatus;

            @SerializedName("get_status")
            private int mStatus;
            
            @SerializedName("sort")
            private int mSort;
            
            @SerializedName("url_type")
            private int mUrlType;
            
            public int getUrlType() {
            	return mUrlType;
            }
            
            public int getSort() {
            	return mSort;
            }
            
            public int getId() {
                return mId;
            }

            public String getTitle() {
                return mTitle;
            }

            public String getInfo() {
                return mInfo;
            }

            public String getPicUrl() {
                return mPicUrl;
            }
            
            public int getTaskStatus() {
            	return mTaskStatus;
            }
            
            public int getStatus() {
            	return mStatus;
            }
        }

        public Map<String, Integer> getCompletedMission() {
            return mCompetedMission == null ? new HashMap<String, Integer>() : mCompetedMission;
        }

        public void setCompletedMission(Map<String, Integer> competedMission) {
            mCompetedMission = competedMission;
        }

        public List<Task> getTask() {
            return mTask == null ? new ArrayList<Task>() : mTask;
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
