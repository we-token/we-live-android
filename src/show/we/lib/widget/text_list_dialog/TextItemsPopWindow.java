package show.we.lib.widget.text_list_dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import show.we.lib.R;
import show.we.lib.widget.BasePopupWindow;

/**
 * Created by Administrator on 13-9-23.
 * 适用于显示在输入框的旁边
 *
 * @param <T> 泛型
 * @author ll
 * @version 1.0.0
 */
public class TextItemsPopWindow<T> extends BasePopupWindow {

    private TextItemsController<T> mTextItemsController;

    /**
     * TextItemsPopWindow
     *
     * @param context context
     */
    public TextItemsPopWindow(Context context) {
        this(context, null);
    }

    /**
     * TextListPopWindow
     *
     * @param context  mContext
     * @param listener listener
     */
    public TextItemsPopWindow(Context context, OnValueSelectListener<T> listener) {
        super(context);
        View view = View.inflate(context, R.layout.layout_text_items, null);
        setContentView(view);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.transparent));
        mTextItemsController = new TextItemsController<T>((LinearLayout) view.findViewById(R.id.id_content), null, this);
        mTextItemsController.setSelectedListener(listener);
        mTextItemsController.setDividerColor(context.getResources().getColor(R.color.popup_menu_divider_normal));
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
    }

    /**
     * getTextItemsControler
     *
     * @return mTextItemsController
     */
    public TextItemsController<T> getTextItemsControler() {
        return mTextItemsController;
    }
}
