package show.we.lib.widget;

import android.widget.BaseAdapter;

/**
 * Created by CG on 13-10-23.
 * 提供一个默认的行为，防止大家都继承BaseAdapter，产生重复代码的警告
 *
 * @author ll
 * @version 3.0.0
 */
public abstract class SimpleBaseAdapter extends BaseAdapter {
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
