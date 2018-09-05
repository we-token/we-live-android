package show.we.lib.ui;

import show.we.lib.config.Enums;
import show.we.lib.model.AudienceResult;
import show.we.lib.utils.LevelUtils;

import java.util.Comparator;

/**
 * Created by Administrator on 13-6-28.
 *
 * @author ll
 * @version 2.0.0
 */
public class AudienceCompare implements Comparator<AudienceResult.User> {

    @Override
    public int compare(AudienceResult.User lhs, AudienceResult.User rhs) {
        int compare = (int) (rhs.getCoinSpend() - lhs.getCoinSpend());
        if (compare == 0) {
            if (rhs.getVipType() == Enums.VipType.SUPER_VIP || lhs.getVipType() == Enums.VipType.SUPER_VIP) {
                return rhs.getVipType().ordinal() - lhs.getVipType().ordinal();
            }
            compare = (int) (LevelUtils.getUserLevelInfo(rhs.getFinance().getCoinSpendTotal()).getLevel()
                    - LevelUtils.getUserLevelInfo(lhs.getFinance().getCoinSpendTotal()).getLevel());
            if (rhs.getVipType() == Enums.VipType.COMMON_VIP || rhs.getVipType() == Enums.VipType.TRIAL_VIP) {
                if (compare >= 0) {
                    return 1;
                } else if (LevelUtils.getUserLevelInfo(lhs.getFinance().getCoinSpendTotal()).getLevel()
                        > LevelUtils.WEALTH_RANK_USER_LEVEL_TEN) {
                    return -1;
                } else {
                    return 1;
                }
            }
            if (lhs.getVipType() == Enums.VipType.COMMON_VIP || lhs.getVipType() == Enums.VipType.TRIAL_VIP) {
                if (compare <= 0) {
                    return -1;
                } else if (LevelUtils.getUserLevelInfo(rhs.getFinance().getCoinSpendTotal()).getLevel()
                        > LevelUtils.WEALTH_RANK_USER_LEVEL_TEN) {
                    return 1;
                } else {
                    return -1;
                }
            }

            if (compare == 0) {
                compare = (int) (LevelUtils.getUserLevelInfo(rhs.getFinance().getCoinSpendTotal()).getLevel()
                        - LevelUtils.getUserLevelInfo(lhs.getFinance().getCoinSpendTotal()).getLevel());
            }
        }
        return compare;
    }
}
