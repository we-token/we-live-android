package show.we.lib.widget.expression;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import show.we.lib.R;
import show.we.lib.utils.GifCacheUtils;
import show.we.lib.utils.Utils;
import show.we.lib.widget.SimpleBaseAdapter;
import show.we.lib.widget.gif.GifView;

/**
 * Created by CG on 13-12-3.
 *
 * @author ll
 * @version 3.2.0
 */
public class ExpressionGridAdapter extends SimpleBaseAdapter {

    private static final int SIZE = 28;

    private Context mContext;
    private int mStart;
    private int mLength;

    /**
     * ExpressionGridAdapter
     *
     * @param context context
     * @param start   start
     * @param length  length
     */
    public ExpressionGridAdapter(Context context, int start, int length) {
        mContext = context;
        mStart = start;
        mLength = length;
    }

    @Override
    public int getCount() {
        return SIZE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_live_item_face, null);
        }
        GifView gifView = (GifView) convertView.findViewById(R.id.item_face);
        if (position < mLength) {
            gifView.setGifFrameList(GifCacheUtils.getExpressionGifIcon(mContext, mStart + position));
        } else if (position == SIZE - 1) {
            Utils.setImageResourceSafely(gifView, R.drawable.xml_del_key);
        } else {
            gifView.setImageDrawable(null);
        }
        return convertView;
    }
}
