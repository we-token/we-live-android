package show.we.lib.widget.chat.spannable_string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.model.Message;
import show.we.lib.utils.LevelUtils;
import show.we.lib.widget.chat.spannable_event.ClickSpan;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class SendFortuneString extends ChatString {

    /**
     * SendFortuneString
     * @param message message
     * @param view    view
     */
    private SpannableStringBuilder mStringBuilder;

    /**
     * SendFortuneString
     * @param message message
     * @param view view
     */
    public SendFortuneString(Message.FortuneModel message, TextView view) {
        super(view.getContext());
        mBuilder = processSendGift(message, view);
    }

    private SpannableStringBuilder processSendGift(Message.FortuneModel message, TextView view) {
		Message.From fromModel = message.getData().getFrom();
        long fromId = fromModel.getId();
        String fromNickName = fromModel.getNickName();
        ChatUserInfo from = new ChatUserInfo(fromId, fromNickName, fromModel.getVipType(), fromModel.getType()
				, LevelUtils.getUserLevelInfo(fromModel.getFinance()).getLevel(), false);

        String inviteGodOfWealth = mContext.getString(R.string.invite_god_of_wealth, message.getData().getCount());
        mStringBuilder = new SpannableStringBuilder(fromNickName + mFor + mAllUsers + inviteGodOfWealth);

        int start = 0;
        int end = fromNickName.length();
        mStringBuilder.setSpan(new ClickSpan(mContext, mBlueColor, from), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + mFor.length();

        mStringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + mAllUsers.length();

        mStringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), end, mStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mStringBuilder.insert(0, processUserType(from.getLevel(), from.getType(), from.getVipType(), fromId, view));

        return mStringBuilder;
    }
}
