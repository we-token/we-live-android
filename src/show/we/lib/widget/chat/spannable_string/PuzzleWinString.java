package show.we.lib.widget.chat.spannable_string;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.model.Message;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class PuzzleWinString extends ChatString {

    /**
     * PuzzleWinString
     *
     * @param message message
     * @param view       view
     */
    public PuzzleWinString(Message.PuzzleWinModel message, TextView view) {
        super(view.getContext());
        mBuilder = processPuzzleWin(message, view);
    }

    private SpannableStringBuilder processPuzzleWin(Message.PuzzleWinModel message, TextView view) {
        long userId = 0;
        int type = 0;
        int vip = 0;
        long level = 0;
        String name = "";
        String spend = mContext.getString(R.string.spend);
        String time = "";
        String second = mContext.getString(R.string.second);
        String step = "";
        String winMessage = mContext.getString(R.string.jigsaw_win_message);
        if (message != null) {
            Message.PuzzleWinModel.Data data = message.getData();
            userId = data.getUserId();
            type = data.getPriv();
            vip = data.getVip();
            level = data.getLevel();
            name = Utils.html2String(data.getUserNickName());
            time = data.getCost();
            step = data.getStep();
        }

        Enums.VipType vipType = Enums.VipType.NONE;
        for (Enums.VipType t : Enums.VipType.values()) {
            if (t.getValue() == vip) {
                vipType = t;
            }
        }

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(name + spend + time + second + step + winMessage);
        int start = 0;
        int end = name.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end + spend.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), end, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        end = start + time.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end + second.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), end, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        end = start + step.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), end, stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableStringBuilder userType = processUserType(level, type, vipType, userId, view);
        stringBuilder.insert(0, userType);

        return stringBuilder;
    }
}
