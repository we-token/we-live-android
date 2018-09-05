package show.we.lib.widget.text_list_dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import show.we.lib.R;
import show.we.lib.widget.BasePopupWindow;

/**
 * Created by Administrator on 13-9-23.
 *
 * @param <T> 泛型
 * @author ll
 * @version 1.0.0
 */
public class TextListPopWindow<T> extends BasePopupWindow {

    private TextListControler<T> mListControler;

    /**
     * TextListPopWindow
     *
     * @param context context
     */
    public TextListPopWindow(Context context) {
        this(context, null);
    }

    /**
     * TextListPopWindow
     *
     * @param context  mContext
     * @param listener listener
     */
    public TextListPopWindow(Context context, OnValueSelectListener<T> listener) {
        super(context);
        ListView listView = (ListView) View.inflate(context, R.layout.layout_list, null);
        setContentView(listView);
        initListView(listView);
        mListControler = new TextListControler<T>(listView, null, this);
        mListControler.setSelectedListener(listener);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 获取焦点，事件将不可穿透（下面的按钮无法响应），输入法无法响应，列表事件需要焦点
        setFocusable(true);
        // 下面两行，点击其他区域，对话框消失
        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);
    }

    /**
     *
     * @param listView
     */
    protected void initListView(ListView listView) {
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
