package show.we.lib.widget.chat.spannable_string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
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
public class SendGiftString extends ChatString {

    /**
     * SendGiftString
     *
     * @param message message
     * @param view    view
     */

    private SpannableStringBuilder mStringBuilder;

    /**
     * SendGiftString
     * @param message message
     * @param view view
     */
    public SendGiftString(Message.SendGiftModel message, TextView view) {
        super(view.getContext());
        mBuilder = processSendGift(message, view);
    }

    private SpannableStringBuilder processSendGift(Message.SendGiftModel message, TextView view) {
		Message.From fromModel = message.getData().getFrom();
		Message.To toModel = message.getData().getTo();
        long fromId = fromModel.getId();
        String fromNickName = fromModel.getNickName();
        long toId = toModel.getId();
        String toNickName = toModel.getNickName();
        String giftName = message.getData().getGift().getName();
        String count = Long.toString(message.getData().getGift().getCount());

        ChatUserInfo from = new ChatUserInfo(fromId, fromNickName, fromModel.getVipType(), fromModel.getType()
				, LevelUtils.getUserLevelInfo(fromModel.getFinance()).getLevel(), false);
        ChatUserInfo to = new ChatUserInfo(toId, toNickName, toModel.getVipType(), toModel.getType()
				, LevelUtils.getUserLevelInfo(toModel.getFinance()).getLevel(), false);

        mStringBuilder = new SpannableStringBuilder(fromNickName + mPresent + toNickName + count + mUnit + giftName);

        int start = 0;
        int end = fromNickName.length();
        mStringBuilder.setSpan(new ClickSpan(mContext, mBlueColor, from), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + mPresent.length();

        mStringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + toNickName.length();

        mStringBuilder.setSpan(new ClickSpan(mContext, mBlueColor, to), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        mStringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), end, mStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        mStringBuilder.insert(start, processUserType(to.getLevel(), to.getType(), to.getVipType(), toId, view));

        mStringBuilder.insert(0, processUserType(from.getLevel(), from.getType(), from.getVipType(), fromId, view));

        if (message.getData().getWinCoin().length > 0) {
            // 赠送礼物获得幸运大奖
            String time = String.format(mXTime, message.getData().getWinCoin()[0]);
            SpannableStringBuilder winCoinStringBuilder = new SpannableStringBuilder(mWinSeparation + time + mGrandPrice);

            start = 0;
            end = start + mWinSeparation.length();
            winCoinStringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            winCoinStringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), end, winCoinStringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mStringBuilder.insert(mStringBuilder.length(), winCoinStringBuilder);
        }

        return mStringBuilder;
    }
}
