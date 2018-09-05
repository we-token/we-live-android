/*
 * socket.io-java-client IOAcknowledge.java
 *
 * Copyright (c) 2012, Enno Boland
 * PROJECT DESCRIPTION
 * 
 * See LICENSE file for more information
 */
package show.we.lib.websocket;


/**
 * The Interface IOAcknowledge.
 *
 * @author ll
 * @version 1.0.0
 */
public interface IOAcknowledge {

    /**
     * Acknowledges a socket.io message.
     *
     * @param args may be all types which can be serialized by {@link org.json.JSONArray#put(Object)}
     */
    void ack(Object... args);
}
