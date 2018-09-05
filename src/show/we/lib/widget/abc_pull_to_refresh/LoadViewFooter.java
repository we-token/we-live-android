/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package show.we.lib.widget.abc_pull_to_refresh;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.widget.abc_pull_to_refresh.LoadListView.State;

/**
 * @author ll
 * @version 1.0.0
 */
public class LoadViewFooter extends LinearLayout {
    private static final int HIDE_FOOTER_DELAY = 500;
    private Context mContext;
    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;
    private String mAllDataLoadedText;

    private State mState = State.NORMAL;

    /**
     * @param context context
     */
    public LoadViewFooter(Context context) {
        super(context);
        init(context);
    }

    /**
     * @param context context
     * @param attrs   attrs
     */
    public LoadViewFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    protected void setState(State state) {
        mState = state;
    }

    protected void notifyAllDataLoaded() {
        mState = State.NORMAL;
        hide();
    }

    protected void reset() {
        mState = State.NORMAL;
        show();
        mProgressBar.setVisibility(View.VISIBLE);
        mHintView.setText("正在加载更多……");
    }

    protected State getState() {
        return mState;
    }

    protected void setBottomMargin(int height) {
        if (height < 0) {
            return;
        }
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    protected int getBottomMargin() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * hide footer when disable pull load more
     */
    protected void hide() {
        mContentView.setVisibility(View.GONE);
    }


    protected void show() {
        mContentView.setVisibility(View.VISIBLE);
    }

    protected void setLoadingText(String text) {
        if (!TextUtils.isEmpty(text)) {
            mHintView.setText(text);
        }
    }

    protected void setAllDataLoadedText(String text) {
        mAllDataLoadedText = text;
        if (!TextUtils.isEmpty(text)) {
            mHintView.setText(text);
        }
    }

    private void init(Context context) {
        mContext = context;
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_pull_list_footer, null);
        addView(mContentView);
        mContentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        mProgressBar = mContentView.findViewById(R.id.id_pull_list_footer_progressbar);
        mHintView = (TextView) mContentView.findViewById(R.id.id_pull_list_footer_hint);

        hide();
    }
}
