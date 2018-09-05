package show.we.lib.websocket;

import show.we.sdk.util.StringUtils;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Created by Administrator on 13-8-14.
 */
public class SocketIOProxy implements IWebSocket {
    private final SocketIO mSocketIO;
    private final IOCallback mCallback;

    public SocketIOProxy(String url, HashMap<String, Object> params, IOCallback callback) {
        mCallback = callback;
        try {
            mSocketIO = new SocketIO(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        for (String key : params.keySet()) {

            Object object = params.get(key);
            if (object != null && !StringUtils.isEmpty(object.toString())) {
            	System.out.println(key+"::"+object.toString());
                mSocketIO.addHeader(key, object.toString());
            }
        }
    }

    @Override
    public void connect() {
        if (mSocketIO != null) {
            mSocketIO.connect(mCallback);
        }
    }

    @Override
    public void disconnect() {
        if (mSocketIO != null) {
            mSocketIO.disconnect();
        }
    }

    @Override
    public boolean isConnected() {
        if (mSocketIO != null) {
            return mSocketIO.isConnected();
        }
        return false;
    }

    @Override
    public void send(String message) {
        if (mSocketIO != null) {
            mSocketIO.send(message);
        }
    }
}
