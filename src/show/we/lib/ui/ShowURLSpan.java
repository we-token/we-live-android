package show.we.lib.ui;

import android.content.Context;
import android.content.Intent;
import android.text.style.URLSpan;
import android.view.View;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.config.BannerIntentKey;
import show.we.lib.utils.Utils;

/**
 * 自定义URLSpan，替换Html中的超链接处理，防止跳转到浏览器
 * @author ll
 * @version 1.0.0
 */
public class ShowURLSpan extends URLSpan {

    private Context mContext;
    private String mContent;

    /**
     *
     * @param context context
     * @param url url
     * @param content content
     */
    public ShowURLSpan(Context context, String url, String content) {
        super(url);
        mContext = context;
        mContent = content;
    }

    @Override
    public void onClick(View widget) {
        if (!StringUtils.isEmpty(getURL())) {
            String bannerStr = "show.we.ktv.activity.BannerActivity";
            Class cls = Utils.getClass(bannerStr, bannerStr);
            if (cls != null) {
                Intent intent = new Intent(mContext, cls);
                intent.putExtra(BannerIntentKey.INTENT_CLICK_URL, getURL());
                intent.putExtra(BannerIntentKey.INTENT_TITLE, StringUtils.isEmpty(mContent) ? mContext.getString(R.string.app_name) : mContent);
                mContext.startActivity(intent);
            }
        }
    }
}
