package show.we.lib.utils;

import show.we.sdk.util.EnvironmentUtils;
import show.we.sdk.util.StringUtils;
import show.we.lib.config.CacheObjectKey;
import show.we.lib.config.Config;
import show.we.lib.config.StarRoomKey;
import show.we.lib.ui.LiveCommonData;
import show.we.lib.websocket.WebSocket;

import io.vov.utils.Log;

import java.util.HashMap;

/**
 * Created by CG on 13-12-10.
 *
 * @author ll
 * @version 3.3.0
 */
public class WebSocketUtils {

    /**
     * 连接WebSocket
     *
     * @param webSocketListener 接口
     */
    public static void connectWebSocket(WebSocket.OnWebSocketListener webSocketListener) {
        String connectInfo = createWebSocketConnectInfo();
        long roomId = LiveCommonData.getRoomId();
        WebSocket webSocket = LiveCommonData.getWebSocket();
        String webSocketConnectInfo = LiveCommonData.getWebSocketConnectInfo();
        if (webSocket == null || !webSocket.isConnected() || !StringUtils.equal(connectInfo, webSocketConnectInfo)) {
            String curAccessToken = CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)
                    ? (String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN) : null;

            if (webSocket != null) {
                webSocket.setWebSocketListener(null);
                webSocket.disconnect();
            }

            LiveCommonData.setWebSocket(null);

            webSocket = new WebSocket();
            webSocket.setWebSocketListener(webSocketListener);
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(StarRoomKey.ROOM_ID_KEY, roomId + "");
            Log.d("room_id:", roomId);
            if (curAccessToken != null) {
            	Log.d("access_token:", curAccessToken);
                params.put("access_token", curAccessToken);
            }

            params.putAll(EnvironmentUtils.GeneralParameters.parameters());
            Log.d("scoketurl:", Config.getWebSocketUrl());
            webSocket.connect(Config.getWebSocketUrl(), params);

            webSocketConnectInfo = connectInfo;
            LiveCommonData.setWebSocketConnectInfo(webSocketConnectInfo);
            LiveCommonData.setWebSocket(webSocket);
        }
    }

    /**
     * 断开webSocket连接
     */
    public static void disconnectWebSocket() {
        if (LiveCommonData.getWebSocket() != null) {
            LiveCommonData.getWebSocket().setWebSocketListener(null);
            LiveCommonData.getWebSocket().disconnect();
            LiveCommonData.setWebSocket(null);
        }
    }

    private static String createWebSocketConnectInfo() {
        String info = CacheUtils.getObjectCache().contain(CacheObjectKey.ACCESS_TOKEN)
                ? (String) CacheUtils.getObjectCache().get(CacheObjectKey.ACCESS_TOKEN) : "null";
        if (LoginUtils.isAlreadyLogin()) {
            info += UserInfoUtils.getUserInfo().getData().getNickName();
        }
        return info;
    }
}
