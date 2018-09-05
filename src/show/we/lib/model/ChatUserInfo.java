package show.we.lib.model;

import show.we.lib.config.Enums;

/**
 * Created by Administrator on 13-6-26.
 * 聊天用户信息
 *
 * @author ll
 * @version 2.0.0
 */
public class ChatUserInfo {

    private long mUserId;
    private String mUserName;
	private String mUserPic;
    private Enums.VipType mVipType;
	/**
	 * 客服、运营、代理
	 */
    private int mType;
	/**
	 * 主播、管理、观众
	 */
	private int mUserType;
    private long mLevel;
    private boolean mIsVipHiding;
    private Family mFamily;

    /**
     * 构造函数
     */
    public ChatUserInfo() {
    }

    /**
     * 构造函数
     *
     * @param userId      userId
     * @param userName    userName
     * @param vipType     vipType
     * @param type        type
     * @param level       level
     * @param isVipHiding isVipHiding
     */
    public ChatUserInfo(long userId, String userName, Enums.VipType vipType, int type, long level, boolean isVipHiding) {
        mUserId = userId;
        mUserName = userName;
        mVipType = vipType;
        mType = type;
        mLevel = level;
        mIsVipHiding = isVipHiding;
    }

    /**
     * setUserId
     *
     * @param userId userId
     */
    public void setId(long userId) {
        mUserId = userId;
    }

    /**
     * getUserId
     *
     * @return UserId
     */
    public long getId() {
        return mUserId;
    }

    /**
     * setUserName
     *
     * @param userName userName
     */
    public void setName(String userName) {
        mUserName = userName;
    }

    /**
     * getUserName
     *
     * @return UserName
     */
    public String getName() {
        return mUserName;
    }

	/**
	 * setUserPic
	 * @param userPic userPic
	 */
	public void setUserPic(String userPic) {
		mUserPic = userPic;
	}

	/**
	 * getUserPic
	 * @return mUserPic
	 */
	public String getUserPic() {
		return mUserPic;
	}

    /**
     * setVip
     *
     * @param vipType vipType
     */
    public void setVipType(Enums.VipType vipType) {
        mVipType = vipType;
    }

    /**
     * getVipValue
     *
     * @return vipType
     */
    public Enums.VipType getVipType() {
        return mVipType;
    }

    /**
     * setType
     *
     * @param type type
     */
    public void setType(int type) {
        mType = type;
    }

    /**
     * getType
     *
     * @return type
     */
    public int getType() {
        return mType;
    }

	/**
	 * setUserType
	 * @param userType userType
	 */
	public void setUserType(int userType) {
		mUserType = userType;
	}

	/**
	 * getUserType
	 * @return mUserType
	 */
	public int getUserType() {
		return mUserType;
	}

    /**
     * setLevel
     *
     * @param level level
     */
    public void setLevel(long level) {
        mLevel = level;
    }

    /**
     * getLevel
     *
     * @return level
     */
    public long getLevel() {
        return mLevel;
    }

    /**
     * 获取用户是否隐身
     *
     * @return true - 隐身
     */
    public boolean isVipHiding() {
        return mIsVipHiding;
    }

    /**
     * 设置用户是否隐身
     *
     * @param isVipHiding 是否隐身
     */
    public void setVipHiding(boolean isVipHiding) {
        this.mIsVipHiding = isVipHiding;
    }

    /**
     * setFamily
     *
     * @param family family
     */
    public void setFamily(Family family) {
        mFamily = family;
    }

    /**
     * getFamily
     *
     * @return mFamily
     */
    public Family getFamily() {
        return mFamily;
    }
}
