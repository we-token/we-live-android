package show.we.lib.widget.abc_pull_to_refresh.hint;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;

/**
 * @author ll
 * @version 4.0.0
 */
public class RequestHintView extends FrameLayout implements View.OnClickListener, RequestStateController {

    /**
     * 点击屏幕事件回调接口
     */
    public interface OnScreenTapedListener {
        /**
         * 点击屏幕事件回调
         */
        public void onScreenTaped();
    }

    private String mDefaultLoadingHint;
    private Enums.HintState mState;

    private View mRequestingView;
    private FrameLayout mRequestedHintLayout;
    private AbsHintView mNoDataHintView;
    private AbsHintView mWifiOffHintView;
    private AbsHintView mRequestFailHintView;
    private AbsHintView mUnloginHintView;

    private OnScreenTapedListener mOnScreenTapedListener;

    /**
     * LoadDataStateView
     * @param context context
     */
    public RequestHintView(Context context) {
        super(context);
        init(context);
    }

    /**
     * LoadDataStateView
     * @param context context
     * @param attrs   attrs
     */
    public RequestHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * LoadDataStateView
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public RequestHintView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    @Override
    public void setNoDataHint(String hint) {
        mNoDataHintView.setHint(hint);
    }

    @Override
    public void setWifiOffHint(String hint) {
        mWifiOffHintView.setHint(hint);
    }

    @Override
    public void setRequestFailedHint(String hint) {
        mRequestFailHintView.setHint(hint);
    }

    @Override
    public void setUnLoginHint(String hint) {
        mUnloginHintView.setHint(hint);
    }

    @Override
    public void setRequestingHint(String hint) {
        this.mDefaultLoadingHint = hint;
    }

    @Override
    public void setNoDataHint(int resId) {
        setNoDataHint(getContext().getString(resId));
    }

    @Override
    public void setRequestingHint(int resId) {
        setRequestingHint(getContext().getString(resId));
    }

    @Override
    public void setWifiOffHint(int resId) {
        setWifiOffHint(getContext().getString(resId));
    }

    @Override
    public void setRequestFailedHint(int resId) {
        setRequestFailedHint(getContext().getString(resId));
    }

    @Override
    public void setUnloginHint(int resId) {
        setUnLoginHint(getContext().getString(resId));
    }

    @Override
    public void replaceNoDataHintView(AbsHintView noDataHintView) {
        int index = mRequestedHintLayout.indexOfChild(mNoDataHintView);
        mRequestedHintLayout.removeView(mNoDataHintView);
        mRequestedHintLayout.addView(noDataHintView, index);
        mNoDataHintView = noDataHintView;
    }

    @Override
    public void replaceRequestFailHintView(AbsHintView requestFailHintView) {
        int index = mRequestedHintLayout.indexOfChild(mRequestFailHintView);
        mRequestedHintLayout.removeView(mRequestFailHintView);
        mRequestedHintLayout.addView(requestFailHintView, index);
        mRequestFailHintView = requestFailHintView;
    }

    @Override
    public void replaceUnLoginHintView(AbsHintView unLoginView) {
        int index = mRequestedHintLayout.indexOfChild(mUnloginHintView);
        mRequestedHintLayout.removeView(mRequestFailHintView);
        mRequestedHintLayout.addView(unLoginView, index);
        mUnloginHintView = unLoginView;
    }

    @Override
    public void replaceWifiOffHintView(AbsHintView wifiOffHintView) {
        int index = mRequestedHintLayout.indexOfChild(mWifiOffHintView);
        mRequestedHintLayout.removeView(mRequestFailHintView);
        mRequestedHintLayout.addView(wifiOffHintView, index);
        mWifiOffHintView = wifiOffHintView;
    }

    /**
     * 设置点击屏幕事件回调接口
     * @param listener listener
     */
    public void setOnScreenTapedListener(OnScreenTapedListener listener) {
        mOnScreenTapedListener = listener;
    }

    private void init(final Context context) {
        mDefaultLoadingHint = getContext().getString(R.string.default_loading_hint);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mRequestedHintLayout = (FrameLayout) View.inflate(context, R.layout.request_complete_state_view, null);
        mNoDataHintView = (AbsHintView) mRequestedHintLayout.findViewById(R.id.hint_view_no_data);
        mWifiOffHintView = (AbsHintView) mRequestedHintLayout.findViewById(R.id.hint_view_wifi_off);
        mRequestFailHintView = (AbsHintView) mRequestedHintLayout.findViewById(R.id.hint_view_request_fail);
        mUnloginHintView = (AbsHintView) mRequestedHintLayout.findViewById(R.id.hint_view_unlogin);
        addView(mRequestedHintLayout, params);
        mRequestedHintLayout.setOnClickListener(this);

        mRequestingView = View.inflate(context, R.layout.requesting_state_view, null);
        addView(mRequestingView, params);
    }

    private void updateWifiOffView() {
        mNoDataHintView.setVisibility(View.INVISIBLE);
        mWifiOffHintView.setVisibility(View.VISIBLE);
        mRequestFailHintView.setVisibility(View.INVISIBLE);
        mUnloginHintView.setVisibility(View.INVISIBLE);
        mRequestedHintLayout.setClickable(false);
    }

    private void updateRequestFailedView() {
        mNoDataHintView.setVisibility(View.INVISIBLE);
        mWifiOffHintView.setVisibility(View.INVISIBLE);
        mRequestFailHintView.setVisibility(View.VISIBLE);
        mUnloginHintView.setVisibility(View.INVISIBLE);
        mRequestedHintLayout.setClickable(true);
    }

    private void updateEmptyDataView() {
        mNoDataHintView.setVisibility(View.VISIBLE);
        mWifiOffHintView.setVisibility(View.INVISIBLE);
        mRequestFailHintView.setVisibility(View.INVISIBLE);
        mUnloginHintView.setVisibility(View.INVISIBLE);
        mRequestedHintLayout.setClickable(false);
    }

    private void updateUnLoginView() {
        mNoDataHintView.setVisibility(View.INVISIBLE);
        mWifiOffHintView.setVisibility(View.INVISIBLE);
        mRequestFailHintView.setVisibility(View.INVISIBLE);
        mUnloginHintView.setVisibility(View.VISIBLE);
        mRequestedHintLayout.setClickable(false);
    }

    @Override
    public void onClick(View v) {
        if (mState == Enums.HintState.FAILED && mOnScreenTapedListener != null) {
            mOnScreenTapedListener.onScreenTaped();
        }
    }

    @Override
    public void updateState(Enums.HintState state) {
        mState = state;
        if (state == Enums.HintState.LOADING) {
            ((TextView)mRequestingView.findViewById(R.id.requesting_txt)).setText(mDefaultLoadingHint);
            mRequestingView.setVisibility(View.VISIBLE);
        } else {
            mRequestingView.setVisibility(View.INVISIBLE);
            switch (state) {
                case WIFI_OFF:
                    updateWifiOffView();
                    break;
                case FAILED:
                    updateRequestFailedView();
                    break;
                case NO_DATA:
                    updateEmptyDataView();
                    break;
                case ACCESS_RESTRICT:
                    updateUnLoginView();
                    break;
                default:
                    break;
            }
        }
    }
}
