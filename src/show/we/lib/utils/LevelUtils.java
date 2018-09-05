package show.we.lib.utils;

import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.Finance;

/**
 * 等级计算工具
 *
 * @author ll
 * @version 2.4.0
 */
public class LevelUtils {

    private static final int USER_LEVEL_COUNT = 40;
    private static final int STAR_LEVEL_COUNT = 31;

    private static final int[] USER_LEVEL_ICONS = {
            R.drawable.img_user_level_00, R.drawable.img_user_level_01, R.drawable.img_user_level_02
            , R.drawable.img_user_level_03, R.drawable.img_user_level_04, R.drawable.img_user_level_05
            , R.drawable.img_user_level_06, R.drawable.img_user_level_07, R.drawable.img_user_level_08
            , R.drawable.img_user_level_09, R.drawable.img_user_level_10, R.drawable.img_user_level_11
            , R.drawable.img_user_level_12, R.drawable.img_user_level_13, R.drawable.img_user_level_14
            , R.drawable.img_user_level_15, R.drawable.img_user_level_16, R.drawable.img_user_level_17
            , R.drawable.img_user_level_18, R.drawable.img_user_level_19, R.drawable.img_user_level_20
            , R.drawable.img_user_level_21, R.drawable.img_user_level_22, R.drawable.img_user_level_23
            , R.drawable.img_user_level_24, R.drawable.img_user_level_25, R.drawable.img_user_level_26
            , R.drawable.img_user_level_27, R.drawable.img_user_level_28, R.drawable.img_user_level_29
            , R.drawable.img_user_level_30, R.drawable.img_user_level_31, R.drawable.img_user_level_32
            , R.drawable.img_user_level_33, R.drawable.img_user_level_34, R.drawable.img_user_level_35
            , R.drawable.img_user_level_36, R.drawable.img_user_level_37, R.drawable.img_user_level_38
            , R.drawable.img_user_level_39, R.drawable.img_user_level_40
    };

    private static final int[] STAR_LEVEL_ICONS = {
            R.drawable.img_star_level_00, R.drawable.img_star_level_01, R.drawable.img_star_level_02
            , R.drawable.img_star_level_03, R.drawable.img_star_level_04, R.drawable.img_star_level_05
            , R.drawable.img_star_level_06, R.drawable.img_star_level_07, R.drawable.img_star_level_08
            , R.drawable.img_star_level_09, R.drawable.img_star_level_10, R.drawable.img_star_level_11
            , R.drawable.img_star_level_12, R.drawable.img_star_level_13, R.drawable.img_star_level_14
            , R.drawable.img_star_level_15, R.drawable.img_star_level_16, R.drawable.img_star_level_17
            , R.drawable.img_star_level_18, R.drawable.img_star_level_19, R.drawable.img_star_level_20
            , R.drawable.img_star_level_21, R.drawable.img_star_level_22, R.drawable.img_star_level_23
            , R.drawable.img_star_level_24, R.drawable.img_star_level_25, R.drawable.img_star_level_26
            , R.drawable.img_star_level_27, R.drawable.img_star_level_28, R.drawable.img_star_level_29
            , R.drawable.img_star_level_30
    };

    private static final long[] USER_LEVEL_COINS = {
            1000, 2000, 5000, 10000, 20000
            , 36000, 60000, 94000, 140000, 200000
            , 300000, 420000, 570000, 750000, 1000000
            , 1280000, 1580000, 1900000, 2250000, 2630000
            , 3030000, 3460000, 3930000, 4440000, 4990000
            , 5580000, 6220000, 6910000, 7650000, 8440000
            , 9290000, 10220000, 11270000, 12480000, 13910000
            , 15640000, 17770000, 20420000, 23730000, 27840000
    };

    private static final long[] STAR_LEVEL_BEANS = {
             5000, 20000, 50000, 90000
            , 150000, 250000, 400000, 600000, 1000000
            , 1500000, 2200000, 3000000, 4000000, 5000000
            , 6000000, 8000000, 10000000, 12500000, 15000000
            , 17500000, 20000000, 23000000, 26000000, 29500000
            , 33000000, 37000000, 41000000, 45500000, 50000000
            , 55000000, 60000000          
    };

    /**
     * 布衣发言长度
     */
    public static final int MAX_MESSAGE_LENGTH_NEW_PLAYER = 30;
    /**
     * 1富发言长度
     */
    public static final int MAX_MESSAGE_LENGTH_COMMON = 50;
    /**
     * 普通vip发言长度
     */
    public static final int MAX_MESSAGE_LENGTH_NORMAL_VIP = 70;
    /**
     * 至尊vip发言长度
     */
    public static final int MAX_MESSAGE_LENGTH_SUPER_VIP = 100;
    /**
     * 布衣发言时间间隔
     */
    public static final int MIN_SEND_MESSAGE_INTERVAL_ALFA = 30000; //ms
    /**
     * 1富发言时间间隔
     */
    public static final int MIN_SEND_MESSAGE_INTERVAL_BETA = 10000; //ms
    /**
     * 2富发言时间间隔
     */
    public static final int MIN_SEND_MESSAGE_INTERVAL_GAMMA = 5000; //ms
    /**
     * 3富发言时间间隔
     */
    public static final int MIN_SEND_MESSAGE_INTERVAL_DELTA = 2000; //ms
    private static final int NO_LIMIT_INTERVAL = 0; //ms

    /**
     * 财富等级10富
     */
    public static final int WEALTH_RANK_USER_LEVEL_TEN = 10;

    /**
     * 用户等级信息
     */
    public static class LevelInfo {
        /**
         * 用户等级 *
         */
        protected long mLevel;
        /**
         * 从用户等级升到下一级所需要的所有数量 *
         */
        protected long mUpgradeNeedAllNum;
        /**
         * 用户在该等级里已经拥有的数量 *
         */
        protected long mCurLevelExtraNum;
        /**
         * 当前等级的进度
         */
        protected float mProgress;

        /**
         * 构造函数
         *
         * @param level             用户等级
         * @param upgradeNeedAllNum 从用户等级升到下一级所需要的所有数量
         * @param curLevelExtraNum  用户在该等级里已经拥有的数量
         */
        public LevelInfo(long level, long upgradeNeedAllNum, long curLevelExtraNum) {
            mLevel = level;
            mUpgradeNeedAllNum = upgradeNeedAllNum;
            mCurLevelExtraNum = curLevelExtraNum;
            mProgress = mUpgradeNeedAllNum == 0 ? 0 : (float) ((double) mCurLevelExtraNum / mUpgradeNeedAllNum);
        }

        /**
         * 获取用户等级
         *
         * @return 用户等级
         */
        public long getLevel() {
            return mLevel;
        }

        /**
         * 获取从用户等级升到下一级所需要的所有数量
         *
         * @return 从用户等级升到下一级所需要的所有数量
         */
        public long getUpgradeNeedAllNum() {
            return mUpgradeNeedAllNum;
        }

        /**
         * 获取用户在该等级里已经拥有的数量
         *
         * @return 用户在该等级里已经拥有的数量
         */
        public long getCurLevelExtraNum() {
            return mCurLevelExtraNum;
        }

        /**
         * 当前等级的进度
         *
         * @return 当前等级的进度
         */
        public float getCurLevelProgress() {
            return mProgress;
        }
    }

    /**
     * 用户等级信息
     */
    public static class UserLevelInfo extends LevelInfo {
        private int mMaxMessageLength = MAX_MESSAGE_LENGTH_NEW_PLAYER;
        private int mMinSendMessageInterval = MIN_SEND_MESSAGE_INTERVAL_ALFA;
        private boolean mCanPrivateTalk = false;
        private boolean mCanSendVoiceMessage = false;

        /**
         * 构造函数
         *
         * @param level             用户等级
         * @param upgradeNeedAllNum 从用户等级升到下一级所需要的所有数量
         * @param curLevelExtraNum  用户在该等级里已经拥有的数量
         */
        public UserLevelInfo(long level, long upgradeNeedAllNum, long curLevelExtraNum) {
            super(level, upgradeNeedAllNum, curLevelExtraNum);
        }

        /**
         * 构造函数
         *
         * @param level                  用户等级
         * @param upgradeNeedAllNum      从用户等级升到下一级所需要的所有数量
         * @param curLevelExtraNum       用户在该等级里已经拥有的数量
         * @param canPrivateTalk         能否发悄悄话
         * @param canSendVoiceMessage    能否发语音消息
         * @param maxMessageLength       可发送最大消息字数
         * @param minSendMessageInterval 最小发言间隔
         */
        public UserLevelInfo(long level, long upgradeNeedAllNum, long curLevelExtraNum, boolean canPrivateTalk, boolean canSendVoiceMessage
                , int maxMessageLength, int minSendMessageInterval) {
            super(level, upgradeNeedAllNum, curLevelExtraNum);
            mCanPrivateTalk = canPrivateTalk;
            mCanSendVoiceMessage = canSendVoiceMessage;
            mMaxMessageLength = maxMessageLength;
            mMinSendMessageInterval = minSendMessageInterval;
        }

        /**
         * 获取最大发言字数
         *
         * @return 最大发言字数
         */
        public int getMaxMessageLength() {
            return mMaxMessageLength;
        }

        /**
         * 获取最小发言间隔
         *
         * @return 最小发言间隔
         */
        public int getMinSendMessageInterval() {
            return mMinSendMessageInterval;
        }

        /**
         * 能否发悄悄话
         *
         * @return true - 能
         */
        public boolean isCanPrivateTalk() {
            return mCanPrivateTalk;
        }

        /**
         * 能否发语音消息
         *
         * @return true - 能
         */
        public boolean isCanSendVoiceMessage() {
            return mCanSendVoiceMessage;
        }
    }

    /**
     * 获取用户的等级信息
     *
     * @param spendCoin 用户的财富、钱币
     * @param vipType   vipType
     * @return 等级信息
     */
    public static UserLevelInfo getUserLevelInfo(long spendCoin, Enums.VipType vipType) {
        return (UserLevelInfo) getLevelInfo(spendCoin, vipType, false);
    }

	/**
	 * 获取用户的等级信息
	 *
	 * @param finance finance
	 * @return UserLevelInfo
	 */
	public static UserLevelInfo getUserLevelInfo(Finance finance) {
		return (UserLevelInfo) getLevelInfo(finance.getCoinSpendTotal(), Enums.VipType.NONE, false);
	}

    /**
     * 获得用户等级的icon
     * @param level 用户等级数
     * @return icon_id
     */
    public static int getUserLevelIcons(int level) {
        return USER_LEVEL_ICONS[Math.min(Math.abs(level), USER_LEVEL_COUNT - 1)];
    }

    /**
     * 获取用户等级数
     * @return 用户等级数
     */
    public static int getUserLevelCount() {
        return USER_LEVEL_COUNT;
    }

    /**
     * 获取主播等级的icon
     * @param level 主播等级
     * @return icon
     */
    public static int getStarLevelIcons(int level) {
        return STAR_LEVEL_ICONS[Math.min(Math.abs(level), STAR_LEVEL_COUNT - 1)];
    }

    /**
     * 获取主播等级数
     * @return 主播等级数
     */
    public static int getStarLevelCount() {
        return STAR_LEVEL_COUNT;
    }

	/**
     * 获取用户的等级信息
     *
     * @param spendCoin spendCoin
     * @return UserLevelInfo
     */
    public static UserLevelInfo getUserLevelInfo(long spendCoin) {
        return (UserLevelInfo) getLevelInfo(spendCoin, Enums.VipType.NONE, false);
    }

    /**
     * 获取自己的当前等级进度
     *
     * @return 自己的当前等级进度
     */
    public static float getCurLevelProgress() {
        return LoginUtils.isAlreadyLogin() ? getUserLevelInfo(UserInfoUtils.getUserInfo().getData().getFinance()
                .getCoinSpendTotal()).getCurLevelProgress() : 0;
    }

    /**
     * 获取主播的等级信息
     *
     * @param beanCount 银币数
     * @param beanCount 银豆数
     * @return 等级信息
     */
    public static LevelInfo getStarLevelInfo(long beanCount) {
        return getLevelInfo(beanCount, Enums.VipType.NONE, true);
    }

    private static LevelInfo getLevelInfo(long coin, Enums.VipType vipType, boolean isStar) {
        int level = 0;
        long upgradeNeedAllNum = 1;
        long curLevelExtraNum = 1;
        long[] levelCoins = isStar ? STAR_LEVEL_BEANS : USER_LEVEL_COINS;
        for (; level < levelCoins.length; level++) {
            if (coin < levelCoins[level]) {
                long preLevelCoins = level == 0 ? 0 : levelCoins[level - 1];
                curLevelExtraNum = coin - preLevelCoins;
                upgradeNeedAllNum = levelCoins[level] - preLevelCoins;
                break;
            }
        }
        
        if (!isStar) {
            boolean canPrivateTalk = false;
            boolean canSendVoiceMessage = false;
            int maxMessageLength = MAX_MESSAGE_LENGTH_NEW_PLAYER;
            int minSendMessageInterval = MIN_SEND_MESSAGE_INTERVAL_ALFA;

            if (vipType == Enums.VipType.SUPER_VIP) {
                canPrivateTalk = true;
                canSendVoiceMessage = true;
                minSendMessageInterval = NO_LIMIT_INTERVAL;
                maxMessageLength = MAX_MESSAGE_LENGTH_SUPER_VIP;
            } else if (vipType == Enums.VipType.COMMON_VIP || vipType == Enums.VipType.TRIAL_VIP) {
                canPrivateTalk = true;
                canSendVoiceMessage = true;
                minSendMessageInterval = NO_LIMIT_INTERVAL;
                maxMessageLength = MAX_MESSAGE_LENGTH_NORMAL_VIP;
            } else {
                if (level > 3) { //四富或以上
                    canPrivateTalk = true;
                    canSendVoiceMessage = true;
                    maxMessageLength = MAX_MESSAGE_LENGTH_COMMON;
                    minSendMessageInterval = NO_LIMIT_INTERVAL;
                } else if (level > 2) { //三富
                    maxMessageLength = MAX_MESSAGE_LENGTH_COMMON;
                    minSendMessageInterval = MIN_SEND_MESSAGE_INTERVAL_DELTA;
                } else if (level > 1) { //二富
                    maxMessageLength = MAX_MESSAGE_LENGTH_COMMON;
                    minSendMessageInterval = MIN_SEND_MESSAGE_INTERVAL_GAMMA;
                } else if (level > 0) { //一富
                    maxMessageLength = MAX_MESSAGE_LENGTH_COMMON;
                    minSendMessageInterval = MIN_SEND_MESSAGE_INTERVAL_BETA;
                }
            }

            return new UserLevelInfo(level, upgradeNeedAllNum, curLevelExtraNum, canPrivateTalk, canSendVoiceMessage
                    , maxMessageLength, minSendMessageInterval);
        }

        return new LevelInfo(level, upgradeNeedAllNum, curLevelExtraNum);
    }
}
