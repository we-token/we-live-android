package show.we.lib.model;

import com.google.gson.annotations.SerializedName;
import show.we.sdk.request.BaseResult;

import java.io.Serializable;
import java.util.List;

/**
 * @author ll
 * @version 1.0.0
 *          Date: 13-6-27
 *          Time: 下午1:39
 *          奇迹礼物数据
 */
public class WonderGiftResult extends BaseResult {

    @SerializedName("my")
    private long mThisRoomGiftCount;   //本房间主播收到礼物数

    @SerializedName("s")
    private int mState = -1;   //当前进行状态

    @SerializedName("t")
    private long mServerTime; //服务器时间

    @SerializedName("data")
    private Data mData; //数据

    public long getThisRoomGiftCount() {
        return mThisRoomGiftCount;
    }

    public int getState() {
        return mState;
    }

    public long getServerTime() {
        return mServerTime;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        this.mData = data;
    }

    public class Data implements Serializable {

        @SerializedName("_flag")
        private long mFlag;

        @SerializedName("fan1")
        private HistoryWonderGiftResult.WonderFan mFan;

        @SerializedName("stime")
        private long mStartTime;

        @SerializedName("etime")
        private long mEndTime;

        @SerializedName("gift")
        private GiftListResult.Gift mGift;

        @SerializedName("star1")
        private HistoryWonderGiftResult.WonderStar mStar;

        @SerializedName("top")
        private List<Top> mTopList;

        public long getFlag() {
            return mFlag;
        }

        public void setFlag(long flag) {
            this.mFlag = flag;
        }

        public HistoryWonderGiftResult.WonderFan getFan() {
            return mFan;
        }

        public void setFan(HistoryWonderGiftResult.WonderFan fan) {
            this.mFan = fan;
        }

        public long getStartTime() {
            return mStartTime;
        }

        public void setStartTime(long startTime) {
            this.mStartTime = startTime;
        }

        public long getEndTime() {
            return mEndTime;
        }

        public void setEndTime(long endTime) {
            this.mEndTime = endTime;
        }

        public GiftListResult.Gift getGift() {
            return mGift;
        }

        public void setGift(GiftListResult.Gift mGift) {
            this.mGift = mGift;
        }

        public HistoryWonderGiftResult.WonderStar getStar() {
            return mStar;
        }

        public void setStar(HistoryWonderGiftResult.WonderStar mStar) {
            this.mStar = mStar;
        }

        public List<Top> getTopList() {
            return mTopList;
        }

        public class Top implements Serializable {

            @SerializedName("_id")
            private long mId;

            @SerializedName("count")
            private int mCount;

            @SerializedName("finance")
            private Finance mFinance;

            @SerializedName("nick_name")
            private String mNickName;

            @SerializedName("pic")
            private String mPic;

            public long getId() {
                return mId;
            }

            public void setId(long id) {
                this.mId = id;
            }

            public int getCount() {
                return mCount;
            }

            public void setmCount(int count) {
                this.mCount = count;
            }

            public Finance getFinance() {
                return mFinance;
            }

            public void setFinance(Finance finance) {
                this.mFinance = finance;
            }

            public String getNickName() {
                return mNickName;
            }

            public void setNickName(String nickName) {
                this.mNickName = nickName;
            }

            public String getPic() {
                return mPic;
            }

            public void setPic(String pic) {
                this.mPic = pic;
            }
        }
    }
}
