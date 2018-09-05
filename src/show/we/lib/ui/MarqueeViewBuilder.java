package show.we.lib.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import show.we.sdk.util.StringUtils;
import show.we.lib.R;
import show.we.lib.config.Config;
import show.we.lib.config.Enums;
import show.we.lib.model.GiftListResult;
import show.we.lib.model.Message;
import show.we.lib.model.SlotWinResult;
import show.we.lib.utils.CacheUtils;
import show.we.lib.utils.DisplayUtils;
import show.we.lib.utils.GiftUtils;
import show.we.lib.utils.ImageUtils;
import show.we.lib.utils.LiveUtils;
import show.we.lib.widget.SmoothBubbingLayout;

/**
 * Created by Administrator on 13-9-3.
 *
 * @author ll
 * @version 1.0.0
 */
public class MarqueeViewBuilder implements SmoothBubbingLayout.BuildChildViewListener {


    private class GiftTextEffectViewHolder {
        private TextView mUserNameTextView;
        private TextView mStarNameTextView;
        private ImageView mGiftIcon;
        private LinearLayout mGiftIcons;
        private TextView mGiftCountTextView;
        private TextView mAtTextView;

        public GiftTextEffectViewHolder(View view) {
            mUserNameTextView = (TextView) view.findViewById(R.id.id_user_name);
            mStarNameTextView = (TextView) view.findViewById(R.id.id_star_name);
            mGiftIcon = (ImageView) view.findViewById(R.id.id_gift_icon);
            mGiftIcons = (LinearLayout) view.findViewById(R.id.id_gift_icons);
            mGiftCountTextView = (TextView) view.findViewById(R.id.id_gift_count);
            mAtTextView = (TextView) view.findViewById(R.id.at);
        }
    }

    private Context mContext;

    /**
     * MarqueeViewBuilder
     *
     * @param context context
     */
    public MarqueeViewBuilder(Context context) {
        mContext = context;
    }

    /**
     * buildMarqueeView
     *
     * @param object object
     * @param view   view
     * @return view
     */
    public View buildMarqueeView(final Object object, View view) {
        if (view == null) {
            view = View.inflate(mContext, R.layout.layout_present_gift_item, null);
            view.setTag(new GiftTextEffectViewHolder(view));
        }
        final GiftTextEffectViewHolder viewHolder = (GiftTextEffectViewHolder) view.getTag();

        if (object instanceof Message.SendGiftModel) {
            viewHolder.mAtTextView.setVisibility(View.VISIBLE);
            viewHolder.mGiftCountTextView.setVisibility(View.VISIBLE);
            Message.SendGiftModel notify = (Message.SendGiftModel) object;
            Message.Gift messageGift = notify.getData().getGift();
            long giftCount = messageGift != null ? messageGift.getCount() : 0;
            GiftListResult.Gift gift = GiftUtils.getGift(messageGift != null ? messageGift.getId() : 0);

            int giftCount_tmp=(int) (giftCount);
            viewHolder.mGiftIcons.removeAllViews();
            List<ImageView> imgViews = new ArrayList<ImageView>();
            for(int index=0;index<giftCount_tmp;index++){
            	ImageView img = new ImageView(mContext);
            	Bitmap picture = CacheUtils.getImageCache().getBitmapFromMemCache(gift.getPicUrl(), null,
            			DisplayUtils.dp2px(Config.COMMON_ICON_WIDTH_DP), DisplayUtils.dp2px(Config.COMMON_ICON_HEIGHT_DP));
            	ImageUtils.requestImage(img, gift.getPicUrl(), DisplayUtils.dp2px(Config.COMMON_ICON_WIDTH_DP)
            			, DisplayUtils.dp2px(Config.COMMON_ICON_HEIGHT_DP), 0);
            	if(picture==null){return view;
            	//img.setImageResource(R.drawable.icon_gift_marquee);
            	}
//        		 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.px2dp(250), DisplayUtils.px2dp(250));
            	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtils.dp2px(25), DisplayUtils.dp2px(25));
            	
            	img.setLayoutParams(params);
            	imgViews.add(img);
            	img.setScaleType(ScaleType.FIT_XY);
            	viewHolder.mGiftIcons.addView(img,index);
            }
            Message.From from = notify.getData().getFrom();
            viewHolder.mUserNameTextView.setText(from != null ? from.getNickName()+"("+from.getId()+")送给" : "");

            Message.To to = notify.getData().getTo();

            viewHolder.mStarNameTextView.setText(to != null ? to.getNickName()+"("+to.getId()+")" : "");
            viewHolder.mGiftCountTextView.setText(mContext.getString(R.string.send_gift_count, giftCount, gift.getName()));

           
           
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            String formatDate = format.format(new Date(notify.getData().getTime()));
            viewHolder.mAtTextView.setText("("+formatDate+")");
            
            LiveUtils.requestStarInfo();
        } else if (object instanceof SlotWinResult.Data) {
            SlotWinResult.Data data = (SlotWinResult.Data) object;
            SlotWinResult.Data.User user = data.getUser();
            if (user != null) {
                String name = user.getNickName();
                String reward = Enums.HammerType.getRewardMap().get(data.getRewardKey());
                if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(reward)) {
                    viewHolder.mAtTextView.setVisibility(View.GONE);
                    viewHolder.mGiftIcon.setVisibility(View.GONE);
                    viewHolder.mGiftCountTextView.setVisibility(View.VISIBLE);
                    viewHolder.mUserNameTextView.setText(name);
                    viewHolder.mGiftCountTextView.setText(mContext.getString(R.string.slot_win) + reward);
                }
            }
        } else if (object instanceof Message.TreasureMarqueeModel) {
            viewHolder.mAtTextView.setVisibility(View.VISIBLE);
            viewHolder.mGiftIcon.setVisibility(View.GONE);
            viewHolder.mGiftCountTextView.setVisibility(View.VISIBLE);
            Message.TreasureMarqueeModel.Data data = ((Message.TreasureMarqueeModel) object).getData();
            Message.From from = data.getFrom();
            viewHolder.mUserNameTextView.setText(from.getNickName());
            viewHolder.mStarNameTextView.setText(mContext.getString(R.string.at_star_room, data.getRoom().getStarName()));
            viewHolder.mGiftCountTextView.setText(mContext.getString(R.string.treasure_marquee, data.getCount()));
        } else if (object instanceof Message.TreasureNoticeModel) {
            viewHolder.mAtTextView.setVisibility(View.GONE);
            viewHolder.mGiftIcon.setVisibility(View.GONE);
            viewHolder.mGiftCountTextView.setVisibility(View.GONE);
            Message.TreasureNoticeModel.Data data = ((Message.TreasureNoticeModel) object).getData();
            viewHolder.mStarNameTextView.setText(mContext.getString(R.string.treasure_opening, data.getRoom().getNickName()));
        } else if (object instanceof Message.TreasureAwardModel) {
            viewHolder.mAtTextView.setVisibility(View.GONE);
            viewHolder.mGiftIcon.setVisibility(View.GONE);
            viewHolder.mGiftCountTextView.setVisibility(View.GONE);
            Message.TreasureAwardModel.Data data = ((Message.TreasureAwardModel) object).getData();
            viewHolder.mStarNameTextView.setText(mContext.getString(R.string.treasure_opened, data.getRoom().getNickName()));
        }
        return view;
    }

    @Override
    public View onBuildChildViewEvent(Object object, View view) {
        return buildMarqueeView(object, view);
    }
}
