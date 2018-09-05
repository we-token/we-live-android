package show.we.lib.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import show.we.lib.R;

/**
 * @author ll
 * @version 1.0.0
 */
public class SendGiftCountSelectorPopWindow extends PopupWindow {

    /**
     * 选择送礼物数量的接口
     */
    public static interface OnSendGiftCountSelectListener {
        /**
         * 选择送礼物数量的接口
         *
         * @param count 送礼物数量
         */
        public void onSendGiftCountSelected(int count);
    }

    private OnSendGiftCountSelectListener mGiftCountSelectListener;
    private View mContentView;
    private Context mContext;

    /**
     * constructor
     *
     * @param context                     context
     * @param sendGiftCountSelectListener sendGiftCountSelectListener
     */
    public SendGiftCountSelectorPopWindow(Context context, OnSendGiftCountSelectListener sendGiftCountSelectListener) {
        super(context);
        mContext = context;
        mContentView = View.inflate(context, R.layout.layout_send_gift_popup, null);
        setContentView(mContentView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);

        if (sendGiftCountSelectListener == null) {
            throw new IllegalArgumentException("sendGiftCountSelectListener must be not null !");
        }

        mGiftCountSelectListener = sendGiftCountSelectListener;

        ((ListView) mContentView.findViewById(R.id.id_send_gift_pop_listview)).setAdapter(new GiftCountListAdapter());
    }

    /**
     * 显示在某个锚点的上面
     *
     * @param anchor    某个锚点的上面
     * @param maxWidth  popupWindow最大宽度
     * @param maxHeight popupWindow最大高度
     */
    public void show(View anchor, int maxWidth, int maxHeight) {
        mContentView.measure(View.MeasureSpec.makeMeasureSpec(maxWidth, View.MeasureSpec.AT_MOST)
                , View.MeasureSpec.makeMeasureSpec(maxHeight, View.MeasureSpec.AT_MOST));
        int selfHeight = mContentView.getMeasuredHeight();
        int anchorHeight = anchor.getMeasuredHeight();
        int yOff = -(selfHeight + anchorHeight);
        setWidth(maxWidth);
        setHeight(selfHeight);
        showAsDropDown(anchor, 0, yOff);
    }

    private class GiftCountListAdapter extends SimpleBaseAdapter {

        @Override
        public int getCount() {
            return mContext.getResources().getStringArray(R.array.gift_count_txt).length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.layout_send_gift_pop_list_item, null);
            }
            ((TextView) convertView).setText(mContext.getResources().getStringArray(R.array.gift_count_txt)[pos]);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mGiftCountSelectListener != null) {
                        mGiftCountSelectListener.onSendGiftCountSelected(mContext.getResources().getIntArray(R.array.gift_count)[pos]);
                    }
                }
            });
            return convertView;
        }
    }
}
