package show.we.lib.widget.chat.spannable_string;

import android.text.SpannableStringBuilder;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.model.Message;
import show.we.lib.utils.EmoticonUtils;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class SystemNoticeString extends CommonData {

    /**
     * SystemNoticeString
     *
     * @param message message
     * @param view    view
     */
    public SystemNoticeString(Message.SystemNoticeModel message, TextView view) {
        super(view.getContext());
        mBuilder = processSystemNotice(message, view);
    }

    private SpannableStringBuilder processSystemNotice(Message.SystemNoticeModel message, TextView view) {
        String sysMessage = "";
        if (message != null) {
            sysMessage = Utils.html2String(message.getData().getMessage());
        }

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(mContext.getString(R.string.sys_notice)
                + sysMessage);

        EmoticonUtils.loadEmoticon(mContext, view, stringBuilder, 0, stringBuilder.length()
                , mBlackColor, R.array.array_expression, false);

        return stringBuilder;
    }
}
