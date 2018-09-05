package show.we.lib.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.model.Message;
import show.we.lib.ui.LiveCommonData;

/**
 * @author ll
 * @version 1.0.0
 */
public class SendGiftTargetSelectorPopWindow extends PopupWindow {

    /**
     * 选择送礼物对象的接口
     */
    public static interface OnSendGiftTargetSelectListener {
        /**
         * 选择送礼物对象的接口
         *
         * @param target 送礼物对象
         */
        public void onSendGiftTargetSelected(Message.To target);
    }

    private OnSendGiftTargetSelectListener mTargetSelectListener;
    private View mContentView;
    private Context mContext;
    private TargetListAdapter mTargetListAdapter;

    /**
     * constructor
     *
     * @param context              context
     * @param targetSelectListener targetSelectListener
     */
    public SendGiftTargetSelectorPopWindow(Context context, OnSendGiftTargetSelectListener targetSelectListener) {
        super(context);
        mContext = context;
        mContentView = View.inflate(context, R.layout.layout_send_gift_popup, null);
        setContentView(mContentView);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);

        mTargetSelectListener = targetSelectListener;

        mTargetListAdapter = new TargetListAdapter();
        ((ListView) mContentView.findViewById(R.id.id_send_gift_pop_listview)).setAdapter(mTargetListAdapter);
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

    /**
     * 更新送礼物对象listView
     */
    public void updateTargetList() {
        mTargetListAdapter.notifyDataSetChanged();
    }

    private class TargetListAdapter extends SimpleBaseAdapter {
        @Override
        public int getCount() {
            return LiveCommonData.getGiftTargetList().size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.layout_send_gift_pop_list_item, null);
            }

            final Message.To to = LiveCommonData.getGiftTargetList().get(position);
            ((TextView) convertView).setText(to.getNickName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    if (mTargetSelectListener != null) {
                        mTargetSelectListener.onSendGiftTargetSelected(to);
                    }
                }
            });
            convertView.setTag(to);

            return convertView;
        }
    }
}
