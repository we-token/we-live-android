package show.we.lib.widget.dialog;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;

/**
 * @author ll
 * @version 4.0.0
 *          Date: 14-3-11
 *          Time: 下午3:13
 */
public class LoadingPromptDialog extends BaseDialog {

    /**
     * LoadingPromptDialog
     * @param context context
     */
    public LoadingPromptDialog(Context context) {
        super(context, R.layout.loading_dialog_view);
        findViewById(R.id.id_loading_img).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.unlimited_rotate));
    }

    /**
     * 设置loading中的提示语, 默认无提示语
     * @param content content
     */
    public void setLoadingText(String content) {
        if (!StringUtils.isEmpty(content)) {
            ((TextView) findViewById(R.id.id_content_text)).setText(content);
        }
    }

    @Override
    public void dismiss() {
        findViewById(R.id.id_loading_img).clearAnimation();
        super.dismiss();
    }
}
