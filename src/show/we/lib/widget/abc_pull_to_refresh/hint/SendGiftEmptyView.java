package show.we.lib.widget.abc_pull_to_refresh.hint;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import show.we.lib.R;

/**
 * 送礼记录为空提示View
 * @author ll
 * @version 1.0.0
 */
public class SendGiftEmptyView extends GoRechargeEmptyView {

    /**
     * @param context context
     */
    public SendGiftEmptyView(Context context) {
        super(context);
    }

    /**
     * @param context context
     * @param attrs attrs
     */
    public SendGiftEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
        TextView hintTextView = (TextView) findViewById(R.id.tv_recharge_no_data_hint);
        hintTextView.setText(R.string.send_gift_log_empty);
    }
}
