package show.we.lib.widget.chat;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import show.we.lib.R;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.control.ObserverGroup;
import show.we.lib.control.OnDataChangeObserver;
import show.we.lib.model.Message;
import show.we.lib.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天窗口
 *
 * @author ll
 * @version 1.0.0
 */
public class ChatWindow extends ListView implements OnDataChangeObserver {

    private static final int MAX_MESSAGE_COUNT = 50;
    private static final float DIVIDER_HEIGHT = 0.5f;

    private List<Object> mMessageList = new ArrayList<Object>(MAX_MESSAGE_COUNT);
    private ChatWindowAdapter mAdapter;

    /**
     * 构造函数
     *
     * @param context context
     */
    public ChatWindow(Context context) {
        this(context, null);
    }

    /**
     * 构造函数
     *
     * @param context context
     * @param attrs   attrs
     */
    public ChatWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCacheColorHint(0);
        setSelector(R.drawable.transparent);
        setDivider(getResources().getDrawable(R.drawable.divider_color));
        setDividerHeight((int) DisplayUtils.dp2px(DIVIDER_HEIGHT));
        setFadingEdgeLength(0);
        setVerticalScrollBarEnabled(false);
        setHeaderDividersEnabled(false);
        setFooterDividersEnabled(false);
        final int paddingWidth = DisplayUtils.dp2px(4);
        setPadding(paddingWidth, 0, paddingWidth, 0);
        setTranscriptMode(TRANSCRIPT_MODE_NORMAL);
		final int backgroundColor = 0xfffbfbfb;
		setBackgroundColor(backgroundColor);

        mAdapter = new ChatWindowAdapter(context);
        mAdapter.setMessageList(mMessageList);
        setAdapter(mAdapter);

        DataChangeNotification.getInstance().addObserver(IssueKey.CHAT_PAGE_CHANGE, this, ObserverGroup.getLiveGroup());
        DataChangeNotification.getInstance().addObserver(IssueKey.INPUT_METHOD_CLOSED, this, ObserverGroup.getLiveGroup());
        DataChangeNotification.getInstance().addObserver(IssueKey.SWITCH_STAR_IN_LIVE, this, ObserverGroup.getLiveGroup());
    }

    /**
     * 是否是私聊
     * @param isPrivate isPrivate
     */
    public void setIsPrivate(boolean isPrivate) {
        mAdapter.setIsPrivate(isPrivate);
    }


    /**
     * 接收到消息
     *
     * @param message 消息对象
     */
    public void receivedMessage(Object message) {
        mMessageList.add(message);
        if (mMessageList.size() > MAX_MESSAGE_COUNT) {
            mMessageList.remove(0);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 聊天消息
     *
     * @param msgObject msgObject
     */
    public void renderMessage(Message.ReceiveModel msgObject) {
        mMessageList.add(msgObject);
        if (mMessageList.size() > MAX_MESSAGE_COUNT) {
            mMessageList.remove(0);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if (IssueKey.INPUT_METHOD_CLOSED.equals(issue)) {
            setSelection(mAdapter.getCount() - 1);
        } else if (IssueKey.SWITCH_STAR_IN_LIVE.equals(issue)) {
            mMessageList.clear();
            mAdapter.notifyDataSetChanged();
        } else if (IssueKey.CHAT_PAGE_CHANGE.equals(issue)) {
            setSelection(mAdapter.getCount() - 1);
        }
    }
}
