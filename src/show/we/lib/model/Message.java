package show.we.lib.model;

import android.text.Html;
import com.google.gson.annotations.SerializedName;
import show.we.lib.config.Enums;
import show.we.lib.utils.NullObjectAssert;
import show.we.lib.utils.Utils;
import show.we.sdk.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Message {

    public static class SendModel {
        @SerializedName("msg")
        private Msg mMsg;

        @SerializedName("user_id")
        private long mUserId;

        public static class Msg {
            @SerializedName("to")
            private To mTo;

            @SerializedName("content")
            private String mContent;

            @SerializedName("level")
            private long mLevel;

            @SerializedName("voice")
            private Voice mVoice;


            public static final class Voice {

                public Voice() {
                }

                public Voice(String url, long duration) {
                    mUrl = url;
                    mDuration = duration;
                }

                @SerializedName("duration")
                private long mDuration;

                @SerializedName("url")
                private String mUrl;

                public long getDuration() {
                    return mDuration;
                }

                public String getUrl() {
                    return NullObjectAssert.assertNull(mUrl, String.class);
                }
            }

            public To getTo() {
                return mTo;
            }

            public void setTo(To to) {
                this.mTo = to;
            }

            public String getContent() {
                return NullObjectAssert.assertNull(Utils.html2String(mContent), String.class);
            }

            public void setContent(String content) {
                this.mContent = content;
            }

            public long getLevel() {
                return mLevel;
            }

            public void setLevel(long level) {
                this.mLevel = level;
            }

            public Voice getVoice() {
                return NullObjectAssert.assertNull(mVoice, Voice.class);
            }

            public void setVoice(Voice voice) {
                mVoice = voice;
            }
        }

        public Msg getMsg() {
            return mMsg;
        }

        public void setMsg(Msg msg) {
            this.mMsg = msg;
        }

        public long getUserId() {
            return mUserId;
        }

        public void setUserId(long userId) {
            this.mUserId = userId;
        }
    }

    public static class ReceiveModel {
        @SerializedName("from")
        private From mFrom;

        @SerializedName("to")
        private To mTo;

        @SerializedName("content")
        private String mContent;

        @SerializedName("room_id")
        private long mRoomId;

        @SerializedName("level")
        private long mLevel;

        public From getFrom() {
            return mFrom;
        }

        public void setFrom(From from) {
            this.mFrom = from;
        }

        public To getTo() {
            return mTo;
        }

        public void setTo(To to) {
            this.mTo = to;
        }

        public String getContent() {
            return NullObjectAssert.assertNull(Utils.html2String(mContent), String.class);
        }

        public void setContent(String content) {
            this.mContent = StringUtils.isEmpty(content) ? content : Html.fromHtml(content).toString();
        }

        public long getRoomId() {
            return mRoomId;
        }

        public void setRoomId(long roomId) {
            this.mRoomId = roomId;
        }

        public long getLevel() {
            return mLevel;
        }

        public void setLevel(long level) {
            this.mLevel = level;
        }
    }

    public static class To {
        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("_id")
        private long mId;

        @SerializedName("private")
        private boolean mPrivate;

        @SerializedName("vip")
        private int mVip;

        @SerializedName("priv")
        private int mType;

        @SerializedName("level")
        private long mLevel;

        @SerializedName("finance")
        private Finance mFinance;

        public To() {

        }

        public To(To to) {
            mNickName = to.getNickName() == null ? null : new String(to.getNickName());
            mId = to.getId();
            mPrivate = to.getPrivate();
        }

        public String getNickName() {
            return mNickName;
        }

        public void setNickName(String nickName) {
            this.mNickName = nickName;
        }

        public long getId() {
            return mId;
        }

        public void setId(long id) {
            this.mId = id;
        }

        public boolean getPrivate() {
            return mPrivate;
        }

        public void setPrivate(boolean isPrivate) {
            this.mPrivate = isPrivate;
        }

        public Enums.VipType getVipType() {
            for (Enums.VipType vipType : Enums.VipType.values()) {
                if (vipType.getValue() == mVip) {
                    return vipType;
                }
            }
            return Enums.VipType.NONE;
        }

        public void setVipType(Enums.VipType vipType) {
            mVip = vipType.getValue();
        }


        public int getType() {
            return mType;
        }

        public void setType(int type) {
            this.mType = type;
        }

        public long getLevel() {
            return mLevel;
        }

        public void setLevel(long level) {
            this.mLevel = level;
        }

        public Finance getFinance() {
            return NullObjectAssert.assertNull(mFinance, Finance.class);
        }

        public void setFinance(Finance finance) {
            mFinance = finance;
        }
    }

    public static class From {
        @SerializedName("nick_name")
        private String mNickName;

        @SerializedName("_id")
        private long mId;

        @SerializedName("vip")
        private int mVip;

        @SerializedName("priv")
        private int mType;

        @SerializedName("finance")
        private Finance mFinance;

        public From() {

        }

        public From(From from) {
            mNickName = from.getNickName() == null ? null : new String(from.getNickName());
            mId = from.getId();
        }

        public String getNickName() {
            return NullObjectAssert.assertNull(Utils.html2String(mNickName), String.class);
        }

        public void setNickName(String nickName) {
            this.mNickName = nickName;
        }

        public long getId() {
            return mId;
        }

        public void setId(long id) {
            this.mId = id;
        }

        public Enums.VipType getVipType() {
            for (Enums.VipType vipType : Enums.VipType.values()) {
                if (vipType.getValue() == mVip) {
                    return vipType;
                }
            }
            return Enums.VipType.NONE;
        }

        public void setVipType(Enums.VipType vipType) {
            mVip = vipType.getValue();
        }

        public int getType() {
            return mType;
        }

        public void setType(int type) {
            this.mType = type;
        }

        public Finance getFinance() {
            return NullObjectAssert.assertNull(mFinance, Finance.class);
        }

        public void setFinance(Finance finance) {
            mFinance = finance;
        }
    }

    public static class Gift {
        @SerializedName("_id")
        private long mId;

        @SerializedName("name")
        private String mName;

        @SerializedName("count")
        private long mCount;

        public Gift() {

        }

        public Gift(Gift gift) {
            mId = gift.getId();
            mName = gift.getName() == null ? null : new String(gift.getName());
            mCount = gift.getCount();
        }

        public long getId() {
            return mId;
        }

        public void setId(long id) {
            this.mId = id;
        }

        public String getName() {
            return NullObjectAssert.assertNull(mName, String.class);
        }

        public void setName(String name) {
            this.mName = name;
        }

        public long getCount() {
            return mCount;
        }

        public void setCount(long count) {
            this.mCount = count;
        }
    }

    public static class Room {
        @SerializedName("_id")
        private int mId;
        @SerializedName("finance")
        private Finance mFinance;
        @SerializedName("nick_name")
        private String mStarName;
        @SerializedName("priv")
        private int mPriv;
        @SerializedName("vip")
        private int mVip;

        public int getId() {
            return mId;
        }

        public Finance getFinance() {
            return NullObjectAssert.assertNull(mFinance, Finance.class);
        }

        public String getStarName() {
            return NullObjectAssert.assertNull(mStarName, String.class);
        }

        public int getPriv() {
            return mPriv;
        }

        public int getVip() {
            return mVip;
        }
    }

    public static class SystemNoticeModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("msg")
            private String mMessage;

            public String getMessage() {
                return NullObjectAssert.assertNull(mMessage, String.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class SongRefuseModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("song_name")
            private String mSongName;

            public String getSongName() {
                return NullObjectAssert.assertNull(mSongName, String.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class SongAgreeModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("nick_name")
            private String mNickName;
            @SerializedName("song_name")
            private String mSongName;

            public String getNickName() {
                return NullObjectAssert.assertNull(mNickName, String.class);
            }

            public String getSongName() {
                return NullObjectAssert.assertNull(mSongName, String.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class ShutUpModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("f_id")
            private long mFromId;
            @SerializedName("f_name")
            private String mFromName;
            @SerializedName("xy_user_id")
            private long mToId;
            @SerializedName("nick_name")
            private String mToName;
            @SerializedName("minute")
            private long mTime;

            public long getFromId() {
                return mFromId;
            }

            public String getFromName() {
                return NullObjectAssert.assertNull(mFromName, String.class);
            }

            public long getToId() {
                return mToId;
            }

            public String getToName() {
                return NullObjectAssert.assertNull(mToName, String.class);
            }

            public long getTime() {
                return mTime;
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class EnterRoomModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("_id")
            private long mUserId;
            @SerializedName("nick_name")
            private String mNickName;
            @SerializedName("vip")
            private int mVip;
            @SerializedName("priv")
            private int mUserType;
            @SerializedName("car")
            private long mMountId;
            @SerializedName("vip_hiding")
            private int mVipHidingFlag;
            @SerializedName("spend")
            private long mSpend;

            public long getUserId() {
                return mUserId;
            }

            public String getNickName() {
                return NullObjectAssert.assertNull(mNickName, String.class);
            }

            public int getVip() {
                return mVip;
            }

            public int getUserType() {
                return mUserType;
            }

            public long getMountId() {
                return mMountId;
            }

            public int getVipHidingFlag() {
                return mVipHidingFlag;
            }

            public long getSpend() {
                return mSpend;
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class PuzzleWinModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("user_id")
            private long mUserId;
            @SerializedName("priv")
            private int mPriv;
            @SerializedName("vip")
            private int mVip;
            @SerializedName("level")
            private int mLevel;
            @SerializedName("user_nick")
            private String mUserNickName;
            @SerializedName("cost")
            private String mCost;
            @SerializedName("step")
            private String mStep;

            public long getUserId() {
                return mUserId;
            }

            public int getPriv() {
                return mPriv;
            }

            public int getVip() {
                return mVip;
            }

            public int getLevel() {
                return mLevel;
            }

            public String getUserNickName() {
                return NullObjectAssert.assertNull(mUserNickName, String.class);
            }

            public String getCost() {
                return NullObjectAssert.assertNull(mCost, String.class);
            }

            public String getStep() {
                return NullObjectAssert.assertNull(mStep, String.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }


    public static class SendFeatherModel {

        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("_id")
            private long mId;

            @SerializedName("nick_name")
            private String mNickName;

            public long getId() {
                return mId;
            }

            public String getNickName() {
                return NullObjectAssert.assertNull(Utils.html2String(mNickName), String.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class SendGiftModel {
        @SerializedName("action")
        private String mAction;

        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("from")
            private From mFrom;

            @SerializedName("to")
            private To mTo;

            @SerializedName("gift")
            private Gift mGift;

            @SerializedName("room_id")
            private long mRoomId;

            @SerializedName("win_coin")
            private int[] mWinCoin;

            @SerializedName("t")
            private long mTime;

            @SerializedName("level")
            private long mLevel;

            public From getFrom() {
                return mFrom;
            }

            public void setFrom(From from) {
                this.mFrom = from;
            }

            public To getTo() {
                return mTo;
            }

            public void setTo(To to) {
                this.mTo = to;
            }

            public Gift getGift() {
                return mGift;
            }

            public void setGift(Gift gift) {
                this.mGift = gift;
            }

            public long getRoomId() {
                return mRoomId;
            }

            public void setRoomId(long roomId) {
                this.mRoomId = roomId;
            }

            public int[] getWinCoin() {
                return mWinCoin;
            }

            public void setWinCoin(int[] winCoin) {
                this.mWinCoin = winCoin;
            }

            public long getLevel() {
                return mLevel;
            }

            public void setLevel(long level) {
                this.mLevel = level;
            }

            public long getTime() {
                return mTime;
            }

            public void setTime(long time) {
                mTime = time;
            }
        }

        public String getAction() {
            return mAction;
        }

        public Data getData() {
            return mData;
        }

        public void setData(Data data) {
            this.mData = data;
        }
    }

    public static class BroadCastModel {
        @SerializedName("action")
        private String mAction;

        @SerializedName("data_d")
        private Data mData;

        public String getAction() {
            return mAction;
        }

        public void setAction(String action) {
            this.mAction = action;
        }

        public Data getData() {
            return mData;
        }

        public void setData(Data data) {
            this.mData = data;
        }

        public static class Data {
            @SerializedName("from")
            private From mFrom;

            @SerializedName("message")
            private String mMessage;

            @SerializedName("room_id")
            private long mRoomId;

            @SerializedName("room_star")
            private String mStarName;

            @SerializedName("url")
            private boolean mIsUrl;

            public From getFrom() {
                return mFrom;
            }

            public void setFrom(From from) {
                this.mFrom = from;
            }

            public String getMessage() {
                return Utils.html2String(mMessage);
            }

            public void setMessage(String message) {
                this.mMessage = StringUtils.isEmpty(message) ? message : Html.fromHtml(message).toString();
            }

            public String getStarName() {
                return Utils.html2String(mStarName);
            }

            public long getRoomId() {
                return mRoomId;
            }

            public void setRoomId(long roomId) {
                this.mRoomId = roomId;
            }

            public boolean getIsUrl() {
                return mIsUrl;
            }

            public void setIsUrl(boolean isUrl) {
                this.mIsUrl = isUrl;
            }
        }
    }

    public static class FortuneModel {
        @SerializedName("action")
        private String mAction;

        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("count")
            private int mCount;
            @SerializedName("from")
            private From mFrom;

            public int getCount() {
                return mCount;
            }

            public From getFrom() {
                return mFrom == null ? new From() : mFrom;
            }
        }

        public Data getData() {
            return mData == null ? new Data() : mData;
        }
    }

    public static class TreasureNotifyModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("count")
            private int mCount;
            @SerializedName("from")
            private From mFrom;

            public int getCount() {
                return mCount;
            }

            public From getFrom() {
                return NullObjectAssert.assertNull(mFrom, From.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class TreasureMarqueeModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("count")
            private int mCount;
            @SerializedName("from")
            private From mFrom;
            @SerializedName("rooms")
            private Room mRoom;

            public int getCount() {
                return mCount;
            }

            public From getFrom() {
                return NullObjectAssert.assertNull(mFrom, From.class);
            }

            public Room getRoom() {
                return NullObjectAssert.assertNull(mRoom, Room.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class TreasureRoom {
        @SerializedName("_id")
        private long mUserId;
        @SerializedName("bean")
        private long mBean;
        @SerializedName("nick_name")
        private String mNickName;

        public long getUserId() {
            return mUserId;
        }

        public long getBean() {
            return mBean;
        }

        public String getNickName() {
            return NullObjectAssert.assertNull(mNickName, String.class);
        }
    }

    public static class TreasureNoticeModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("room")
            private TreasureRoom mRoom;

            public TreasureRoom getRoom() {
                return NullObjectAssert.assertNull(mRoom, TreasureRoom.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class TreasureAwardModel {
        @SerializedName("data_d")
        private Data mData;

        public static class Data {
            @SerializedName("room")
            private TreasureRoom mRoom;

            public TreasureRoom getRoom() {
                return NullObjectAssert.assertNull(mRoom, TreasureRoom.class);
            }
        }

        public Data getData() {
            return NullObjectAssert.assertNull(mData, Data.class);
        }
    }

    public static class TreasureRoomModel {
        @SerializedName("data_d")
        private List<Data> mDataList;

        public static class Data {
            private AwardUser mCurrentAwardUser;
            @SerializedName("first_user")
            private AwardUser mFirstAwardUser;
            @SerializedName("sec_user1")
            private AwardUser mSecondAwardUser;
            @SerializedName("sec_user2")
            private AwardUser mThirdAwardUser;
            @SerializedName("third_user1")
            private AwardUser mFourthAwardUser;
            @SerializedName("third_user2")
            private AwardUser mFifthAwardUser;

            @SerializedName("obtain_coin")
            private int mObtainCoin;
            @SerializedName("room_id")
            private String mRoomId;
            @SerializedName("t")
            private long mTimeStamp;
            @SerializedName("other")
            private int mOtherCoin;

            public AwardUser getFirstAwardUser() {
                return NullObjectAssert.assertNull(mFirstAwardUser, AwardUser.class);
            }

            public AwardUser getSecondAwardUser() {
                return NullObjectAssert.assertNull(mSecondAwardUser, AwardUser.class);
            }

            public AwardUser getThirdAwardUser() {
                return NullObjectAssert.assertNull(mThirdAwardUser, AwardUser.class);
            }

            public AwardUser getFourthAwardUser() {
                return NullObjectAssert.assertNull(mFourthAwardUser, AwardUser.class);
            }

            public AwardUser getFifthAwardUser() {
                return NullObjectAssert.assertNull(mFifthAwardUser, AwardUser.class);
            }

            public int getObtainCoin() {
                return mObtainCoin;
            }

            public String getRoomId() {
                return NullObjectAssert.assertNull(mRoomId, String.class);
            }

            public long getTimeStamp() {
                return mTimeStamp;
            }

            public int getOtherCoin() {
                return mOtherCoin;
            }

            public AwardUser getCurrentAwardUser() {
                return mCurrentAwardUser;
            }

            public void setCurrentAwardUser(AwardUser mCurrentAwardUser) {
                this.mCurrentAwardUser = mCurrentAwardUser;
            }
        }

        public static class AwardUser {
            @SerializedName("_id")
            private long mUserId;
            @SerializedName("finance")
            private Finance mFinance;
            @SerializedName("nick_name")
            private String mNickName;
            @SerializedName("obtain_coin")
            private int mObtainCoin;

            public long getUserId() {
                return mUserId;
            }

            public Finance getFinance() {
                return NullObjectAssert.assertNull(mFinance, Finance.class);
            }

            public String getNickName() {
                return NullObjectAssert.assertNull(mNickName, String.class);
            }

            public int getObtainCoin() {
                return mObtainCoin;
            }
        }

        public List<Data> getDataList() {
            return mDataList == null ? new ArrayList<Data>() : mDataList;
        }
    }
}
