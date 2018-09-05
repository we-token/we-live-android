package show.we.lib.widget.chat.spannable_string;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.config.Enums;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.widget.chat.spannable_event.ClickSpan;

import org.json.JSONObject;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class KickString extends ChatString {

    /**
     * EntryRoomString
     *
     * @param jsonObject jsonObject
     * @param view       view
     */
    public KickString(JSONObject jsonObject, TextView view) {
        super(view.getContext());
        mBuilder = processKick(jsonObject, view);
    }

    private SpannableStringBuilder processKick(JSONObject jsonObject, TextView view) {
        long fromId = 0;
        String fromName = "";
        long toId = 0;
        String toName = "";
        JSONObject data = jsonObject.optJSONObject("data_d");
        if (data != null) {
            fromId = data.optLong("f_id");
            fromName = data.optString("f_name");
            toId = data.optLong("xy_user_id");
            toName = data.optString("nick_name");
        }

        ChatUserInfo toUser = new ChatUserInfo(toId, toName, Enums.VipType.NONE, 0, 0, false);
        ChatUserInfo fromUser = new ChatUserInfo(fromId, fromName, Enums.VipType.NONE, 0, 0, false);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(toName + mBySeparation + fromName
                + SPACE_SEPARATION + mKick);

        int start = 0;
        int end = start + toName.length();
        stringBuilder.setSpan(new ClickSpan(mContext, mBlueColor, toUser), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + mBySeparation.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + fromName.length();
        stringBuilder.setSpan(new ClickSpan(mContext, mBlueColor, fromUser), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = stringBuilder.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder toUserType = processUserType(0, 0, Enums.VipType.NONE, toId, view);
        stringBuilder.insert(0, toUserType);

        SpannableStringBuilder fromUserType = processUserType(0, 0, Enums.VipType.NONE, fromId, view);
        stringBuilder.insert((toUserType + toName + mBySeparation).length(), fromUserType);

        return stringBuilder;
    }
}
