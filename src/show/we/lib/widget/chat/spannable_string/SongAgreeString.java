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
public class SongAgreeString extends CommonData {

    /**
     * SongAgreeString
     *
     * @param context    context
     * @param message message
     */
    public SongAgreeString(Context context, Message.SongAgreeModel message) {
        super(context);
        mBuilder = processSongAgree(message);
    }

    private SpannableStringBuilder processSongAgree(Message.SongAgreeModel message) {
        String nickName = "";
        String songName = "";
        if (message != null) {
            Message.SongAgreeModel.Data data = message.getData();
            nickName = data.getNickName();
            songName = Utils.html2String(data.getSongName());
        }

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(nickName + mOrderedSong + "<<" + songName + ">>" + mStarAgreed);

        int start = 0;
        int end = nickName.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mBlackColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        start = end;
        end = stringBuilder.length();
        stringBuilder.setSpan(new ForegroundColorSpan(mGrayColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return stringBuilder;
    }
}
