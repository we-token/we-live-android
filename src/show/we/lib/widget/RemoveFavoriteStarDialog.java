package show.we.lib.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;
import show.we.lib.R;
import show.we.lib.utils.PromptUtils;
import show.we.lib.utils.RequestUtils;
import show.we.lib.widget.dialog.StandardDialog;

/**
 * Created by l on 14-1-14.
 *
 * @author jun.hou
 * @version 3.4.0
 */
public class RemoveFavoriteStarDialog {

    private static boolean mIsExist;

    /**
     * show
     *
     * @param context  context
     * @param nickName nickName
     * @param id       id
     */
    public static void show(final Context context, String nickName, final long id) {
        if (mIsExist) {
            return;
        }
        StandardDialog cancelDialog = new StandardDialog(context);
        String str1 = context.getString(R.string.cancel_star_prompt);
        SpannableString string = new SpannableString(str1 + "\n" + nickName);
        string.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), str1.length(), string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        cancelDialog.setContentText(string);
        cancelDialog.setPositiveButtonText(context.getString(R.string.unfollow));
        cancelDialog.setNegativeButtonText(context.getString(R.string.maintain_follow));
        cancelDialog.setPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptUtils.showToast(R.string.unfollow, Toast.LENGTH_SHORT);
                RequestUtils.requestDelFavoriteStar(id, context);
            }
        });
        cancelDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mIsExist = false;
            }
        });
        cancelDialog.show();
        mIsExist = true;
    }
}
