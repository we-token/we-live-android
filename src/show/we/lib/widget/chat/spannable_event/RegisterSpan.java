package show.we.lib.widget.chat.spannable_event;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class RegisterSpan extends ClickSpan {

    private Class mClass;

    /**
     * RegisterSpan
     *
     * @param context context
     * @param color   color
     */
    public RegisterSpan(Context context, int color) {
        super(context, color);
        String fillName = "show.we.ktv.activity.RegisterActivity";
        String fillNameHD = "com.hongdou.activity_hd.ui.RegisterActivity";
        mClass = Utils.getClass(fillName, fillNameHD);
    }

    @Override
    public void onClick(View widget) {
        if (!LoginUtils.isAlreadyLogin() && mClass != null) {
            mContext.startActivity(new Intent(mContext, mClass));
        }
    }
}
