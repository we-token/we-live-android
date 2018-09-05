package show.we.lib.widget.chat.spannable_event;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import show.we.lib.model.ChatUserInfo;
import show.we.lib.widget.ChatUserPopMenu;

/**
 * Created by CG on 13-11-29.
 *
 * @author ll
 * @version 3.2.0
 */
public class ClickSpan extends ClickableSpan {

    private int mColor;
    private ChatUserInfo mChatUserInfo;
    /**
     * Context
     */
    protected Context mContext;

    /**
     * ClickSpan
     *
     * @param context context
     * @param color   color
     */
    public ClickSpan(Context context, int color) {
        mContext = context;
        mColor = color;
    }

    /**
     * ClickSpan
     *
     * @param context      context
     * @param color        color
     * @param chatUserInfo chatUserInfo
     */
    public ClickSpan(Context context, int color, ChatUserInfo chatUserInfo) {
        this(context, color);
        mChatUserInfo = chatUserInfo;
    }

    @Override
    public void onClick(View widget) {
        if (mChatUserInfo != null) {
            new ChatUserPopMenu(mContext).showOperatePanel(mChatUserInfo);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        final int radius = 10;
        ds.setColor(mColor);
        ds.setTypeface(Typeface.SERIF);
        ds.setShadowLayer(radius, 1, 1, Color.WHITE);
    }
}

