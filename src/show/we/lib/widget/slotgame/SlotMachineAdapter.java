package show.we.lib.widget.slotgame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.Enums;
import show.we.lib.utils.ImageUtils;
import show.we.lib.widget.wheelview.adapters.AbstractWheelAdapter;

import java.util.List;

/**
 * 摇奖礼品项Adapter
 * @author ll
 * @version 1.0.0
 */
public class SlotMachineAdapter extends AbstractWheelAdapter {

    private Context mContext;

    private List<SlotRewardItem> mRewardItemList;

    /**
     * @param context context
     */
    public SlotMachineAdapter(Context context) {
        mContext = context;
    }

    /**
     *
     * @param hammerType hammerType
     */
    public void updateRewardType(Enums.HammerType hammerType) {
        mRewardItemList = hammerType.getCachedRewardList();
        notifyDataChangedEvent();
    }

    @Override
    public int getItemsCount() {
        return mRewardItemList != null ? mRewardItemList.size() : 0;
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.slot_reward_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        SlotRewardItem rewardItem = mRewardItemList.get(index);
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (rewardItem.getRewardKey().equals("COIN10000")) {
            viewHolder.mRewardImageView.setImageResource(R.drawable.slot_reward_coin);
        } else {
            ImageUtils.requestImage(viewHolder.mRewardImageView, rewardItem.getRewardPicUrl()
                    , Integer.MAX_VALUE, Integer.MAX_VALUE, R.drawable.default_gift_icon);
        }
        viewHolder.mRewardNumTextView.setText("x " + rewardItem.getRewardNum());
        return convertView;
    }

    private class ViewHolder {
        private ImageView mRewardImageView;
        private TextView mRewardNumTextView;

        public ViewHolder(View convertView) {
            mRewardImageView = (ImageView) convertView.findViewById(R.id.img_reward_icon);
            mRewardNumTextView = (TextView) convertView.findViewById(R.id.txt_reward_num);
        }
    }
}
