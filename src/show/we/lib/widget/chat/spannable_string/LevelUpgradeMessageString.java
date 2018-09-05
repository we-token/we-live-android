package show.we.lib.widget.chat.spannable_string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.widget.chat.UserTypeInfo;
import show.we.lib.widget.chat.spannable_event.ClickSpan;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class LevelUpgradeMessageString extends ChatString {

    /**
     * LevelUpgradeMessageString
     *
     * @param userTypeInfo userTypeInfo
     * @param view         view
     */
    public LevelUpgradeMessageString(UserTypeInfo userTypeInfo, TextView view) {
        super(view.getContext());
        mBuilder = processLevelUpgradeMessage(userTypeInfo, view);
    }

    private SpannableStringBuilder processLevelUpgradeMessage(UserTypeInfo userTypeInfo, TextView view) {
        String nickName = userTypeInfo.getNickName();
        Enums.VipType vipType = userTypeInfo.getVipType();
        long userLevel = userTypeInfo.getUserLevel();
        long starLevel = userTypeInfo.getStarLevel();
        long id = userTypeInfo.getId();
        boolean isVipHiding = userTypeInfo.getIsVipHiding();
        boolean isStar = userTypeInfo.getIsStar();
        String str = mContext.getString(isStar ? R.string.star_level_upgrade_notify : R.string.user_level_upgrade_notify
                , nickName, isStar ? starLevel : userLevel);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(str);

        final int starPreCount = 5;
        int start = isStar ? starPreCount : 4;
        int end = start + nickName.length();
        stringBuilder.setSpan(new ClickSpan(mContext, vipType == Enums.VipType.SUPER_VIP ? mPurpleColor : mBlueColor
                , new ChatUserInfo(id, nickName, vipType, 0, starLevel, isVipHiding)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder userType = processUserType(userLevel, 0, vipType, id, view);
        stringBuilder.insert(start, userType);
        return stringBuilder;
    }
}
