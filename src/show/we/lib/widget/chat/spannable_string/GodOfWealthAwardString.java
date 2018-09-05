package show.we.lib.widget.chat.spannable_string;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import show.we.lib.R;
import show.we.lib.model.GodOfWealth;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.UserInfoUtils;

/**
 * Created by CG on 14-1-17.
 *
 * @author ll
 * @version 3.4.0
 */
public class GodOfWealthAwardString extends CommonData {

    /**
     * GodOfWealthAwardString
     *
     * @param context   context
     * @param awardUser awardUser
     */
    public GodOfWealthAwardString(Context context, GodOfWealth.AwardUser awardUser) {
        super(context);
        mBuilder = processAwardUserInfo(awardUser);
    }

    private SpannableStringBuilder processAwardUserInfo(GodOfWealth.AwardUser awardUser) {
        if (awardUser == null) {
            return new SpannableStringBuilder();
        }
        String name = awardUser.getUser().getNickName();
        if (LoginUtils.isAlreadyLogin() && awardUser.getUser().getId() == UserInfoUtils.getUserInfo().getData().getId()) {
            name = mContext.getString(R.string.you);
        }
        String fromName = awardUser.getCurrentUser();
        String msg1 = mContext.getString(R.string.god_of_wealth1);
        String msg2 = mContext.getString(R.string.god_of_wealth2, awardUser.getObtainCoin());
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(name + mBySeparation + fromName
                + msg1 + msg2);
        stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), 0, name.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int start = name.length();
        int end = start + mBySeparation.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        start = end;
        end = start + fromName.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        start = end;
        end = start + msg1.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        start = end;
        end = stringBuilder.length() - 3;
        stringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        start = end;
        end = stringBuilder.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mYellowColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }
}
