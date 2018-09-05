package show.we.lib.widget.chat.spannable_string;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.sdk.util.StringUtils;
import show.we.lib.config.Enums;
import show.we.lib.model.SlotWinResult;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class SlotWinString extends ChatString {

    /**
     * PuzzleWinString
     * @param data data
     * @param view view
     */
    public SlotWinString(SlotWinResult.Data data, TextView view) {
        super(view.getContext());
        mBuilder = processSlotWin(data, view);
    }

    private SpannableStringBuilder processSlotWin(SlotWinResult.Data data, TextView view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("");
        if (data.getUser() != null) {
            String name = data.getUser().getNickName();
            String reward = Enums.HammerType.getRewardMap().get(data.getRewardKey());
            if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(reward)) {
                stringBuilder.append(name + mSlotWin + reward);
                int start = 0;
                int end = name.length();
                stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = end + mSlotWin.length();
                stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), end, start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                end = start + reward.length();
                stringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return stringBuilder;
    }
}
