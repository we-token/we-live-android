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
 *
 * @author ll
 * @version 3.9.0
 */
public class TreasureAwardString extends ChatString {

    /**
     * SendGiftString
     *
     * @param data data
     * @param view    view
     */

    public TreasureAwardString(Message.TreasureRoomModel.Data data, TextView view) {
        super(view.getContext());
        mBuilder = processTreasureAward(data, view);
    }

    private SpannableStringBuilder processTreasureAward(Message.TreasureRoomModel.Data data, TextView view) {
        SpannableStringBuilder stringBuilder = null;
        if (data != null) {
            Message.TreasureRoomModel.AwardUser awardUser = data.getCurrentAwardUser();
            String userName = mYou;
            long userId = LoginUtils.isAlreadyLogin() ? UserInfoUtils.getUserInfo().getData().getId() : 0;
            int winCoin = data.getOtherCoin();
            long level = 0;
            if (awardUser != null) {
                if (awardUser.getUserId() != userId) {
                    userName =  awardUser.getNickName();
                }
                winCoin = data.getObtainCoin();
                level = LevelUtils.getUserLevelInfo(awardUser.getFinance()).getLevel();
                userId = awardUser.getUserId();
            }
            stringBuilder = new SpannableStringBuilder(mContext.getString(R.string.treasure_award_message, userName, winCoin));
            ChatUserInfo user = new ChatUserInfo(userId, userName, Enums.VipType.NONE, 0, level, false);

            int start = 0;
            int end = start + userName.length();
            stringBuilder.setSpan(new ClickSpan(mContext, mBlueColor, user), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            start = end;
            end = stringBuilder.length();
            stringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            stringBuilder.insert(0, processUserType(level, 0, Enums.VipType.NONE, userId, view));
        }

        return stringBuilder;
    }
}
