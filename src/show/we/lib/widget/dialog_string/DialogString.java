package show.we.lib.widget.dialog_string;

import android.content.Context;
import show.we.lib.R;

/**
 * Created by tiger on 14-4-14.
 * @author ll
 * @version 3.8.0
 */


public class DialogString {
    /**
     * @return mRefuse
     */
    public static String getmRefuse() {
        return mRefuse;
    }
    /**
     * @return mLoginNow
     */
    public static String getmLoginNow() {
        return mLoginNow;
    }
    /**
     * @return mNexttime
     */
    public static String getmNexttime() {
        return mNexttime;
    }
    /**
     * @return mNotWantChat
     */
    public static String getmNotWantChat() {
        return mNotWantChat;
    }
    /**
     * @return mNotWantSend
     */
    public static String getmNotWantSend() {
        return mNotWantSend;
    }
    /**
     * @return mClickOtherUserPrompt
     */
    public static String getmClickOtherUserPrompt() {
        return mClickOtherUserPrompt;
    }
    /**
     * @return mFocusPrompt
     */
    public static String getmFocusPrompt() {
        return mFocusPrompt;
    }
    /**
     * @return mSharePrompt
     */
    public static String getmSharePrompt() {
        return mSharePrompt;
    }
    /**
     * @return mSofaPrompt
     */
    public static String getmSofaPrompt() {
        return mSofaPrompt;
    }
    /**
     * @return mSendGiftPrompt
     */
    public static String getmSendGiftPrompt() {
        return mSendGiftPrompt;
    }
    /**
     * @return mChooseChatPersonPrompt
     */
    public static String getmChooseChatPersonPrompt() {
        return mChooseChatPersonPrompt;
    }
    /**
     * @return mClickChatEdittextPrompt
     */
    public static String getmClickChatEdittextPrompt() {
        return mClickChatEdittextPrompt;
    }
    /**
     * @return mSendFeatherPrompt
     */
    public static String getmSendFeatherPrompt() {
        return mSendFeatherPrompt;
    }
    /**
     * @return mRechargePrompt
     */
    public static String getmRechargePrompt() {
        return mRechargePrompt;
    }
    /**
     * @return mErniePrompt
     */
    public static String getmErniePrompt() {
        return mErniePrompt;
    }
    /**
     * @return mBroadCastPrompt
     */
    public static String getmBroadCastPrompt() {
        return mBroadCastPrompt;
    }
    /**
     * @return mRequestSongPrompt
     */
    public static String getmRequestSongPrompt() {
        return mRequestSongPrompt;
    }


    /**
     * 拒绝
     */
    protected static String mRefuse;
    /**
     * 立即登录
     */
    protected static String mLoginNow;
    /**
     * 下次再说
     */
    protected static String mNexttime;
    /**
     * 暂不想聊
     */
    protected static String mNotWantChat;
    /**
     * 暂不想送
     */
    protected static String mNotWantSend;

    /**
     * 登录提示
     */
    protected static String mFocusPrompt;
    /**
     * 分享提示
     */
    protected static String mSharePrompt;
    /**
     * 抢座提示
     */
    protected static String mSofaPrompt;

    /**
     * 点击其它用户提示
     */
    protected static String mClickOtherUserPrompt;
    /**
     * 送礼提示
     */
    protected static String mSendGiftPrompt;
    /**
     * 选择聊天对象
     */
    protected static String mChooseChatPersonPrompt;
    /**
     * 点击聊天输入框提示
     */
    protected static String mClickChatEdittextPrompt;
    /**
     * 送羽毛提示
     */
    protected static String mSendFeatherPrompt;
    /**
     * 充值提示
     */
    protected static String mRechargePrompt;
    /**
     * 摇奖提示
     */
    protected static String mErniePrompt;
    /**
     * 广播提示
     */
    protected static String mBroadCastPrompt;
    /**
     * 点歌提示
     */
    protected static String mRequestSongPrompt;

    /**
     * init
     * @param context context
     */
    public static void init(Context context) {
        new DialogString(context);
    }

    /**
     * @param mContext context
     */
    public DialogString(Context mContext) {
        mRefuse = mContext.getString(R.string.refuse);
        mLoginNow = mContext.getString(R.string.login_now);
        mNexttime = mContext.getString(R.string.next_time);
        mNotWantChat = mContext.getString(R.string.not_want_chat);
        mNotWantSend = mContext.getString(R.string.not_want_send);
        mFocusPrompt = mContext.getString(R.string.focus_prompt);
        mSharePrompt = mContext.getString(R.string.share_prompt);
        mSofaPrompt = mContext.getString(R.string.sofa_prompt);
        mClickOtherUserPrompt = mContext.getString(R.string.click_other_user_prompt);
        mSendGiftPrompt = mContext.getString(R.string.send_gift_prompt);
        mChooseChatPersonPrompt = mContext.getString(R.string.choose_chat_person_prompt);
        mClickChatEdittextPrompt = mContext.getString(R.string.click_chat_edittext_prompt);
        mSendFeatherPrompt = mContext.getString(R.string.send_feather_prompt);
        mRechargePrompt = mContext.getString(R.string.recharge_prompt);
        mErniePrompt = mContext.getString(R.string.ernie_prompt);
        mBroadCastPrompt = mContext.getString(R.string.broadcast_prompt);
        mRequestSongPrompt = mContext.getString(R.string.request_song_prompt);
    }
}
