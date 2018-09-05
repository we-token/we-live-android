package show.we.lib.widget.chat.spannable_event;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import show.we.lib.R;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.PromptUtils;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class RechargeSpan extends ClickSpan {

    /**
     * RechargeSpan
     *
     * @param context context
     * @param color   color
     */
    public RechargeSpan(Context context, int color) {
        super(context, color);
    }

    @Override
    public void onClick(View widget) {
        if (!LoginUtils.isAlreadyLogin()) {
            PromptUtils.showToast(R.string.please_login, Toast.LENGTH_SHORT);
            Utils.entryLoginActivity(mContext);
        } else {
			Utils.entryRechargeActivity(mContext);
        }
    }
}
