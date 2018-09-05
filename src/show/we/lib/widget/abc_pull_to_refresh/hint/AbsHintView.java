package show.we.lib.widget.abc_pull_to_refresh.hint;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 提示View基类，所有具体提示View都必须继承自此类
 * @author ll
 * @version 1.0.0
 */
public abstract class AbsHintView extends FrameLayout {

    /**
     * @param context context
     */
    public AbsHintView(Context context) {
        this(context, null);
    }

    /**
     *
     * @param context context
     * @param attrs attrs
     */
    public AbsHintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     * @param context context
     */
    public abstract void init(Context context);

    /**
     * 设置提示语
     * @param hint hint
     */
    public abstract void setHint(String hint);
}
