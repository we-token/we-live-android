package show.we.lib.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;

/**
 * Created by Administrator on 13-8-14.
 */
public class WebSocketClientProxy implements IWebSocket {

    /**
     * The Constant STATE_INIT.
     */
    private static final int STATE_INIT = 0;

    /**
     * The Constant STATE_CONNECTING.
     */
    private static final int STATE_CONNECTING = 1;

    /**
     * The Constant STATE_READY.
     */
    private static final int STATE_READY = 2;

    /**
     * The Constant STATE_INTERRUPTED.
     */
    private static final int STATE_INTERRUPTED = 3;

    /**
     * The Constant STATE_INVALID.
     */
    private static final int STATE_INVALID = 4;

    /**
     * The mState.
     */
    private int mState = STATE_INIT;

    private WebSocketClient mWebSocketClient;
    private final IOCallback mCallback;

    public WebSocketClientProxy(String url, HashMap<String, Object> params, IOCallback callback) {
        HashMap<String, String> headers = packHeaders(params);
        mCallback = callback;
        mWebSocketClient = new WebSocketClient(URI.create(url), new Draft_17(), headers, 0) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                mState = STATE_READY;
                if (mCallback != null) {
                    mCallback.onConnect();
                }
            }

            @Override
            public void onMessage(String message) {
                if (mCallback != null) {
                    mCallback.onMessage(message, null);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                mState = STATE_INVALID;
                if (mCallback != null) {
                    mCallback.onDisconnect();
                }
            }

            @Override
            public void onError(Exception ex) {
                mState = STATE_INTERRUPTED;
                if (mCallback != null) {
                    mCallback.onError(new SocketIOException("websocket error", ex));
                }
            }

            @Override
            public String getResourceDescriptor() {
                return null;
            }
        };
    }

    @Override
    public void connect() throws IllegalStateException {
        if (mState == STATE_CONNECTING || mState == STATE_READY) {
            throw new IllegalStateException();
        }
        mState = STATE_CONNECTING;
        mWebSocketClient.connect();
    }

    @Override
    public void disconnect() {
        mWebSocketClient.close();
    }

    @Override
    public boolean isConnected() {
        return mState == STATE_READY;
    }

    @Override
    public void send(String message) {
        mWebSocketClient.send(message);
    }

    private HashMap<String, String> packHeaders(HashMap<String, Object> params) {
        HashMap<String, String> headers = new HashMap<String, String>(params.size());
        for (String key : params.keySet()) {
            headers.put(key, params.get(key).toString());
        }
        return headers;
    }
}

