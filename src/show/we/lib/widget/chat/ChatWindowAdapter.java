package show.we.lib.widget.chat;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import show.we.lib.R;
import show.we.lib.config.UMengConfig;
import show.we.lib.model.GodOfWealth;
import show.we.lib.model.Message;
import show.we.lib.model.SlotWinResult;
import show.we.lib.model.UserInfoResult;
import show.we.lib.utils.LoginUtils;
import show.we.lib.utils.UserInfoUtils;
import show.we.lib.widget.SimpleBaseAdapter;
import show.we.lib.widget.chat.spannable_string.*;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class ChatWindowAdapter extends SimpleBaseAdapter {

    private List<Object> mMessageList;
    private Context mContext;
    private boolean mIsPrivate;
    private UserInfoResult mUserInfoResult;
    /**
     * ChatWindowAdapter
     *
     * @param context context
     */
    public ChatWindowAdapter(Context context) {
        mContext = context;
    }

    /**
     * setMessageList
     *
     * @param list list
     */
    public void setMessageList(List<Object> list) {
        mMessageList = list;
    }

    /**
     * getMessageList
     *
     * @return mMessageList
     */
    public List<Object> getMessageList() {
        return mMessageList;
    }

    /**
     * setIsPrivate
     *
     * @param isPrivate isPrivate
     */
    public void setIsPrivate(boolean isPrivate) {
        mIsPrivate = isPrivate;
    }

    @Override
    public int getCount() {
        return mMessageList != null ? mMessageList.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_message_list_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.id_live_message);
        textView.setVisibility(View.VISIBLE);
        SpannableStringBuilder stringBuilder = null;
        Object messageObject = mMessageList.get(position);
        if (messageObject instanceof Message.EnterRoomModel) {
            stringBuilder = new EntryRoomString((Message.EnterRoomModel) messageObject, textView, this).getSpan();
        } else if (messageObject instanceof Message.ShutUpModel) {
            stringBuilder = new ShutUpString((Message.ShutUpModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.SongAgreeModel) {
            stringBuilder = new SongAgreeString(mContext, (Message.SongAgreeModel) messageObject).getSpan();
        } else if (messageObject instanceof Message.SongRefuseModel) {
            stringBuilder = new SongRefuseString(mContext, (Message.SongRefuseModel) messageObject).getSpan();
        } else if (messageObject instanceof Message.SystemNoticeModel) {
            stringBuilder = new SystemNoticeString((Message.SystemNoticeModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.PuzzleWinModel) {
            stringBuilder = new PuzzleWinString((Message.PuzzleWinModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.FortuneModel) {
            stringBuilder = new SendFortuneString((Message.FortuneModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.SendGiftModel) {
            stringBuilder = new SendGiftString((Message.SendGiftModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.ReceiveModel) {
            stringBuilder = new UserMessageString((Message.ReceiveModel) messageObject, textView, mIsPrivate).getSpan();
        } else if (messageObject instanceof UserTypeInfo) {
            stringBuilder = new LevelUpgradeMessageString((UserTypeInfo) messageObject, textView).getSpan();
        } else if (messageObject instanceof GodOfWealth.AwardUser) {
            stringBuilder = new GodOfWealthAwardString(mContext, (GodOfWealth.AwardUser) messageObject).getSpan();
        } else if (messageObject instanceof SlotWinResult.Data) {
            stringBuilder = new SlotWinString((SlotWinResult.Data) messageObject, textView).getSpan();
        } else if (messageObject instanceof  Message.SendFeatherModel) {
            stringBuilder = new SendFeatherString((Message.SendFeatherModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.TreasureNotifyModel) {
            stringBuilder = new TreasureNotifyString((Message.TreasureNotifyModel) messageObject, textView).getSpan();
        } else if (messageObject instanceof Message.TreasureRoomModel.Data) {
            stringBuilder = new TreasureAwardString((Message.TreasureRoomModel.Data) messageObject, textView).getSpan();
        } else if (messageObject instanceof String) {
            stringBuilder = new InfoString((String) messageObject, textView).getSpan();
        }

        if (stringBuilder != null) {
            try {
                textView.setText(stringBuilder, TextView.BufferType.SPANNABLE);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (Exception e) {
                MobclickAgent.reportError(mContext, UMengConfig.KEY_CHATWINDOW_ERROR_STRING + "\n" + stringBuilder.toString());
            }
        }else{
        	textView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
