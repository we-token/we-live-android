package show.we.lib.model;

/**
 * Created by CG on 13-11-13.
 *
 * @author ll
 * @version 3.1.0
 */
public class StarRoomInfo {

    private boolean mIsLive;

    private long mRoomId;

    private long mStarId;

    private String mPicUrl;

    private String mCoverUrl;

    private String mNickName;

    private int mConstellation;

    private int mSex;

    private String mLocation;

    private int mVisitorCount;

    private int mStarLevel;

    private int mFollowers;

    public StarRoomInfo(boolean isLive, long roomId, long starId, String picUrl, String coverUrl, String nickName,
                        int constellation, int sex, String location, int visitorCount, int starLevel, int follows) {
        mIsLive = isLive;
        mRoomId = roomId;
        mStarId = starId;
        mPicUrl = picUrl;
        mCoverUrl = coverUrl;
        mNickName = nickName;
        mConstellation = constellation;
        mSex = sex;
        mLocation = location;
        mVisitorCount = visitorCount;
        mStarLevel = starLevel;
        mFollowers = follows;
    }

    public int getmFollowers() {
        return mFollowers;
    }
    public boolean getIsLive() {
        return mIsLive;
    }

    public long getRoomId() {
        return mRoomId;
    }

    public long getStarId() {
        return mStarId;
    }

    public String getPicUrl() {
        return mPicUrl;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public String getNickName() {
        return mNickName;
    }

    public int getConstellation() {
        return mConstellation;
    }

    public int getSex() {
        return mSex;
    }

    public String getLocation() {
        return mLocation;
    }

    public int getVisiterCount() {
        return mVisitorCount;
    }

    public int getStarLevel() {
        return mStarLevel;
    }
}
