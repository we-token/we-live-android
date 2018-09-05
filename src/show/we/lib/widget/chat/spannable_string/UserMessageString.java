package show.we.lib.widget.chat.spannable_string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.model.Message;
import show.we.lib.utils.EmoticonUtils;
import show.we.lib.widget.chat.spannable_event.ClickSpan;
import show.we.lib.widget.chat.spannable_event.RechargeSpan;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class UserMessageString extends ChatString {

    /**
     * UserMessageString
     *
     * @param message   message
     * @param view      view
     * @param isPrivate isPrivate
     */
    public UserMessageString(Message.ReceiveModel message, TextView view, boolean isPrivate) {
        super(view.getContext());
        mBuilder = processUserMessage(message, view, isPrivate);
    }

    private SpannableStringBuilder processUserMessage(Message.ReceiveModel message, TextView view, boolean isPrivate) {
        SpannableStringBuilder stringBuilder;
        Message.From from = message.getFrom();
        if (from != null) {
            long fromId = from.getId();
            String fromNickName = from.getNickName();
            Enums.VipType fromVipType = from.getVipType();
            int fromType = from.getType();
            long fromLevel = message.getLevel();
            ChatUserInfo fromUser = new ChatUserInfo(fromId, fromNickName, fromVipType, fromType, fromLevel, false);
            int fromUserColor = from.getVipType() == Enums.VipType.SUPER_VIP ? mPurpleColor : mBlueColor;
            Message.To to = message.getTo();
            if (to != null) { //a对b说:..
                if (mMyId == fromId) {
                    fromNickName = mYou;
                }
                int toUserColor = to.getVipType() == Enums.VipType.SUPER_VIP ? mPurpleColor : mBlueColor;
                long toId = to.getId();
                String toNickName = message.getTo().getNickName();
                if (mMyId == to.getId()) {
                    toNickName = mYou;
                }
                Enums.VipType toVipType = to.getVipType();
                int toType = to.getType();
                long toLevel = to.getLevel();
                ChatUserInfo toUser = new ChatUserInfo(toId, toNickName, toVipType, toType, toLevel, false);
                String toSeparation = (isPrivate & message.getTo().getPrivate()) ? mPrivateToSeparation : mToSeparation;
                stringBuilder = new SpannableStringBuilder(fromNickName + toSeparation + toNickName + mSaidSeparation + "\n");

                int start = 0;
                int end = start + fromNickName.length();
                stringBuilder.setSpan(new ClickSpan(mContext, fromUserColor, fromUser), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = end;
                end = start + toSeparation.length();
                stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = end;
                end = start + toNickName.length();
                stringBuilder.setSpan(new ClickSpan(mContext, toUserColor, toUser), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                SpannableStringBuilder fromUserType = processUserType(fromLevel, fromType, fromVipType, fromId, view);
                stringBuilder.insert(0, fromUserType);

                SpannableStringBuilder toUserType = processUserType(toLevel, toType, toVipType, toId, view);
                stringBuilder.insert((fromUserType + fromNickName + toSeparation).length(), toUserType);

                SpannableStringBuilder processedBuilder = processPrivacyString(message.getContent(), view);
                stringBuilder.insert(stringBuilder.length(), processedBuilder);
                if (from.getVipType() == Enums.VipType.SUPER_VIP) {
                    end = stringBuilder.length();
                    start = end - message.getContent().length();
                    stringBuilder.setSpan(new ClickSpan(mContext, fromUserColor, fromUser), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                stringBuilder = new SpannableStringBuilder(fromNickName + mSaidSeparation + "\n" + message.getContent());

                int start = 0;
                int end = start + fromNickName.length();
                stringBuilder.setSpan(new ClickSpan(mContext, fromUserColor, fromUser), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                EmoticonUtils.loadEmoticon(mContext, view, stringBuilder, end, stringBuilder.length()
                        , mBlackColor, R.array.array_expression, false);
                if (from.getVipType() == Enums.VipType.SUPER_VIP) {
                    end = stringBuilder.length();
                    start = end - message.getContent().length();
                    stringBuilder.setSpan(new ClickSpan(mContext, fromUserColor, fromUser), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                long level = message.getLevel();
                int type = from.getType();
                Enums.VipType vipType = from.getVipType();
                long id = from.getId();
                SpannableStringBuilder fromUserType = processUserType(level, type, vipType, id, view);
                stringBuilder.insert(0, fromUserType);
            }
        } else {
            stringBuilder = new SpannableStringBuilder("");
        }
        return stringBuilder;
    }

    private SpannableStringBuilder processPrivacyString(String content, TextView view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);
        stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor), 0, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        EmoticonUtils.loadEmoticon(mContext, view, stringBuilder, 0, stringBuilder.length()
                , mBlackColor, R.array.array_expression, false);

        if (content.contains(mRecharge)) {
            int start = content.indexOf(mRecharge);
            int end = start + mRecharge.length();
            stringBuilder.setSpan(new RechargeSpan(mContext, mContext.getResources().getColor(R.color.dark_red))
                    , start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return stringBuilder;
    }
}
