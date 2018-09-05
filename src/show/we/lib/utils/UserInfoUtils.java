package show.we.lib.utils;

import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Enums;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.model.Finance;
import show.we.lib.model.UserInfoResult;

/**
 * 处理的业务过多，所以抽离
 * Created by CG on 13-10-24.
 *
 * @author ll
 * @version 3.0.0
 */
public class UserInfoUtils {

    /**
     * @return 返回 ACCESS_TOKEN
     */
    public static String getAccessToken() {
        return CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN) ? (String) CacheUtils.getObjectCache()
                .get(CacheObjectKey.ACCESS_TOKEN) : null;
    }

    /**
     * 获取用户vip类型
     *
     * @return Enums.VIP_TYPE vip类型
     */
    public static Enums.VipType getVipType() {
        return getUserInfo() != null ? getUserInfo().getData().getVipType() : Enums.VipType.NONE;
    }

    /**
     * getUserInfo
     *
     * @return UserInfoResult
     */
    public static UserInfoResult getUserInfo() {
        return LoginUtils.isAlreadyLogin() ? ((UserInfoResult) CacheUtils.getObjectCache().get(CacheObjectKey.USER_INFO_KEY)) : null;
    }

    /**
     * saveUserInfo
     */
    public static void saveUserInfo() {
        CacheUtils.getObjectCache().save(CacheObjectKey.USER_INFO_KEY);
    }

    /**
     * 检查用户余额是否足够
     *
     * @param price 消费金额
     * @return true钱够
     */
    public static Boolean hasEnoughMoney(Long price) {
        if (LoginUtils.isAlreadyLogin()) {
            Finance finance = getUserInfo().getData().getFinance();
            long coinCount = finance != null ? finance.getCoinCount() : 0;
            if (coinCount > price) {
                return true;
            }
        }
        return false;
    }

    /**
     * checkUserUpgrade
     *
     * @param oldCoin oldCoin
     * @param newCoin newCoin
     */
    public static void checkUserUpgrade(long oldCoin, long newCoin) {
        long oldLevel = LevelUtils.getUserLevelInfo(oldCoin).getLevel();
        long newLevel = LevelUtils.getUserLevelInfo(newCoin).getLevel();
        if (newLevel > oldLevel) {
        	 DataChangeNotification.getInstance().notifyDataChanged(IssueKey.USER_UPGRADE); 
        }
    }
}
