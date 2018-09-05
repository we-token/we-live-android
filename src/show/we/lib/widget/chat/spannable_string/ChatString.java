package show.we.lib.widget.chat.spannable_string;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.AudienceResult;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.utils.AudienceUtils;
import show.we.lib.utils.LevelUtils;
import show.we.lib.widget.chat.spannable_event.VipClickSpan;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class ChatString extends CommonData {

    /**
     * 等级大于等于该值才在聊天窗口显示财富等级图标
     */
    private static final int SHOW_MIN_LEVEL_ICON = 4;

    /**
     * 等级图标宽度因子，由管理图片计算得出
     */
    private static final float LEVEL_GIF_WIDTH_FACTOR = 36.7f;
    private static final float LEVEL_GIF_HEIGHT_FACTOR = 16.7f;
    private static final float ROUND_DIFFERENCE = 0.5f;

    private static final int OPERATIONS_PERSONNEL = 1;
    private static final int CUSTOMER_SERVICE = 4;
    private static final int PROXY = 5;

    private VipClickSpan mVipClickSpan;

    /**
     * ChatString
     *
     * @param context context
     */
    public ChatString(Context context) {
        super(context);
        mVipClickSpan = new VipClickSpan(context);
    }

    protected SpannableStringBuilder processUserType(long level, int type, Enums.VipType vipType, long id, TextView view) {
        long starId = LiveCommonData.getStarId();
        AudienceResult adminInfoResult = LiveCommonData.getAdminResult();
        long spendFirstId = LiveCommonData.getSpendRankFirstId();

        String levelStr = "level ";
        String typeStr = "type ";
        String starStr = "star ";
//        String adminStr = "admin ";
        String vipStr = "vip ";
//        String spendMostStr = "spendMost ";
        SpannableStringBuilder userType = new SpannableStringBuilder(levelStr + typeStr + vipStr /*+ adminStr  + spendMostStr*/ + starStr);

        if (level >= SHOW_MIN_LEVEL_ICON) {
            userType.setSpan(new ImageSpan(mContext, LevelUtils.getUserLevelIcons((int) level), ImageSpan.ALIGN_BASELINE)
                    , 0, levelStr.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            userType.replace(0, levelStr.length(), "");
        }

        int start = userType.toString().indexOf(typeStr);

        switch (type) {
            case OPERATIONS_PERSONNEL:
                userType.setSpan(new ImageSpan(mContext, R.drawable.img_operations_personnel, ImageSpan.ALIGN_BASELINE)
                        , start, start + typeStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case CUSTOMER_SERVICE:
                userType.setSpan(new ImageSpan(mContext, R.drawable.img_customer_service, ImageSpan.ALIGN_BASELINE)
                        , start, start + typeStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case PROXY:
                userType.setSpan(new ImageSpan(mContext, R.drawable.img_proxy, ImageSpan.ALIGN_BASELINE)
                        , start, start + typeStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            default:
                userType.replace(start, start + typeStr.length(), "");
                break;
        }

//        start = userType.toString().indexOf(adminStr);
//        if (adminInfoResult != null && AudienceUtils.isAdmin(id, adminInfoResult)) {
//            userType.setSpan(new ImageSpan(mContext, R.drawable.img_manager, ImageSpan.ALIGN_BASELINE)
//                    , start, start + adminStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            userType.replace(start, start + adminStr.length(), "");
//        }

        start = userType.toString().indexOf(vipStr);
        if (vipType == Enums.VipType.NONE) {
            userType.replace(start, start + vipStr.length(), "");
        } else {
            int drawableVipId = R.drawable.img_vip_normal;
            if (vipType == Enums.VipType.TRIAL_VIP) {
                drawableVipId = R.drawable.img_trial_vip;
            } else if (vipType == Enums.VipType.SUPER_VIP) {
                drawableVipId = R.drawable.img_vip_extreme;
            }
            userType.setSpan(new ImageSpan(mContext, drawableVipId, ImageSpan.ALIGN_BASELINE), start
                    , start + vipStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (mVipClickSpan != null) {
                userType.setSpan(mVipClickSpan, start, start + vipStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

//        start = userType.toString().indexOf(spendMostStr);
//        if (spendFirstId == id) {
//            userType.setSpan(new ImageSpan(mContext, R.drawable.img_spend_most, ImageSpan.ALIGN_BASELINE)
//                    , start, start + spendMostStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        } else {
//            userType.replace(start, start + spendMostStr.length(), "");
//        }
        
        

        start = userType.toString().indexOf(starStr);
        if (id == starId) {
            userType.setSpan(new ImageSpan(mContext, R.drawable.img_star, ImageSpan.ALIGN_BASELINE)
                    , start, start + starStr.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            userType.replace(start, start + starStr.length(), "");
        }

        return userType;
    }
}
