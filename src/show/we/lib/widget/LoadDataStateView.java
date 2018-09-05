package show.we.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;

/**
 * @author ll
 * @version 4.0.0
 *          Date: 14-3-12
 *          Time: 下午2:02
 */
public class LoadDataStateView extends FrameLayout implements View.OnClickListener {

    /**
     * 数据加载状态的enum
     */
    public enum LoadDataState {
        /**
         * 1、正在加载状态；
         */
        DATA_LOADING(1),
        /**
         * 2、WIFI未连接，网络状况不好；
         */
        WIFI_NEGATIVE(2),
        /**
         * 3、获取服务器端数据失败或者主播数据载入失败；
         */
        REQUEST_FAILED(3),
        /**
         * 4、没有获取得到相关数据；
         */
        EMPTY_DATA(4),
        /**
         * 5、用户未登录，访问限制；
         */
        ACCESS_RESTRICT(5);

        private int mValue;

        /**
         * PageType
         *
         * @param value value
         */
        private LoadDataState(int value) {
            mValue = value;
        }

        /**
         * getValue
         *
         * @return mValue
         */
        public int getValue() {
            return mValue;
        }
    }

    /**
     * 触摸屏幕的回调接口
     */
    public interface OnTapScreenListener {
        /**
         * 触摸屏幕回调
         */
        public void onTap();
    }

    private LoadDataState mLoadDataState;
    private OnTapScreenListener mListener;

    /**
     * LoadDataStateView
     *
     * @param context context
     */
    public LoadDataStateView(Context context) {
        super(context);
        initViews();
    }

    /**
     * LoadDataStateView
     *
     * @param context context
     * @param attrs   attrs
     */
    public LoadDataStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    /**
     * LoadDataStateView
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public LoadDataStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        View stateView = View.inflate(getContext(), R.layout.layout_load_data_state_view, null);
        addView(stateView, new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        stateView.setOnClickListener(this);
    }

    /**
     * 设置数据加载状态变更
     * @param state state 变更状态
     * @param promptStr promptStr 状态提示语
     */
    public void setOnStateChanged(LoadDataState state, String promptStr) {
        mLoadDataState = state;
        initLoadDataStateView(promptStr);
        switch (mLoadDataState) {
            case DATA_LOADING:
                setDataLoadingStateView();
                break;
            case WIFI_NEGATIVE:
                setWifiNegativeStateView();
                break;
            case REQUEST_FAILED:
                setRequestFailedStateView();
                break;
            case EMPTY_DATA:
                setEmptyDataStateView();
                break;
            case ACCESS_RESTRICT:
                setLoginRestrictStateView();
                break;
            default:
                break;
        }
    }

    /**
     * 设置触摸屏幕的回调监听
     * @param listener listener
     */
    public void setOnTapListener(OnTapScreenListener listener) {
        mListener = listener;
    }

    private void initLoadDataStateView(String promptStr) {
        setVisibility(View.VISIBLE);
        findViewById(R.id.id_state_img).clearAnimation();
        if (!StringUtils.isEmpty(promptStr)) {
            ((TextView) findViewById(R.id.id_state_text)).setText(promptStr);
        }
    }

    private void setDataLoadingStateView() {
        ((ImageView) findViewById(R.id.id_state_img)).setImageResource(R.drawable.gif_login_loading);
        findViewById(R.id.id_state_img).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.unlimited_anti_rotate));
        findViewById(R.id.id_state_btn).setVisibility(View.INVISIBLE);
    }

    private void setWifiNegativeStateView() {
        ((ImageView) findViewById(R.id.id_state_img)).setImageResource(R.drawable.img_wifi_negative);
        findViewById(R.id.id_state_btn).setVisibility(View.INVISIBLE);
    }

    private void setRequestFailedStateView() {
        ((ImageView) findViewById(R.id.id_state_img)).setImageResource(R.drawable.img_request_failed);
        findViewById(R.id.id_state_btn).setVisibility(View.INVISIBLE);
    }

    private void setEmptyDataStateView() {
        setRequestFailedStateView();
    }

    private void setLoginRestrictStateView() {
        ((ImageView) findViewById(R.id.id_state_img)).setImageResource(R.drawable.img_request_failed);
        findViewById(R.id.id_state_btn).setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (mLoadDataState == LoadDataState.REQUEST_FAILED && mListener != null) {
            mListener.onTap();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        switch(visibility) {
            case View.VISIBLE:
                break;
            default:
                findViewById(R.id.id_state_img).clearAnimation();
                break;
        }
        super.setVisibility(visibility);
    }
}
