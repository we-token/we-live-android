package show.we.lib.websocket;

/**
 * WebSocket抽象接口
 *
 * @author ll
 * @version 1.0.0
 */
public interface IWebSocket {
    /**
     * connects to an already set host.
     */
    public void connect();

    /**
     * Disconnect the socket.
     */
    public void disconnect();

    /**
     * Returns, if a connection is established at the moment
     *
     * @return true if a connection is established, false if the transport is
     * not connected or currently connecting
     */
    public boolean isConnected();

    /**
     * Send String data to the Socket.io server.
     *
     * @param message the message String
     */
    public void send(final String message);
}
