package show.we.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import show.we.lib.R;

/**
 * baseDialog
 *
 * @author ll
 * @version 3.8.0
 */
public class BaseDialog extends Dialog {

    /**
     * 创建BaseDialog
     * @param context context
     * @param layoutResID layoutResID
     */
    public BaseDialog(Context context, int layoutResID) {
        this(context, layoutResID, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     *
     * @param context context
     * @param layoutResID layoutResID
     * @param width width
     * @param height height
     */
    public BaseDialog(Context context, int layoutResID, int width, int height) {
        this(context, layoutResID, width, height, Gravity.CENTER);
    }

    /**
     *
     * @param context context
     * @param layoutResID layoutResID
     * @param width width
     * @param height height
     * @param gravity gravity
     */
    public BaseDialog(Context context, int layoutResID, int width, int height, int gravity) {
        super(context, getTheme(context));
        setContentView(layoutResID);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setWindowAnimations(android.R.style.Animation_Dialog);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        window.setAttributes(params);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    private static int getTheme(Context context) {
        int dialogStyle = R.style.Dialog_Standard;
        Resources.Theme theme = context.getTheme();
        if (theme != null) {
            TypedArray styleAttrs = theme.obtainStyledAttributes(R.attr.dialogStyle, R.styleable.AppTheme);
            if (styleAttrs != null) {
                dialogStyle = styleAttrs.getResourceId(R.styleable.AppTheme_dialogStyle, 0);
            }
        }
        return dialogStyle;
    }
}
