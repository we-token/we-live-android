package show.we.lib.widget.chat.spannable_string;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.model.Message;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.UserInfoUtils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class SendFeatherString extends ChatString {

    /**
     * SendGiftString
     *
     * @param message message
     * @param view    view
     */

    public SendFeatherString(Message.SendFeatherModel message, TextView view) {
        super(view.getContext());
        mBuilder = processSendFeather(message, view);
    }

    private SpannableStringBuilder processSendFeather(Message.SendFeatherModel message, TextView view) {
        String userName = message.getData().getNickName();
        long id = message.getData().getId();
        if (LoginUtils.isAlreadyLogin() && UserInfoUtils.getUserInfo().getData().getId() == id) {
            userName = mYou;
        }
        String present = mPresent;
        String starName = LiveCommonData.getStarName();
        String feather = mOneFeather;
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(userName + present + starName + feather);

        int start = 0;
        int end = userName.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end += present.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end += starName.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end += feather.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
    }
}
