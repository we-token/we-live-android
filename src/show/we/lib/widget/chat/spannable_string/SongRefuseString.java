package show.we.lib.widget.chat.spannable_string;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import show.we.lib.model.Message;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class SongRefuseString extends CommonData {

    /**
     * SongRefuseString
     *
     * @param context    context
     * @param message message
     */
    public SongRefuseString(Context context, Message.SongRefuseModel message) {
        super(context);
        mBuilder = processSongRefuse(message);
    }

    private SpannableStringBuilder processSongRefuse(Message.SongRefuseModel message) {
        String songName = "";
        if (message != null) {
            songName = Utils.html2String(message.getData().getSongName());
        }

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(mStar + mStarRefused + songName);

        int start = 0;
        int end = mStar.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mBlueColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = start + mStarRefused.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = stringBuilder.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
    }
}
