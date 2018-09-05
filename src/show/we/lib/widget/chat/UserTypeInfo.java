package show.we.lib.widget.chat;

import show.we.lib.config.Enums;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class UserTypeInfo {

    private long mId;
    private String mNickName;
    private Enums.VipType mVipType;
    private long mUserLevel;
    private long mStarLevel;
    private boolean mIsStar;
    private boolean mIsVipHiding;

    /**
     * 构造函数
     *
     * @param id          id
     * @param nickName    nickName
     * @param vipType     vipType
     * @param starLevel   starLevel
     * @param userLevel   userLevel
     * @param isStar      isStar
     * @param isVipHiding isVipHiding
     */
    public UserTypeInfo(long id, String nickName, Enums.VipType vipType, long starLevel, long userLevel, boolean isStar, boolean isVipHiding) {
        mId = id;
        mNickName = nickName;
        mVipType = vipType;
        mStarLevel = starLevel;
        mUserLevel = userLevel;
        mIsStar = isStar;
        mIsVipHiding = isVipHiding;
    }

    /**
     * getId
     *
     * @return mId
     */
    public long getId() {
        return mId;
    }

    /**
     * getNickName
     *
     * @return mNickName
     */
    public String getNickName() {
        return mNickName;
    }

    /**
     * getVipType
     *
     * @return mVipType
     */
    public Enums.VipType getVipType() {
        return mVipType;
    }

    /**
     * getStarLevel
     *
     * @return mStarLevel
     */
    public long getStarLevel() {
        return mStarLevel;
    }

    /**
     * getUserLevel
     *
     * @return mUserLevel
     */
    public long getUserLevel() {
        return mUserLevel;
    }

    /**
     * getIsStar
     *
     * @return mIsStar
     */
    public boolean getIsStar() {
        return mIsStar;
    }

    /**
     * getIsVipHiding
     *
     * @return mIsVipHiding
     */
    public boolean getIsVipHiding() {
        return mIsVipHiding;
    }
}
