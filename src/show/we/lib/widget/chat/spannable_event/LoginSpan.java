package show.we.lib.widget.chat.spannable_event;

import android.content.Context;
import android.view.View;

import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class LoginSpan extends ClickSpan {

    /**
     * RechargeSpan
     *
     * @param context context
     * @param color   color
     */
    public LoginSpan(Context context, int color) {
        super(context, color);
    }

    @Override
    public void onClick(View widget) {
        if (!LoginUtils.isAlreadyLogin()) {
			Utils.entryLoginActivity(mContext);
        }
    }
}
