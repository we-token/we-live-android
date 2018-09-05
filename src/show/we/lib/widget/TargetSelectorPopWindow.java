package show.we.lib.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.model.Message;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.utils.DisplayUtils;

/**
 * @author ll
 * @version 1.0.0
 */
public class TargetSelectorPopWindow extends PopupWindow {

    /**
     * 选择聊天对象的接口
     */
    public static interface OnTargetSelectListener {
        /**
         * 选择聊天对象的接口
         *
         * @param target    聊天对象
         */
        public void onTargetSelected(Message.To target);
    }

    public static interface OnWindowDismissListener {
        /**
         * 选择选项消失时调用的接口
         * @param isDismiss 是否消失
         */
        public void onWindowDismiss(boolean isDismiss);
    }

    private OnTargetSelectListener mTargetSelectListener;
    private OnWindowDismissListener mWindowDismissListener;
    private boolean mContainAll;
    private View mLayout;
    private TargetListAdapter mTargetListAdapter = new TargetListAdapter();
    private Context mContext;

    /**
     * constructor
     *
     * @param context              context
     * @param targetSelectListener targetSelectListener
     * @param containAll           containAll
     */
    public TargetSelectorPopWindow(Context context
            , OnTargetSelectListener targetSelectListener
            , OnWindowDismissListener windowDismissListener
            , boolean containAll) {
        super(context);
        mContext = context;
        mLayout = View.inflate(context, R.layout.layout_target_user_selector, null);
        setContentView(mLayout);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);

        setBackgroundDrawable(new ColorDrawable(0));
        setOutsideTouchable(true);

        mTargetSelectListener = targetSelectListener;
        mWindowDismissListener = windowDismissListener;

        ((ListView) mLayout.findViewById(R.id.id_content_listview)).setAdapter(mTargetListAdapter);
        ((ListView) mLayout.findViewById(R.id.id_content_listview)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mContainAll) {
                    if (position == 0) {
                        Message.To object = new Message.To();
                        object.setNickName(null);
                        mTargetSelectListener.onTargetSelected(object);
                    } else {
                        mTargetSelectListener.onTargetSelected(LiveCommonData.getChatTargetList().get(position - 1));
                    }
                } else {
                    mTargetSelectListener.onTargetSelected(LiveCommonData.getChatTargetList().get(position));
                }
                dismiss();
            }
        });

        mContainAll = containAll;
    }

    @Override
    public void dismiss() {
        mWindowDismissListener.onWindowDismiss(true);
        super.dismiss();
    }

    /**
     * 显示在某个锚点的上面
     *
     * @param anchor 某个锚点的上面
     */
    public void show(View anchor) {
        mLayout.measure(View.MeasureSpec.makeMeasureSpec(DisplayUtils.getWidthPixels(), View.MeasureSpec.AT_MOST)
                , View.MeasureSpec.makeMeasureSpec(DisplayUtils.getHeightPixels(), View.MeasureSpec.AT_MOST));
        int selfWidth = mLayout.getMeasuredWidth();
        int selfHeight = mLayout.getMeasuredHeight();
        int anchorWidth = anchor.getMeasuredWidth();
        int anchorHeight = anchor.getMeasuredHeight();
        int xOff = (anchorWidth - selfWidth) / 2;
        int yOff = -(selfHeight + anchorHeight);
        showAsDropDown(anchor, xOff, yOff);
        mTargetListAdapter.notifyDataSetChanged();
    }

    private class TargetListAdapter extends SimpleBaseAdapter {
        @Override
        public int getCount() {
            return LiveCommonData.getChatTargetList().size() + (mContainAll ? 1 : 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mLayout.getContext(), R.layout.layout_target_select_list_item, null);
            }

            if (mContainAll && position == 0) {
                ((TextView) convertView.findViewById(R.id.txt)).setText(mLayout.getContext().getString(R.string.all_person));
            } else {
                Message.To to = LiveCommonData.getChatTargetList().get(mContainAll ? position - 1 : position);
                ((TextView) convertView.findViewById(R.id.txt)).setText(to.getNickName());
                convertView.setTag(to);
            }
            return convertView;
        }
    }
}
