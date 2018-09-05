package show.we.lib.widget.chat.spannable_string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.model.Message;
import show.we.lib.utils.LevelUtils;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.UserInfoUtils;
import show.we.lib.widget.chat.spannable_event.ClickSpan;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class TreasureNotifyString extends ChatString {

    /**
     * SendGiftString
     *
     * @param message message
     * @param view    view
     */

    public TreasureNotifyString(Message.TreasureNotifyModel message, TextView view) {
        super(view.getContext());
        mBuilder = processSendFeather(message, view);
    }

    private SpannableStringBuilder processSendFeather(Message.TreasureNotifyModel message, TextView view) {
        SpannableStringBuilder stringBuilder = null;
        if (message != null) {
            Message.TreasureNotifyModel.Data data = message.getData();
            Message.From from = data.getFrom();
            String userName = from.getNickName();
            long userId = from.getId();
            int count = data.getCount();
            if (LoginUtils.isAlreadyLogin() && UserInfoUtils.getUserInfo().getData().getId() == userId) {
                userName = mYou;
            }
            String treasureUserName = mContext.getString(R.string.treasure_notify_user, userName);
            String treasureCount = mContext.getString(R.string.treasure_notify_count, count);
            String treasureNotice = mContext.getString(R.string.treasure_notify_notice);
            stringBuilder = new SpannableStringBuilder(treasureUserName + treasureCount + treasureNotice);

            Enums.VipType vipType = from.getVipType();
            int userColor = from.getVipType() == Enums.VipType.SUPER_VIP ? mPurpleColor : mBlueColor;
            int userType = from.getType();
            long level = LevelUtils.getUserLevelInfo(from.getFinance()).getLevel();
            ChatUserInfo user = new ChatUserInfo(userId, userName, vipType, userType, level, false);

            int start = 0;
            int end = start + userName.length();
            stringBuilder.setSpan(new ClickSpan(mContext, userColor, user), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            start = end;
            end = start + treasureUserName.length() - userName.length();
            stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            start = end;
            end = start + treasureCount.length();
            stringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            start = end;
            end = start + treasureNotice.length();
            stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            stringBuilder.insert(0, processUserType(level, userType, vipType, userId, view));
        }

        return stringBuilder;
    }
}
