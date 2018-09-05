package show.we.lib.config;

import show.we.lib.R;
import show.we.lib.utils.CacheUtils;
import show.we.lib.widget.slotgame.SlotRewardItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举类型
 *
 * @author ll
 * @version 2.5.0
 */
public class Enums {
    private static final String EXPENSE_TYPE_LIVE = "live";
    private static final String EXPENSE_TYPE_MONTH = "month";
    private static final String EXPENSE_TYPE_TOTAL = "total";

    /**
     * 锤子的类型
     */
    public static enum AccuseType {
        /**  违禁品、传销 **/
        VIOLATE(0, "违禁品、传销"),
        /**  色情低俗 **/
        PORN(1, "色情低俗"),
        /**  政治违规 **/
        POLITICAL(2, "政治违规"),
        /**  外站拉人 **/
        UNFAIR_COMPETITION(3, "外站拉人");

        private int mType;
        private String mDesc;

        private AccuseType(int type, String desc) {
            mType = type;
            mDesc = desc;
        }

        /**
         * @return mType
         */
        public int getType() {
            return mType;
        }

        /**
         * @return mDesc
         */
        public String getDesc() {
            return mDesc;
        }
    }

    /**
     * 购买Vip的类型
     */
    public enum BuyVipType {
        BUY_NORMAL_VIP,    //		    * 购买普通Vip *
        RENEW_NORMAL_VIP,  //	 	    * 续费普通Vip *
        BUY_EXTREME_VIP,   //    	    * 购买至尊Vip *
        RENEW_EXTREME_VIP  // 			* 续费至尊Vip *
    }

    /**
     * 购买Vip的月数
     */
    public enum VipMonth {
        ONE(0), 	//	* 一个月 *
        THREE(1),   //  * 三个月 *
        SIX(2),     //  * 六个月 *
        TWELVE(3);  //	* 十二个月 *

        private int mValue;

        /**
         * PageType
         * @param value value
         */
        private VipMonth(int value) {
            mValue = value;
        }

        /**
         * getValue
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 家族列表类型
     */
    public enum FamilyListType {
        /**
         * 全部家族 按创建时间
         */
        ALL_FAMILY,
        /**
         * 人气家族 按成员数量
         */
        SUPPORT_RANK_FAMILY,
        /**
         * 星光家族 按主播数量
         */
        STAR_RANK_FAMILY,
        /**
         * 财富家族 按财富总值
         */
        WEALTH_RANK_FAMILY,
    }

    /**
     * 家族详情ListView的类型
     */
    public enum FamilyDetailsListType {
        /**
         * 家族话题
         */
        FAMILY_TOPIC,

        /**
         * 家族主播
         */
        FAMILY_STAR,

        /**
         * 家族成员
         */
        FAMILY_MEMBER
    }

    /**
     * 列表类型
     *
     * @author ll
     * @version 1.0.0
     */
    public enum RoomListType {
        /**
         * 人气主播-和pc端相同
         */
        ALL("AllRoomList"),
        /**
         * 人气主播
         */
        RECOMMEND("RecommendRoomList"),
        /**
         * 新秀推荐
         */
        NEW("NewRoomList"),
        /**
         * 最新开播
         */
        LATEST("LatestRoomList"),
        /**
         * 所有的主播（按等级排序）
         */
        ALL_RANK_STAR("AllRankStarRoomList"),
        /**
         * 超星
         */
        SUPER_STAR("SuperStarRoomList"),
        /**
         * 巨星
         */
        GIANT_STAR("GiantStarRoomList"),
        /**
         * 明星
         */
        BRIGHT_STAR("BrightStarRoomList"),
        /**
         * 红星
         */
        RED_STAR("RedStarRoomList");

        private String mValue;

        private RoomListType(String value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 消费排行类型
     */
    public enum ExpenseType {
        /**
         * 本场消费排行
         */
        LIVE(EXPENSE_TYPE_LIVE),
        /**
         * 本月消费排行
         */
        MONTH(EXPENSE_TYPE_MONTH),
        /**
         * 总消费排行
         */
        TOTAL(EXPENSE_TYPE_TOTAL);

        private final String mValue;

        private ExpenseType(String val) {
            mValue = val;
        }

        /**
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * 广告类型
     */
    public enum BannerType {
        /**
         * 首页海报 *
         */
        MAIN(2),
        /**
         * 充值页公告 *
         */
        RECHARGE(3);
        private final int mValue;

        private BannerType(int val) {
            mValue = val;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 主播照片类型
     */
    public static enum StarPhotoType {
        /**
         * 直播秀 *
         */
        LIVE(0),
        /**
         * 生活照 *
         */
        LIFE(1);
        private final int mValue;

        private StarPhotoType(int val) {
            mValue = val;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 页面类型枚举
     *
     * @author ll
     * @version 1.0.0
     */
    public enum PageType {
        /**
         * 收藏主播、人气主播、新星推荐、最新开播页面、超星页面、巨星页面、明星页面、红星页面、最近观看
         */
        PAGE_TYPE_COMMEND_STAR(0),

        /**
         * 排行榜-主播排行、财富排行、礼物排行、奇迹礼物排行
         */
        PAGE_TYPE_RANK(21),

        /**
         * 家族
         */
        PAGE_TYPE_FAMILY(22),


        /**
         * 超星页面
         */
        PAGE_TYPE_SUPER_STAR(1),
        /**
         * 巨星页面
         */
        PAGE_TYPE_GIANT_STAR(2),
        /**
         * 明星页面
         */
        PAGE_TYPE_BRIGHT_STAR(3),
        /**
         * 红星页面
         */
        PAGE_TYPE_RED_STAR(4),

        /**
         * 明星排行榜
         */
        PAGE_TYPE_RANK_STAR_TOP(5),
        /**
         * 财富排行榜
         */
        PAGE_TYPE_RANK_WEALTH_TOP(6),
        /**
         * 点播榜
         */
        PAGE_TYPE_RANK_SONG_ORDER(7),
        /**
         * 魅力榜(羽毛榜)
         */
        PAGE_TYPE_RANK_FEATHER_TOP(8),
        /**
         * 礼物排行榜
         */
        PAGE_TYPE_RANK_GIFT(9),

        /**
         * 主播分类和排行的列表
         */
        PAGE_TYPE_CATEGORY_RANK_LIST(10),

        /**
         * 进入商城
         */
        PAGE_TYPE_ENTRY_MALL(11),

        /**
         * 设置页面
         */
        PAGE_TYPE_SETTING(12),

        /**
         * 观众页面
         */
        PAGE_TYPE_AUDIENCE(13),

        /**
         * 粉丝页面
         */
        PAGE_TYPE_FANS(14),

        /**
         * 车库页面
         */
        PAGE_TYPE_CARPORT(15),

        /**
         * 功能页面
         */
        PAGE_TYPE_FUNCTION(16),

        /**
         * 用户中心
         */
        PAGE_TYPE_USER_CENTER(17);

        private int mValue;

        /**
         * PageType
         *
         * @param value value
         */
        PageType(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 主播排行分类
     */
    public enum StarRankType {
        /**
         * 明星排行榜
         */
        RANK_STAR_TOP("RankStar_"),
        /**
         * 魅力榜(羽毛榜)
         */
        RANK_FEATHER_TOP("RankFeather_"),
        /**
         * 点播榜
         */
        RANK_SONG_ORDER("RankSongOrder_");

        private String mValue;

        private StarRankType(String value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public String getValue() {
            return mValue;
        }
    }

    /**
     * vip类型
     */
    public enum VipType {
        /**
         * 普通用户 *
         */
        NONE(0),

        /**
         * 试用Vip
         */
        TRIAL_VIP(-1),

        /**
         * 普通Vip *
         */
        COMMON_VIP(1),

        /**
         * 至尊Vip *
         */
        SUPER_VIP(2);

        private int mValue;

        /**
         * PageType
         *
         * @param value value
         */
        private VipType(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 话题类型 普通话题、官方话题
     */
    public enum TopicType {
        /**
         * 普通话题
         */
        NORMAL_TOPIC(0),
        /**
         * 官方话题
         */
        OFFICIAL_TOPIC(2);

        private int mValue;

        private TopicType(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }

        /**
         * getTopicType
         *
         * @param value value
         * @return TopicType
         */
        public static TopicType getTopicType(int value) {
            return OFFICIAL_TOPIC.getValue() == value ? OFFICIAL_TOPIC : NORMAL_TOPIC;
        }
    }

    /**
     * 回复的分类 评论话题、评论回复
     */
    public enum CommentCategory {
        /**
         * 评论话题
         */
        COMMENT_TOPIC(1),
        /**
         * 评论回复
         */
        COMMENT_REPLY(2);

        private int mValue;

        private CommentCategory(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }

        /**
         * getCommentCategory
         *
         * @param value value
         * @return CommentCategory
         */
        public static CommentCategory getCommentCategory(int value) {
            return COMMENT_TOPIC.getValue() == value ? COMMENT_TOPIC : COMMENT_REPLY;
        }
    }

    /**
     * 分享类型
     */
    public enum ShareType {

        /**
         * 新浪微博 *
         */
        SINA_WEIBO(1),

        /**
         * QQ好友 *
         */
        QQ(2),

        /**
         * QQ微博 *
         */
        QQ_WEIBO(3),

        /**
         * QQ空间 *
         */
        QQ_ZONE(4),

        /**
         * 微信 *
         */
        WEIXIN(5),

        /**
         * 朋友圈 *
         */
        WEIXIN_FC(6);


        private int mValue;

        /**
         * PageType
         *
         * @param value value
         */
        private ShareType(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 数据加载状态的enum
     */
    public enum HintState {
        /**
         * 1、正在加载状态；
         */
        LOADING(1),
        /**
         * 2、WIFI未连接，网络状况不好；
         */
        WIFI_OFF(2),
        /**
         * 3、获取服务器端数据失败或者主播数据载入失败；
         */
        FAILED(3),
        /**
         * 4、没有获取得到相关数据；
         */
        NO_DATA(4),
        /**
         * 5、用户未登录，访问限制；
         */
        ACCESS_RESTRICT(5);

        private int mValue;

        /**
         * @param value value
         */
        private HintState(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 主题的类型
     */
    public static enum Theme {
        /** App默认主题 **/
        NORMAL(R.style.Theme_Standard, R.style.Theme_Standard_SlideBack, R.style.Theme_Standard_Window_Overlay),
        /** 黑色主题 **/
        BLACK(R.style.Theme_Black, R.style.Theme_Black_SlideBack, R.style.Theme_Black_Window_Overlay),
        /** 紫色主题 **/
        PURPLE(R.style.Theme_Purple, R.style.Theme_Purple_SlideBack, R.style.Theme_Purple_Window_Overlay);

        private final int[] mResIDArray = new int[3];

        private Theme(int baseResId, int slideResId, int winResId) {
            mResIDArray[0] = baseResId;
            mResIDArray[1] = slideResId;
            mResIDArray[2] = winResId;
        }

        /**
         * @param themeResId themeResId
         * @return index
         */
        public int indexOf(int themeResId) {
            for (int i = 0; i < mResIDArray.length; i++) {
                if (mResIDArray[i] == themeResId) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * getResIDArray
         * @return getResIDArray
         */
        public int[] getResIDArray() {
            return mResIDArray;
        }
    }

    /**
     * 锤子的类型
     */
    public static enum HammerType {
        /**
         * 木锤子 *
         */
        WOOD(100) {
            @Override
            public List<SlotRewardItem> getCachedRewardList() {
                return (List<SlotRewardItem>) CacheUtils.getObjectCache().get(CacheObjectKey.WOOD_REWARD_LIST);
            }

            @Override
            public List<SlotRewardItem> getNoPicRewardList() {
                final int t = 10;
                final int f = 5;
                final int w = 10000;
                List<SlotRewardItem> rewardList = new ArrayList<SlotRewardItem>();
                rewardList.add(new SlotRewardItem(1, "MeiGui1", "1朵玫瑰",  "玫瑰", ""));
                rewardList.add(new SlotRewardItem(t, "XiGua10", "10个西瓜",  "西瓜", ""));
                rewardList.add(new SlotRewardItem(f, "DanGao5", "5个蛋糕",  "蛋糕", ""));
                rewardList.add(new SlotRewardItem(1, "HaiYang1", "1个海洋之心",  "海洋之心", ""));
                rewardList.add(new SlotRewardItem(1, "WuGui30Day", "乌龟座驾30天",  "乌龟", ""));
                rewardList.add(new SlotRewardItem(w, "COIN10000", "10000个金币",  "金币", "local"));
                return rewardList;
            }
        },
        /**
         * 铁锤子 *
         */
        IRON(250) {
            @Override
            public List<SlotRewardItem> getCachedRewardList() {
                return (List<SlotRewardItem>) CacheUtils.getObjectCache().get(CacheObjectKey.IRON_REWARD_LIST);
            }

            @Override
            public List<SlotRewardItem> getNoPicRewardList() {
                final int f = 50;
                final int h = 100;
                List<SlotRewardItem> rewardList = new ArrayList<SlotRewardItem>();
                rewardList.add(new SlotRewardItem(1, "ZhangSheng1", "1个掌声",  "掌声", ""));
                rewardList.add(new SlotRewardItem(f, "BingQiLin50", "50个冰淇淋",  "冰激凌", ""));
                rewardList.add(new SlotRewardItem(h, "MeiGui100", "100朵玫瑰",  "玫瑰", ""));
                rewardList.add(new SlotRewardItem(1, "YanHua1", "1个浪漫烟花",  "浪漫烟花", ""));
                rewardList.add(new SlotRewardItem(1, "ChengBao1", "1个爱的城堡",  "爱的城堡", ""));
                rewardList.add(new SlotRewardItem(1, "Ferrari30Day", "法拉利座驾30天",  "法拉利", ""));
                return rewardList;
            }
        },
        /**
         * 金锤子 *
         */
        GOLD(500) {
            @Override
            public List<SlotRewardItem> getCachedRewardList() {
                return (List<SlotRewardItem>) CacheUtils.getObjectCache().get(CacheObjectKey.GOLD_REWARD_LIST);
            }

            @Override
            public List<SlotRewardItem> getNoPicRewardList() {
                final int f = 50;
                final int h = 100;
                List<SlotRewardItem> rewardList = new ArrayList<SlotRewardItem>();
                rewardList.add(new SlotRewardItem(1, "MaiKeFeng1", "1个麦克风",  "麦克风", ""));
                rewardList.add(new SlotRewardItem(f, "NaiZui50", "50个奶嘴",  "奶嘴", ""));
                rewardList.add(new SlotRewardItem(h, "KouHong100", "100个口红",  "口红", ""));
                rewardList.add(new SlotRewardItem(1, "Kiss1", "1个强吻",  "强吻", ""));
                rewardList.add(new SlotRewardItem(1, "HuanQiu1", "1个环球旅行",  "环球旅行", ""));
                rewardList.add(new SlotRewardItem(1, "HuoShan1", "1个爱的火山",  "爱的火山", ""));
                return rewardList;
            }
        };

        private final int mCost;

        private HammerType(int cost) {
            this.mCost = cost;
        }

        /**
         * @return Cost
         */
        public int getCost() {
            return mCost;
        }

        /**
         * 获取缓存中的奖池列表
         * @return 缓存中的奖池列表
         */
        public abstract List<SlotRewardItem> getCachedRewardList();

        /**
         * 获取没有图片url的奖池列表
         * @return 没有图片url的奖池列表
         */
        public abstract List<SlotRewardItem> getNoPicRewardList();

        /**
         * 获取奖池map
         * @return 奖池map
         */
        public static Map<String, String> getRewardMap() {
            Map<String, String> rewardMap = new HashMap<String, String>();
            rewardMap.put("MeiGui1", "1朵玫瑰");
            rewardMap.put("XiGua10", "10个西瓜");
            rewardMap.put("DanGao5", "5个蛋糕");
            rewardMap.put("HaiYang1", "1个海洋之心");
            rewardMap.put("WuGui30Day", "乌龟座驾30天");
            rewardMap.put("COIN10000", "10000个金币");
            rewardMap.put("ZhangSheng1", "1个掌声");
            rewardMap.put("BingQiLin50", "50个冰淇淋");
            rewardMap.put("MeiGui100", "100朵玫瑰");
            rewardMap.put("YanHua1", "1个浪漫烟花");
            rewardMap.put("ChengBao1", "1个爱的城堡");
            rewardMap.put("Ferrari30Day", "法拉利座驾30天");
            rewardMap.put("MaiKeFeng1", "1个麦克风");
            rewardMap.put("NaiZui50", "50个奶嘴");
            rewardMap.put("KouHong100", "100个口红");
            rewardMap.put("Kiss1", "1个强吻");
            rewardMap.put("HuanQiu1", "1个环球旅行");
            rewardMap.put("HuoShan1", "1个爱的火山");
            return rewardMap;
        }
    }

    /**
     * MemerApplyStatus
     */
    public static enum MemerApplyStatus {
        /**
         * 未通过
         */
        REFUSE(1),

        /**
         * 通过
         */
        PASS(2),

        /**
         * 未处理
         */
        UNTREATED(3),

        /**
         * 解散
         */
        DISMISS(4),

        /**
         * 踢出
         */
        KICK_OUT(5),

        /**
         * 退出
         */
        QUIT(6),

        /**
         * 用户取消
         */
        CANCLE(7),

        /**
         * 无状态
         */
        NONE(-1);

        private int mValue;

        /**
         * @param value value
         */
        private MemerApplyStatus(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 用户角色枚举
     */
    public enum UserRolePriv {

        /**
         * 运营人员
         */
        OPERATER(1),

        /**
         * 主播
         */
        STAR(2),

        /**
         * 普通用户
         */
        NORMAL_USER(3),

        /**
         * 客服人员
         */
        CLIENT(4),

        /**
         * 经济人
         */
        MANAGER(5),

        /**
         * 无角色
         */
        NONE(-1);

        private int mValue;

        /**
         * @param value value
         */
        private UserRolePriv(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

}
