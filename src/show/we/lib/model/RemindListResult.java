package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.Utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by CG on 14-1-9.
 *
 * @author ll
 * @version 3.4.0
 */
public class RemindListResult extends BaseListResult<RemindListResult.Data> {

    private static final int READ = 1;

    @Override
    public List<Data> getDataList() {
        return filterOfficialTopic(super.getDataList());
    }

    private List<Data> filterOfficialTopic(List<Data> dataList) {
        Iterator<Data> it = dataList.iterator();
        while (it.hasNext()) {
            if (it.next().getTopicType() == Enums.TopicType.OFFICIAL_TOPIC) {
                it.remove();
            }
        }
        return dataList;
    }

    public static class Data implements Serializable {
        @SerializedName("_id")
        private String mId;
        @SerializedName("tid")
        private String mTopicId;
        @SerializedName("tread")
        private int mReadFlag;
        @SerializedName("cate")
        private int mCategory;
        @SerializedName("type")
        private int mTopicType;
        @SerializedName("content")
        private String mContent;
        @SerializedName("timestamp")
        private long mTimeStamp;

        public String getId() {
            return mId;
        }

        public String getTopicId() {
            return mTopicId;
        }

        public boolean isAlreadyRead() {
            return mReadFlag == READ;
        }

        public void markRead() {
            mReadFlag = READ;
        }

        public Enums.CommentCategory getCategory() {
            return Enums.CommentCategory.getCommentCategory(mCategory);
        }

        public Enums.TopicType getTopicType() {
            return Enums.TopicType.getTopicType(mTopicType);
        }

        public String getContent() {
            return Utils.html2String(mContent);
        }

        public long getTimeStamp() {
            return mTimeStamp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;

            Data data = (Data) o;

            if (mId != null ? !mId.equals(data.mId) : data.mId != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return mId != null ? mId.hashCode() : 0;
        }
    }
}