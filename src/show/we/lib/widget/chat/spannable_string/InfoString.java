package show.we.lib.widget.chat.spannable_string;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.utils.EmoticonUtils;
import show.we.lib.widget.chat.spannable_event.LoginSpan;
import show.we.lib.widget.chat.spannable_event.RechargeSpan;
import show.we.lib.widget.chat.spannable_event.RegisterSpan;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class InfoString extends CommonData {

    /**
     * InfoString
     *
     * @param content content
     * @param view    view
     */
    public InfoString(String content, TextView view) {
        super(view.getContext());
        mBuilder = processString(content, view);
    }

    private SpannableStringBuilder processString(String content, TextView view) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(content);
		if (content.startsWith(mktv)) {

			stringBuilder.setSpan(new ForegroundColorSpan(mRedColor), 0, mktv.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			if (content.contains(mRegister) && content.contains(mLogin)) {
				int start = content.indexOf(mRegister);
				stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor), mktv.length(), start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //设置文字为系统提示颜色

				int end = start + mRegister.length();
				stringBuilder.setSpan(new RegisterSpan(mContext, mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				start = content.indexOf(mLogin);
				stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor), end, start - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				end = start + mLogin.length();
				stringBuilder.setSpan(new LoginSpan(mContext, mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor), end, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (content.contains(mRecharge)) {
				int start = content.indexOf(mRecharge);
				stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor),  mktv.length(), start, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

				int end = start + mRecharge.length();
				stringBuilder.setSpan(new RechargeSpan(mContext, mBlueColor), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

				stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor), end, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else if (content.contains(mktvNetAddress)) {
                int start = content.indexOf(mktvNetAddress);
                int end = content.length();
                stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
		} else {
			EmoticonUtils.loadEmoticon(mContext, view, stringBuilder, 0, stringBuilder.length()
					, mGrayColor, R.array.array_expression, false);
		}
        return stringBuilder;
    }
}
