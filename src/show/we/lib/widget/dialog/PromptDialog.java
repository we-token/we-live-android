package show.we.lib.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;

/**
 * Created by Administrator on 2014/4/24.
 * @author ll
 * @version 3.8.0
 */
public class PromptDialog extends BaseDialog {

    private View.OnClickListener mConfirmBtnListner;

    /**
     * PromptDialog
     * @param context context
     */
    public PromptDialog(Context context) {
        super(context, R.layout.layout_prompt_dialog_view);
        mConfirmBtnListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };

        findViewById(R.id.id_prompt_btn).setOnClickListener(mConfirmBtnListner);
    }

    /**
     * setPromptText
     * @param resId resId
     */
    public void setPromptText(int resId) {
        String content = getContext().getResources().getString(resId);
        setPromptText(content);
    }

    /**
     * setPromptText
     * @param content content
     */
    public void setPromptText(String content) {
        if (!StringUtils.isEmpty(content)) {
            ((TextView) findViewById(R.id.id_prompt_content)).setText(content);
        }
    }

    /**
     * setConfirmBtnText
     * @param resId resId
     */
    public void setConfirmBtnText(int resId) {
        String confirm = getContext().getResources().getString(resId);
        setConfirmBtnText(confirm);
    }

    /**
     *  setConfirmBtnText
     * @param content content
     */
    public void setConfirmBtnText(String content) {
        if (!StringUtils.isEmpty(content)) {
            ((TextView) findViewById(R.id.id_prompt_btn)).setText(content);
        }
    }

    /**
     * setConfirmBtnListener
     * @param listener listener
     */
    public void setConfirmBtnListener(View.OnClickListener listener) {
        if (listener != null) {
            mConfirmBtnListner = listener;
            findViewById(R.id.id_prompt_btn).setOnClickListener(mConfirmBtnListner);
        }
    }

}

