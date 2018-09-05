package show.we.lib.websocket;

import android.os.Handler;
import android.os.Message;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * WebSocket封装
 *
 * @author ll
 * @version 1.0.0
 */
public class WebSocket implements IOCallback {

    private static final String TAG = "WebSocket";

    /**
     * WebSocket消息回调
     *
     * @author ll
     * @version 1.0.0
     */
    public static interface OnWebSocketListener {
        /**
         * 收到消息
         *
         * @param message 消息字符串
         */
        public void onMessageReceived(String message);

        /**
         * 连接成功
         */
        public void onConnected();

        /**
         * 连接断开
         */
        public void onDisconnected();

        /**
         * 出错
         *
         * @param exception 导致出错的异常对象
         */
        public void onError(SocketIOException exception);
    }

    private static final int MSG_ID_ERROR = 0x00;
    private static final int MSG_ID_CONNECTED = 0x01;
    private static final int MSG_ID_DISCONNECTED = 0x02;
    private static final int MSG_ID_RECEIVED = 0x03;

    private boolean mIsConnecting = false;
    private IWebSocket mWebSocket;
    private OnWebSocketListener mWebSocketListener;

    /**
     * 设置回调
     *
     * @param webSocketListener 消息监听接口 <b>非空</b>
     */
    public void setWebSocketListener(OnWebSocketListener webSocketListener) {
        this.mWebSocketListener = webSocketListener;
    }

    /**
     * 连接操作
     *
     * @param url    连接地址
     * @param params 连接参数
     */
    public void connect(String url, HashMap<String, Object> params) {
        mIsConnecting = true;

        if (mWebSocket != null) {
            mWebSocket.disconnect();
            mWebSocket = null;
        }

        try {
            mWebSocket = new SocketIOProxy(url, params, this);
            mWebSocket.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param message 消息字符串
     */
    public void sendMessage(String message) {
        if (mWebSocket == null || !mWebSocket.isConnected()) {
            throw new IllegalStateException("Socket is not connected!");
        }
        mWebSocket.send(message);
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        if (mWebSocket != null && mWebSocket.isConnected()) {
            mWebSocket.disconnect();
        }
    }

    /**
     * 是否已经连接
     *
     * @return true表示连接好
     */
    public boolean isConnected() {
        return mWebSocket != null && mWebSocket.isConnected();
    }

    /**
     * 是否正在连接
     *
     * @return true表示正在连接
     */
    public boolean isConnecting() {
        return mIsConnecting;
    }

    @Override
    public void onDisconnect() {
        mIsConnecting = false;
        mMessageHandler.sendMessage(Message.obtain(mMessageHandler, MSG_ID_DISCONNECTED, null));
    }

    @Override
    public void onConnect() {
        mIsConnecting = false;
        mMessageHandler.sendMessage(Message.obtain(mMessageHandler, MSG_ID_CONNECTED, null));
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
    	System.out.println("onMessage_data:"+data);
        mMessageHandler.sendMessage(Message.obtain(mMessageHandler, MSG_ID_RECEIVED, data));
    }

    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
    	System.out.println("onMessage_data_json:"+json.toString());
        mMessageHandler.sendMessage(Message.obtain(mMessageHandler, MSG_ID_RECEIVED, json.toString()));
    }

    @Override
    public void on(String event, IOAcknowledge ack, Object... args) {
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        mIsConnecting = false;
        mMessageHandler.sendMessage(Message.obtain(mMessageHandler, MSG_ID_ERROR, socketIOException));
    }

    private Handler mMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mWebSocketListener != null) {
                switch (msg.what) {
                    case MSG_ID_CONNECTED:
                    	System.out.println("isconnecte..");
                        mWebSocketListener.onConnected();
                        break;

                    case MSG_ID_DISCONNECTED:
                    	System.out.println("disconnecte..");
                        mWebSocketListener.onDisconnected();
                        break;

                    case MSG_ID_ERROR:
                    	System.out.println("connecte_error..");
                        mWebSocketListener.onError((SocketIOException) msg.obj);
                        break;

                    case MSG_ID_RECEIVED:
                    	System.out.println("connecte_received..");
                        mWebSocketListener.onMessageReceived((String) msg.obj);
                        break;

                    default:
                        break;
                }
            }
        }
    };
}
