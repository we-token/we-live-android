package show.we.lib.widget.abc_pull_to_refresh.hint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import show.we.lib.R;

/**
 * 无数据提示View
 * @author ll
 * @version 1.0.0
 */
public class NoDataHintView extends AbsHintView {

    private TextView mHintTextView;
    private static final int DEFAULT_HINT = R.string.default_no_data_hint;

    /**
     *
     * @param context context
     */
    public NoDataHintView(Context context) {
        super(context);
    }

    /**
     *
     * @param context context
     * @param attrs attrs
     */
    public NoDataHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        addViewInLayout(View.inflate(context, R.layout.hint_view_not_data, null), -1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        requestLayout();
        mHintTextView = (TextView) findViewById(R.id.tv_hint_view_no_data);
        mHintTextView.setText(DEFAULT_HINT);
    }

    @Override
    public void setHint(String hint) {
        mHintTextView.setText(hint);
    }
}
