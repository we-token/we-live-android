package show.we.lib.widget.text_list_dialog;

import android.app.Dialog;
import android.widget.PopupWindow;

/**
 * Created by CG on 14-2-27.
 *
 * @param <T> 泛型
 * @author ll
 * @version 4.0.0
 */
public interface OnValueSelectListener<T> {

    /**
     * onValueSelected
     *
     * @param dialog      dialog
     * @param popupWindow popupWindow
     * @param position    position
     * @param show        show
     * @param message     message
     * @param value       value
     * @param o           o
     */
    public void onValueSelected(Dialog dialog, PopupWindow popupWindow, int position, String show, String message, long value, T o);
}
