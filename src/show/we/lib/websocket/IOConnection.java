/*
 * socket.io-java-client IOConnection.java
 *
 * Copyright (c) 2012, Enno Boland
 * socket.io-java-client is a implementation of the socket.io protocol in Java.
 * 
 * See LICENSE file for more information
 */
package show.we.lib.websocket;

import show.we.sdk.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * The Class IOConnection.
 *
 * @author ll
 * @version 1.0.0
 */
public class IOConnection implements IOCallback {

    /**
     * Debug LOGGER
     */
    static final Logger LOGGER = Logger.getLogger("io.socket");

    static final boolean DEBUG = true;

    /**/
    public static final String FRAME_DELIMITER = "\ufffd";

    /**
     * The Constant STATE_INIT.
     */
    private static final int STATE_INIT = 0;

    /**
     * The Constant STATE_HANDSHAKE.
     */
    private static final int STATE_HANDSHAKE = 1;

    /**
     * The Constant STATE_CONNECTING.
     */
    private static final int STATE_CONNECTING = 2;

    /**
     * The Constant STATE_READY.
     */
    private static final int STATE_READY = 3;

    /**
     * The Constant STATE_INTERRUPTED.
     */
    private static final int STATE_INTERRUPTED = 4;

    /**
     * The Constant STATE_INVALID.
     */
    private static final int STATE_INVALID = 6;

    /**
     * The mState.
     */
    private int mState = STATE_INIT;

    /**
     * Socket.io path.
     */
    public static final String SOCKET_IO_1 = "/socket.io/1/";

    /**
     * The SSL socket factory for HTTPS connections
     */
    private static SSLContext sslContext = null;

    /**
     * All available connections.
     */
    private static HashMap<String, List<IOConnection>> connections = new HashMap<String, List<IOConnection>>();

    /**
     * The mUrl for this connection.
     */
    private URL mUrl;

    /**
     * The mTransport for this connection.
     */
    private IOTransport mTransport;

    /**
     * The connection timeout.
     */
    private int mConnectTimeout = 100000;

    /**
     * The session id of this connection.
     */
    private String mSessionId;

    /**
     * The heartbeat timeout. Set by the server
     */
    private long mHeartbeatTimeout;

    /**
     * The closing timeout. Set By the server
     */
    private long mClosingTimeout;

    /**
     * The mProtocols supported by the server.
     */
    private List<String> mProtocols;

    /**
     * The output buffer used to cache messages while (re-)connecting.
     */
    private ConcurrentLinkedQueue<String> mOutputBuffer = new ConcurrentLinkedQueue<String>();

    /**
     * The mSockets of this connection.
     */
    private HashMap<String, SocketIO> mSockets = new HashMap<String, SocketIO>();

    /**
     * Custom Request mHeaders used while handshaking
     */
    private Properties mHeaders;

    /**
     * The first socket to be connected. the socket.io server does not send a
     * connected response to this one.
     */
    private SocketIO mFirstSocket = null;

    /**
     * The reconnect timer. IOConnect waits a second before trying to reconnect
     */
    final private Timer mBackgroundTimer = new Timer("mBackgroundTimer");

    /**
     * A String representation of {@link #mUrl}.
     */
    private String mUrlStr;

    /**
     * The last occurred exception, which will be given to the user if
     * IOConnection gives up.
     */
    private Exception mLastException;

    /**
     * The next ID to use.
     */
    private int mNextId = 1;

    /**
     * Acknowledges.
     */
    private HashMap<Integer, IOAcknowledge> mAcknowledge = new HashMap<Integer, IOAcknowledge>();

    /**
     * true if there's already a keepalive in {@link #mOutputBuffer}.
     */
    private boolean mKeepAliveInQueue;

    /**
     * The heartbeat timeout task. Only null before connection has been
     * initialised.
     */
    private HeartbeatTimeoutTask mHeartbeatTimeoutTask;
    private SafeHearbeatTask mSafeHeartbeatTask;

    private static final int TIME_DELAY = 1000;

    /**
     * The Class HeartbeatTimeoutTask. Handles dropping this IOConnection if no
     * heartbeat is received within life time.
     */
    private class HeartbeatTimeoutTask extends TimerTask {
        @Override
        public void run() {
//            error(new SocketIOException(
//                    "Timeout Error. No heartbeat from server within life time of the socket. closing.",
//                    mLastException));
        	if (getState() != STATE_HANDSHAKE && getState() != STATE_CONNECTING) {
        		  setState(STATE_INTERRUPTED);
        	      handshake();
        	      reconnect();
        	}

        }
    }
    
    /**
     * The Class HeartbeatTimeoutTask. Handles dropping this IOConnection if no
     * heartbeat is received within life time.
     */
    private class SafeHearbeatTask extends TimerTask {

        /*
         * (non-Javadoc)
         *
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            sendPlain("2::");
        }
    }

    /**
     * The reconnect task. Null if no reconnection is in progress.
     */
    private ReconnectTask mReconnectTask = null;

    /**
     * The Class ReconnectTask. Handles reconnect attempts
     */
    private class ReconnectTask extends TimerTask {

        @Override
        public void run() {
            connectTransport();
            if (!mKeepAliveInQueue) {
                sendPlain("2::");
                mKeepAliveInQueue = true;
            }
        }
    }

    /**
     * The Class ConnectThread. Handles connecting to the server with an
     * {@link show.we.lib.websocket.IOTransport}
     */
    private class ConnectThread extends Thread {
        /**
         * Instantiates a new thread for handshaking/connecting.
         */
        public ConnectThread() {
            super("ConnectThread");
        }

        /**
         * Tries handshaking if necessary and connects with corresponding
         * mTransport afterwards.
         */
        @Override
        public void run() {
            if (IOConnection.this.getState() == STATE_INIT) {

                handshake();
            }
            connectTransport();
        }

    };

    /**
     * Set the socket factory used for SSL connections.
     *
     * @param context context
     */
    public static void setSslContext(SSLContext context) {
        IOConnection.sslContext = context;
    }

    /**
     * Get the socket factory used for SSL connections.
     *
     * @return socketFactory
     */
    public static SSLContext getSslContext() {
        return sslContext;
    }

    /**
     * Creates a new connection or returns the corresponding one.
     *
     * @param origin the origin
     * @param socket the socket
     * @return a IOConnection object
     */
    static public IOConnection register(String origin, SocketIO socket) {

    	 
        List<IOConnection> list = connections.get(origin);
        if (list == null) {
            list = new LinkedList<IOConnection>();
            connections.put(origin, list);
        } else {
            synchronized (list) {
                for (IOConnection connection : list) {
                    if (connection.register(socket)) {
                        return connection;
                    }
                }
            }
        }

        IOConnection connection = new IOConnection(origin, socket);
        list.add(connection);
    	 
        return connection;
    }

    /**
     * Connects a socket to the IOConnection.
     *
     * @param socket the socket to be connected
     * @return true, if successfully registered on this mTransport, otherwise
     * false.
     */
    public synchronized boolean register(SocketIO socket) {
        String namespace = socket.getNamespace();
        if (mSockets.containsKey(namespace)) {
            return false;
        }
        mSockets.put(namespace, socket);
        socket.setHeaders(mHeaders);
        IOMessage connect = new IOMessage(IOMessage.TYPE_CONNECT,
                socket.getNamespace(), "");
        sendPlain(connect.toString());
        return true;
    }

    /**
     * Disconnect a socket from the IOConnection. Shuts down this IOConnection
     * if no further connections are available for this IOConnection.
     *
     * @param socket the socket to be shut down
     */
    public synchronized void unregister(SocketIO socket) {
        sendPlain("0::" + socket.getNamespace());
        mSockets.remove(socket.getNamespace());
        socket.getCallback().onDisconnect();

        if (mSockets.size() == 0) {
            cleanup();
        }
    }

    /**
     * Handshake.
     */
    private void handshake() {
        URL url;
        String response;
        URLConnection connection;
        try {
            setState(STATE_HANDSHAKE);
            StringBuilder sb=new StringBuilder("?");
            Enumeration enu=mHeaders.propertyNames();
            while (enu.hasMoreElements()) {
				String key=(String)enu.nextElement();
				String value=(String)mHeaders.get(key);
				sb.append("&").append(key).append("=").append(value);
				
			}
            url = new URL(IOConnection.this.mUrl.toString() + SOCKET_IO_1+sb.toString());

            connection = url.openConnection();
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection)
                        .setSSLSocketFactory(sslContext.getSocketFactory());
            }
            connection.setConnectTimeout(mConnectTimeout);
            connection.setReadTimeout(mConnectTimeout);

			/* Setting the request mHeaders */
            for (Entry<Object, Object> entry : mHeaders.entrySet()) {
                connection.setRequestProperty((String) entry.getKey(),
                        (String) entry.getValue());
                
            }
            connection.setRequestProperty("Connection",
                    "Close");
            InputStream stream = connection.getInputStream();
            Scanner in = new Scanner(stream);
            response = in.nextLine();
            String[] data = response.split(":");
            mSessionId = data[0];
            mHeartbeatTimeout = Long.parseLong(data[1]) * TIME_DELAY;
            mClosingTimeout = Long.parseLong(data[2]) * TIME_DELAY;
            mProtocols = Arrays.asList(data[3].split(","));
            in.close();
            stream.close();
        } catch (Exception e) {
        	e.printStackTrace();
            error(new SocketIOException("Error while handshaking", e));

        }
    }

    /**
     * Connect mTransport.
     */
    private synchronized void connectTransport() {
        if (getState() == STATE_INVALID) {
            return;
        }
        setState(STATE_CONNECTING);
        if (mProtocols.contains(WebsocketTransport.TRANSPORT_NAME)) {
            mTransport = WebsocketTransport.create(mUrl, this);
        } else if (mProtocols.contains(XhrTransport.TRANSPORT_NAME)) {
            mTransport = XhrTransport.create(mUrl, this);
        } else {

            error(new SocketIOException(
                    "Server supports no available transports. You should reconfigure the server to support a available mTransport"));

            return;
        }
        mTransport.connect();

    }

    /**
     * Creates a new {@link show.we.lib.websocket.IOAcknowledge} instance which sends its arguments
     * back to the server.
     *
     * @param message the message
     * @return an {@link show.we.lib.websocket.IOAcknowledge} instance, may be <code>null</code> if
     * server doesn't request one.
     */
    private IOAcknowledge remoteAcknowledge(IOMessage message) {
        String msgId = message.getId();
        if (StringUtils.isEmpty(msgId)) {
            return null;
        } else if (!msgId.endsWith("+")) {
            msgId = msgId + "+";
        }
        final String id = msgId;
        final String endPoint = message.getEndpoint();
        return new IOAcknowledge() {
            @Override
            public void ack(Object... args) {
                JSONArray array = new JSONArray();
                for (Object o : args) {
                    try {
                        array.put(o == null ? JSONObject.NULL : o);
                    } catch (Exception e) {

                        error(new SocketIOException(
                                "You can only put values in IOAcknowledge.ack() which can be handled by JSONArray.put()",
                                e));

                    }
                }
                IOMessage ackMsg = new IOMessage(IOMessage.TYPE_ACK, endPoint,
                        id + array.toString());
                sendPlain(ackMsg.toString());
            }
        };
    }

    /**
     * adds an {@link show.we.lib.websocket.IOAcknowledge} to an {@link show.we.lib.websocket.IOMessage}.
     *
     * @param message the {@link show.we.lib.websocket.IOMessage}
     * @param ack     the {@link show.we.lib.websocket.IOAcknowledge}
     */
    private void synthesizeAck(IOMessage message, IOAcknowledge ack) {
        if (ack != null) {
            int id = mNextId++;
            mAcknowledge.put(id, ack);
            message.setId(id + "+");
        }
    }

    /**
     * Instantiates a new IOConnection.
     *
     * @param url    the URL
     * @param socket the socket
     */
    private IOConnection(String url, SocketIO socket) {
        try {
            this.mUrl = new URL(url);
            this.mUrlStr = url;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        mFirstSocket = socket;
        mHeaders = socket.getHeaders();
        mSockets.put(socket.getNamespace(), socket);
        new ConnectThread().start();
    }

    /**
     * Cleanup. IOConnection is not usable after this calling this.
     */
    private synchronized void cleanup() {
        setState(STATE_INVALID);
        if (mTransport != null) {
            mTransport.disconnect();
        }
        mSockets.clear();
        synchronized (connections) {
            List<IOConnection> con = connections.get(mUrlStr);
            if (con != null && con.size() > 1) {
                con.remove(this);
            } else {
                connections.remove(mUrlStr);
            }
            if (DEBUG) {
                LOGGER.info(mUrlStr);
            }

        }
        if (DEBUG) {
            LOGGER.info("Cleanup");
        }
        mBackgroundTimer.cancel();
    }

    /**
     * Populates an error to the connected {@link show.we.lib.websocket.IOCallback}s and shuts down.
     *
     * @param e an exception
     */
    private void error(SocketIOException e) {
        for (SocketIO socket : mSockets.values()) {

            socket.getCallback().onError(e);
            if (DEBUG) {
                LOGGER.info(e.getMessage().toString());
            }
        }

        cleanup();

    }

    /**
     * Sends a plain message to the {@link show.we.lib.websocket.IOTransport}.
     *
     * @param text the Text to be send.
     */
    private synchronized void sendPlain(String text) {
        if (getState() == STATE_READY) {
            try {
                if (DEBUG) {
                    LOGGER.info("> " + text);
                }
                mTransport.send(text);
            } catch (Exception e) {
                if (DEBUG) {
                    LOGGER.info("IOEx: saving");
                }

                mOutputBuffer.add(text);
            }
        } else {

            mOutputBuffer.add(text);
        }
    }

    /**
     * Invalidates an {@link show.we.lib.websocket.IOTransport}, used for forced reconnecting.
     */
    private void invalidateTransport() {
        if (mTransport != null) {
            mTransport.invalidate();
        }
        mTransport = null;
    }

    /**
     * Reset timeout.
     */
    private synchronized void resetTimeout() {
        if (mHeartbeatTimeoutTask != null) {
            mHeartbeatTimeoutTask.cancel();
        }
        if (getState() != STATE_INVALID) {
            mHeartbeatTimeoutTask = new HeartbeatTimeoutTask();
            mBackgroundTimer.schedule(mHeartbeatTimeoutTask, mClosingTimeout
                    + mHeartbeatTimeout);
        }
        if (mSafeHeartbeatTask != null) {
        	mSafeHeartbeatTask.cancel();
        }
        if (getState() != STATE_INVALID) {
        	mSafeHeartbeatTask = new SafeHearbeatTask();
            mBackgroundTimer.schedule(mSafeHeartbeatTask,
                    30000);
        }
    }

    /**
     * finds the corresponding callback object to an incoming message. Returns a
     * dummy callback if no corresponding callback can be found
     *
     * @param message the message
     * @return the iO callback
     * @throws show.we.lib.websocket.SocketIOException
     */
    private IOCallback findCallback(IOMessage message) throws SocketIOException {
        if (StringUtils.isEmpty(message.getEndpoint())) {
            return this;
        }
        SocketIO socket = mSockets.get(message.getEndpoint());
        if (socket == null) {
            throw new SocketIOException("Cannot find socket for '"
                    + message.getEndpoint() + "'");
        }
        return socket.getCallback();
    }

    /**
     * Transport connected.
     * <p/>
     * {@link show.we.lib.websocket.IOTransport} calls this when a connection is established.
     */
    public synchronized void transportConnected() {
        setState(STATE_READY);
        if (mReconnectTask != null) {
            mReconnectTask.cancel();
            mReconnectTask = null;
        }
        resetTimeout();
        if (mTransport.canSendBulk()) {
            ConcurrentLinkedQueue<String> outputBuffer = this.mOutputBuffer;
            this.mOutputBuffer = new ConcurrentLinkedQueue<String>();
            try {
                // DEBUG
                String[] texts = outputBuffer.toArray(new String[outputBuffer
                        .size()]);
                if (DEBUG) {
                    LOGGER.info("Bulk start:");
                    for (String text : texts) {
                        LOGGER.info("> " + text);
                    }
                    LOGGER.info("Bulk end");
                }
                // DEBUG END
                mTransport.sendBulk(texts);
            } catch (IOException e) {
                this.mOutputBuffer = outputBuffer;
            }
        } else {
            String text;
            while ((text = mOutputBuffer.poll()) != null) {
                sendPlain(text);
            }
        }
        this.mKeepAliveInQueue = false;
    }

    /**
     * Transport disconnected.
     * <p/>
     * {@link show.we.lib.websocket.IOTransport} calls this when a connection has been shut down.
     */
    public void transportDisconnected() {
        this.mLastException = null;
        if (getState() != STATE_INVALID) {
            handshake();
        }
        setState(STATE_INTERRUPTED);
        reconnect();
    }

    /**
     * Transport error.
     *
     * @param error the error {@link show.we.lib.websocket.IOTransport} calls this, when an exception
     *              has occurred and the mTransport is not usable anymore.
     */
    public void transportError(Exception error) {
        this.mLastException = error;
        setState(STATE_INTERRUPTED);
        reconnect();
    }

    /**
     * {@link show.we.lib.websocket.IOTransport} should call this function if it does not support
     * framing. If it does, transportMessage should be used
     *
     * @param text the text
     */
    public void transportData(String text) {
        if (!text.startsWith(FRAME_DELIMITER)) {
            transportMessage(text);
            return;
        }

        Iterator<String> fragments = Arrays.asList(text.split(FRAME_DELIMITER))
                .listIterator(1);
        while (fragments.hasNext()) {
            int length = Integer.parseInt(fragments.next());
            String string = (String) fragments.next();
            // Potential BUG: it is not defined if length is in bytes or
            // characters. Assuming characters.

            if (length != string.length()) {
            	System.out.println("SocketIOException:Garbage from server: " + text);
                error(new SocketIOException("Garbage from server: " + text));

                return;
            }

            transportMessage(string);
        }
    }

    /**
     * Transport message. {@link show.we.lib.websocket.IOTransport} calls this, when a message has
     * been received.
     *
     * @param text the text
     */
    public void transportMessage(String text) {
        if (DEBUG) {
            LOGGER.info("< " + text);
        }
        IOMessage message;
        try {
            message = new IOMessage(text);
        } catch (Exception e) {
            error(new SocketIOException("Garbage from server: " + text, e));
            return;
        }
        resetTimeout();
        switch (message.getType()) {
            case IOMessage.TYPE_DISCONNECT:
                try {
                    findCallback(message).onDisconnect();
                } catch (Exception e) {
                    error(new SocketIOException(
                            "Exception was thrown in onDisconnect()", e));

                }
                break;
            case IOMessage.TYPE_CONNECT:
                try {
                    if (mFirstSocket != null && "".equals(message.getEndpoint())) {
                        if (mFirstSocket.getNamespace().equals("")) {
                            mFirstSocket.getCallback().onConnect();
                        } else {
                            IOMessage connect = new IOMessage(
                                    IOMessage.TYPE_CONNECT,
                                    mFirstSocket.getNamespace(), "");
                            sendPlain(connect.toString());
                        }
                    } else {
                        findCallback(message).onConnect();
                    }
                    mFirstSocket = null;
                } catch (Exception e) {
                    error(new SocketIOException(
                            "Exception was thrown in onConnect()", e));

                }
                break;
            case IOMessage.TYPE_HEARTBEAT:
                sendPlain("2::");
                break;
            case IOMessage.TYPE_MESSAGE:
                try {
                    findCallback(message).onMessage(message.getData(),
                            remoteAcknowledge(message));
                } catch (Exception e) {
                    error(new SocketIOException(
                            "Exception was thrown in onMessage(String).\n"
                                    + "Message was: " + message.toString(), e));

                }
                break;
            case IOMessage.TYPE_JSON_MESSAGE:
                try {
                    JSONObject obj = null;
                    String data = message.getData();
                    if (!data.trim().equals("null")) {
                        obj = new JSONObject(data);
                    }
                    try {
                        findCallback(message).onMessage(obj,
                                remoteAcknowledge(message));
                    } catch (Exception e) {
                        error(new SocketIOException(
                                "Exception was thrown in onMessage(JSONObject).\n"
                                        + "Message was: " + message.toString(), e));

                    }
                } catch (JSONException e) {
                    LOGGER.warning("Malformated JSON received");
                }
                break;
            case IOMessage.TYPE_EVENT:
                try {
                    JSONObject event = new JSONObject(message.getData());
                    Object[] argsArray;
                    if (event.has("args")) {
                        JSONArray args = event.getJSONArray("args");
                        argsArray = new Object[args.length()];
                        for (int i = 0; i < args.length(); i++) {
                            if (!args.isNull(i)) {
                                argsArray[i] = args.get(i);
                            }
                        }
                    } else {
                        argsArray = new Object[0];
                    }
                    String eventName = event.getString("name");
                    try {
                        findCallback(message).on(eventName,
                                remoteAcknowledge(message), argsArray);
                    } catch (Exception e) {
                        error(new SocketIOException(
                                "Exception was thrown in on(String, JSONObject[]).\n"
                                        + "Message was: " + message.toString(), e));

                    }
                } catch (JSONException e) {
                    LOGGER.warning("Malformated JSON received");
                }
                break;

            case IOMessage.TYPE_ACK:
                String[] data = message.getData().split("\\+", 2);
                if (data.length == 2) {
                    try {
                        int id = Integer.parseInt(data[0]);
                        IOAcknowledge ack = mAcknowledge.get(id);
                        if (ack == null) {
                            LOGGER.warning("Received unknown ack packet");
                        } else {
                            JSONArray array = new JSONArray(data[1]);
                            Object[] args = new Object[array.length()];
                            for (int i = 0; i < args.length; i++) {
                                args[i] = array.get(i);
                            }
                            ack.ack(args);
                        }
                    } catch (NumberFormatException e) {
                        LOGGER.warning("Received malformated Acknowledge! This is potentially filling up the acknowledges!");
                    } catch (JSONException e) {
                        LOGGER.warning("Received malformated Acknowledge data!");
                    }
                } else if (data.length == 1) {
                    sendPlain("6:::" + data[0]);
                }
                break;
            case IOMessage.TYPE_ERROR:
                try {
                    findCallback(message).onError(
                            new SocketIOException("transportMessage: "
                                    + "\nstate = " + mState
                                    + "\ntext = " + text
                                    + "\ntype = " + message.getType()
                                    + "\nid = " + message.getId()
                                    + "\nendpoint = " + message.getEndpoint()
                                    + "\ndata = " + message.getData()));
                } catch (SocketIOException e) {
                    error(e);

                }
                if (message.getData().endsWith("+0")) {
                    // We are advised to disconnect
                    //cleanup();

					handshake();
                }
                break;
            case IOMessage.TYPE_NOOP:
                break;
            default:
                LOGGER.warning("Unkown type received" + message.getType());
                break;
        }
    }

    /**
     * forces a reconnect. This had become useful on some android devices which
     * do not shut down TCP-connections when switching from HSDPA to Wifi
     */
    public synchronized void reconnect() {
        if (getState() != STATE_INVALID) {
            invalidateTransport();
            setState(STATE_INTERRUPTED);
            if (mReconnectTask != null) {
                mReconnectTask.cancel();
            }
            mReconnectTask = new ReconnectTask();
            mBackgroundTimer.schedule(mReconnectTask, TIME_DELAY);
        }
    }

    /**
     * Returns the session id. This should be called from a {@link show.we.lib.websocket.IOTransport}
     *
     * @return the session id to connect to the right Session.
     */
    public String getSessionId() {
        return mSessionId;
    }

    /**
     * sends a String message from {@link show.we.lib.websocket.SocketIO} to the {@link show.we.lib.websocket.IOTransport}.
     *
     * @param socket the socket
     * @param ack    mAcknowledge package which can be called from the server
     * @param text   the text
     */
    public void send(SocketIO socket, IOAcknowledge ack, String text) {
        IOMessage message = new IOMessage(IOMessage.TYPE_MESSAGE,
                socket.getNamespace(), text);
        synthesizeAck(message, ack);
        sendPlain(message.toString());
    }

    /**
     * sends a JSON message from {@link show.we.lib.websocket.SocketIO} to the {@link show.we.lib.websocket.IOTransport}.
     *
     * @param socket the socket
     * @param ack    mAcknowledge package which can be called from the server
     * @param json   the json
     */
    public void send(SocketIO socket, IOAcknowledge ack, JSONObject json) {
        IOMessage message = new IOMessage(IOMessage.TYPE_JSON_MESSAGE,
                socket.getNamespace(), json.toString());
        synthesizeAck(message, ack);
        sendPlain(message.toString());
    }

    /**
     * emits an event from {@link show.we.lib.websocket.SocketIO} to the {@link show.we.lib.websocket.IOTransport}.
     *
     * @param socket the socket
     * @param event  the event
     * @param ack    mAcknowledge package which can be called from the server
     * @param args   the arguments to be send
     */
    public void emit(SocketIO socket, String event, IOAcknowledge ack,
                     Object... args) {
        try {
            JSONObject json = new JSONObject().put("name", event).put("args",
                    new JSONArray(Arrays.asList(args)));
            IOMessage message = new IOMessage(IOMessage.TYPE_EVENT,
                    socket.getNamespace(), json.toString());
            synthesizeAck(message, ack);
            sendPlain(message.toString());
        } catch (JSONException e) {
            error(new SocketIOException(
                    "Error while emitting an event. Make sure you only try to send arguments, which can be serialized into JSON."));
            

        }

    }

    /**
     * Checks if IOConnection is currently connected.
     *
     * @return true, if is connected
     */
    public boolean isConnected() {
        return getState() == STATE_READY;
    }

    /**
     * Gets the current mState of this IOConnection.
     *
     * @return current mState
     */
    private synchronized int getState() {
        return mState;
    }

    /**
     * Sets the current mState of this IOConnection.
     *
     * @param state the new state
     */
    private synchronized void setState(int state) {
        this.mState = state;
    }

    /**
     * gets the currently used mTransport.
     *
     * @return currently used mTransport
     */
    public IOTransport getTransport() {
        return mTransport;
    }

    @Override
    public void onDisconnect() {
        SocketIO socket = mSockets.get("");
        if (socket != null) {
            socket.getCallback().onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        SocketIO socket = mSockets.get("");
        if (socket != null) {
            socket.getCallback().onConnect();
        }
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
        for (SocketIO socket : mSockets.values()) {
            socket.getCallback().onMessage(data, ack);
        }
    }

    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
        for (SocketIO socket : mSockets.values()) {
            socket.getCallback().onMessage(json, ack);
        }
    }

    @Override
    public void on(String event, IOAcknowledge ack, Object... args) {
        for (SocketIO socket : mSockets.values()) {
            socket.getCallback().on(event, ack, args);
        }
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        for (SocketIO socket : mSockets.values()) {
            socket.getCallback().onError(socketIOException);
        }
    }
}
