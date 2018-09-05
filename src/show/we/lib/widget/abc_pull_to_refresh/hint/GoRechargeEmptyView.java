package show.we.lib.widget.abc_pull_to_refresh.hint;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.config.BannerIntentKey;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.model.PropertiesListResult;
import show.we.lib.utils.CacheUtils;
import show.we.lib.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * 充值记录为空提示View
 * @author ll
 * @version 1.0.0
 */
public class GoRechargeEmptyView extends AbsHintView {

    private TextView mHintTextView;

    /**
     * @param context context
     */
    public GoRechargeEmptyView(Context context) {
        super(context);
    }

    /**
     *
     * @param context context
     * @param attrs attrs
     */
    public GoRechargeEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        addView(View.inflate(context, R.layout.hint_view_recharge_list_no_data, null));
        mHintTextView = (TextView) findViewById(R.id.tv_recharge_no_data_hint);
        setHintTextLink();
        findViewById(R.id.btn_go_to_recharge).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.entryRechargeActivity(getContext());
            }
        });
    }

    @Override
    public void setHint(String hint) {
        mHintTextView.setText(hint);
    }

    private void setHintTextLink() {
        if (CacheUtils.getObjectCache().contain(CacheObjectKey.PROPERTIES_LIST)) {
            HashMap<String, String> mHashMap = (HashMap<String, String>) CacheUtils.getObjectCache().get(CacheObjectKey.PROPERTIES_LIST);
            if (mHashMap.get(PropertiesListResult.FIRST_CHARGE_TIP) != null) {
                if (mHashMap.get(PropertiesListResult.FIRST_CHARGE_TIP).contains("*")) {
                    List<String> mContentAndLink = StringUtils.splitToStringList(mHashMap.get(PropertiesListResult.FIRST_CHARGE_TIP), "*");
                    final String urlRechargeAward = mContentAndLink.get(1);
                    if (!urlRechargeAward.equals("")) {
                        mHintTextView.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String bannerStr = "show.we.ktv.activity.BannerActivity";
                                Class cls = Utils.getClass(bannerStr, bannerStr);
                                if (cls != null) {
                                    Intent intent = new Intent(getContext(), cls);
                                    intent.putExtra(BannerIntentKey.INTENT_CLICK_URL, urlRechargeAward);
                                    intent.putExtra(BannerIntentKey.INTENT_TITLE, getContext().getString(R.string.recharge_award_title));
                                    getContext().startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }
        }
    }
}
