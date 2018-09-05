package show.we.lib.widget.text_list_dialog;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.widget.dialog.BaseDialog;

/**
 * @param <T> 泛型
 * @author ll
 * @version 1.0.0
 */
public class TextListDialog<T> extends BaseDialog {

    private TextView mTitle;
    private TextListControler<T> mListControler;

    /**
     * constructor
     *
     * @param context context
     */
    public TextListDialog(Context context) {
        this(context, null);
    }

    /**
     * constructor
     *
     * @param context  context
     * @param listener listener
     */
    public TextListDialog(Context context, OnValueSelectListener<T> listener) {
        super(context, R.layout.layout_text_dialog);

        mTitle = (TextView) findViewById(R.id.id_title);
        mListControler = new TextListControler<T>((ListView) findViewById(R.id.id_listview), this, null);
        mListControler.setSelectedListener(listener);
        setCanceledOnTouchOutside(true);
    }

    /**
     * 设置标题资源id
     *
     * @param titleRes 标题资源id
     */
    public void setTitleRes(int titleRes) {
        mTitle.setText(titleRes);
    }

    /**
     * 设置标题文本
     *
     * @param title 标题文本
     */
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    /**
     * 设置Title是否显示
     * @param visibility visibility
     */
    public void setTitleVisibility(int visibility) {
        mTitle.setVisibility(visibility);
    }

    /**
     * getListControler
     *
     * @return TextListControler<T>
     */
    public TextListControler<T> getListControler() {
        return mListControler;
    }
}
