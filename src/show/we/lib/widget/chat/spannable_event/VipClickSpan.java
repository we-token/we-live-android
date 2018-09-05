package show.we.lib.widget.chat.spannable_event;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;

import show.we.lib.utils.Utils;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class VipClickSpan extends ClickableSpan {

    private Class mClass;
    private Context mContext;

    /**
     * VipClickSpan
     *
     * @param context context
     */
    public VipClickSpan(Context context) {
        mContext = context;
        String fillName = "show.we.ktv.activity.UpgradeVipActivity";
        String fillNameHD = "show.we.ktv.activity_hd.ui.UpgradeVipActivity";
        mClass = Utils.getClass(fillName, fillNameHD);
    }

    @Override
    public void onClick(View widget) {
        if (mClass != null) {
            mContext.startActivity(new Intent(mContext, mClass));
        }
    }
}
