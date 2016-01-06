package com.xianggao.letscalculate.commons;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Acesine on 1/5/16.
 */
public class SocketUtils {
    private SocketUtils() {}

    public static void sendMessage(DatagramSocket socket, String message, InetAddress address, int port) throws IOException {
        if (message == null) return;
        byte [] bytes = message.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        socket.send(packet);
    }
}
