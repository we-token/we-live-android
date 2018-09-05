package show.we.lib.widget.abc_pull_to_refresh.hint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import show.we.lib.R;

/**
 * @author ll
 * @version 1.0.0
 */
public class WifiOffHintView extends AbsHintView {

    private static final int DEFAULT_HINT = R.string.default_wifi_off_hint;
    private TextView mHintTextView;

    /**
     * @param context context
     */
    public WifiOffHintView(Context context) {
        super(context);
    }

    /**
     * @param context context
     * @param attrs attrs
     */
    public WifiOffHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        addView(View.inflate(context, R.layout.hint_view_wifi_off, null));
        mHintTextView = (TextView) findViewById(R.id.tv_hint_view_wifi_off);
        mHintTextView.setText(DEFAULT_HINT);
    }

    @Override
    public void setHint(String hint) {
        mHintTextView.setText(hint);
    }
}
