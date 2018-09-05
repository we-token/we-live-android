package show.we.lib.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import show.we.sdk.util.ConstantUtils;
import show.we.lib.R;
import show.we.lib.config.Config;
import show.we.lib.config.UMengConfig;
import show.we.lib.control.DataChangeNotification;
import show.we.lib.control.IssueKey;
import show.we.lib.control.ObserverGroup;
import show.we.lib.control.OnDataChangeObserver;
import show.we.lib.websocket.SocketIOException;
import show.we.lib.websocket.WebSocket;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by CG on 13-12-22.
 *
 * @author ll
 * @version 3.3.0
 */
public class WebSocketManager implements WebSocket.OnWebSocketListener, OnDataChangeObserver {

    private static final int MSG_RECONNECT = 0x06;
    private static final int MSG_RECONNECT_INTERVAL = 5000; //ms

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_RECONNECT:
                    WebSocketUtils.connectWebSocket(WebSocketManager.this);
                    break;
                default:
                    break;
            }
        }
    };
    private Activity mContext;

    /**
     * WebSocketManager
     *
     * @param context context
     */
    public WebSocketManager(Activity context) {
        mContext = context;
        DataChangeNotification.getInstance().addObserver(IssueKey.UPLOAD_USER_INFO_SUCCESS, this, ObserverGroup.getLiveGroup());
        DataChangeNotification.getInstance().addObserver(IssueKey.USER_INFO_UPDATE, this, ObserverGroup.getLiveGroup());
    }

    /**
     * onActivityResume
     */
    public void onActivityResume() {
        WebSocketUtils.connectWebSocket(this);
    }

    /**
     * onActivityDestroy
     */
    public void onActivityDestroy() {
        mHandler.removeMessages(MSG_RECONNECT);
        WebSocketUtils.disconnectWebSocket();
    }

    @Override
    public void onMessageReceived(String message) {
        MessageParseUtils.doParse(mContext, message);
    }

    @Override
    public void onConnected() {
        MobclickAgent.onEvent(mContext, UMengConfig.KEY_WEBSOCKET_CONNECT, UMengConfig.VALUE_SUCCEED);
    }

    @Override
    public void onDisconnected() {
		PrintInfoUtils.print("onDisconnected");
        disconnectedAndReconnect();
    }

    @Override
    public void onError(SocketIOException exception) {
		if (exception.getCause() == null) {
			return;
		} else {
            new Config.ResolveSocketDNSThread().start();
            MobclickAgent.reportError(mContext, UMengConfig.KEY_WEB_SOCKET_CONNECT_ERROR_LOG + "\n" + exception.getCause());
			if (exception.getCause().toString().contains("java.net.UnknownHostException")) {
				WebSocketUtils.disconnectWebSocket();
				if (!mContext.isFinishing()) {
					String str = "请检查您的wifi网络！5秒后重连";
					DataChangeNotification.getInstance().notifyDataChanged(IssueKey.WEB_SOCKET_RECONNECT, str);
					mHandler.sendEmptyMessageDelayed(MSG_RECONNECT, MSG_RECONNECT_INTERVAL);
				}
				return;
			} else {
				// 记录到日志文件
			}
		}

		disconnectedAndReconnect();

		if (!mContext.isFinishing()) {
            MobclickAgent.onEvent(mContext, UMengConfig.KEY_WEBSOCKET_CONNECT, UMengConfig.VALUE_FAIL);
        }
    }

    private void disconnectedAndReconnect() {
        WebSocketUtils.disconnectWebSocket();
        if (!mContext.isFinishing()) {
            String str = mContext.getString(R.string.disconnected_reconnect_after_seconds
                    , MSG_RECONNECT_INTERVAL / ConstantUtils.THOUSAND);
            DataChangeNotification.getInstance().notifyDataChanged(IssueKey.WEB_SOCKET_RECONNECT, str);
            mHandler.sendEmptyMessageDelayed(MSG_RECONNECT, MSG_RECONNECT_INTERVAL);
        }
    }

    @Override
    public void onDataChanged(IssueKey issue, Object o) {
        if (IssueKey.UPLOAD_USER_INFO_SUCCESS.equals(issue)) {
            WebSocketUtils.disconnectWebSocket();
            if (!mContext.isFinishing()) {
                mHandler.sendEmptyMessageDelayed(MSG_RECONNECT, 0);
            }
        } else if (IssueKey.USER_INFO_UPDATE.equals(issue)) {
            if (LoginUtils.isAlreadyLogin()) {
                WebSocketUtils.connectWebSocket(this);
            }
        }
    }
}
