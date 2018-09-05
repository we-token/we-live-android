package show.we.lib.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import show.we.lib.R;

/**
 * @author ll
 * @version 2.2.0
 */
public class StandardDialog extends BaseDialog {

    private boolean mAutoDismiss = true;

    private View.OnClickListener mConfirmListener;
    private View.OnClickListener mCancelListener;

    private TextView mPositiveTextView;
    private TextView mNegativeTextView;
    private TextView mMainTextView;
    private TextView mViceTextView;
    private ViewGroup mContentView;

    /**
     * @param context Context
     */
    public StandardDialog(final Context context) {
        super(context, R.layout.standard_dialog);

        mPositiveTextView = (TextView) findViewById(R.id.txt_standard_dialog_confirm);
        mNegativeTextView = (TextView) findViewById(R.id.txt_standard_dialog_cancel);
        mContentView = (ViewGroup) findViewById(R.id.layout_standard_dialog_content);
        mMainTextView = (TextView) findViewById(R.id.txt_dialog_main_content_text);
        mViceTextView = (TextView) findViewById(R.id.txt_dialog_vice_content_text);

        findViewById(R.id.txt_standard_dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmListener != null) {
                    mConfirmListener.onClick(v);
                }
                if (mAutoDismiss) {
                    dismiss();
                }
            }
        });

        findViewById(R.id.txt_standard_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener != null) {
                    mCancelListener.onClick(v);
                }
                dismiss();
            }
        });
    }

    /**
     * 设置确认按钮监听
     *
     * @param listener listener
     */
    public void setPositiveButtonClickListener(View.OnClickListener listener) {
        mConfirmListener = listener;
    }

    /**
     * 设置取消按钮监听
     *
     * @param listener listener
     */
    public void setNegativeButtonClickListener(View.OnClickListener listener) {
        mCancelListener = listener;
    }

    /**
     * 设置点击确认按钮是否自动关闭对话框
     *
     * @param auto auto
     */
    public void setAutoDismissWhenConfirm(boolean auto) {
        mAutoDismiss = auto;
    }

    /**
     * 设置确认按钮文字
     *
     * @param text text
     */
    public void setPositiveButtonText(String text) {
        mPositiveTextView.setText(text);
    }

    /**
     * 设置确认按钮文字
     *
     * @param resId resId
     */
    public void setPositiveButtonText(int resId) {
        mPositiveTextView.setText(resId);
    }

    /**
     * 设置取消按钮文字
     *
     * @param text text
     */
    public void setNegativeButtonText(String text) {
        mNegativeTextView.setText(text);
    }

    /**
     * 设置取消按钮文字
     *
     * @param resId resId
     */
    public void setNegativeButtonText(int resId) {
        mNegativeTextView.setText(resId);
    }

    /**
     * 设置主内容文字
     *
     * @param text text
     */
    public void setContentText(String text) {
        mMainTextView.setText(text);
    }

    /**
     * 设置主内容文字
     *
     * @param text text
     */
    public void setContentText(CharSequence text) {
        mMainTextView.setText(text);
    }

    /**
     * 设置主内容文字
     *
     * @param resId resId
     */
    public void setContentText(int resId) {
        mMainTextView.setText(resId);
    }

    /**
     * 设置副内容文字
     *
     * @param text text
     */
    public void setViceContentText(String text) {
        setViceTextViewVisibility(true);
        mViceTextView.setText(text);
    }

    /**
     * 设置副内容文字
     *
     * @param text text
     */
    public void setViceContentText(CharSequence text) {
        setViceTextViewVisibility(true);
        mViceTextView.setText(text);
    }

    /**
     * 设置副内容文字
     *
     * @param resId resId
     */
    public void setViceContentText(int resId) {
        setViceTextViewVisibility(true);
        mViceTextView.setText(resId);
    }

    /**
     * 设置副文字内容可否显示
     * @param able able
     */
    public void setViceTextViewVisibility(boolean able) {
        if (able) {
            mViceTextView.setVisibility(View.VISIBLE);
        } else {
            mViceTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置Content
     *
     * @param view view
     */
    public void replaceContentView(View view) {
        mContentView.removeAllViews();
        mContentView.addView(view);
    }

    /**
     * 设置Content
     *
     * @param layoutResId layoutResId
     */
    public void replaceContentView(int layoutResId) {
        replaceContentView(View.inflate(getContext(), layoutResId, null));
    }
}
