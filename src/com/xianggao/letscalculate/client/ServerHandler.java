package com.xianggao.letscalculate.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Acesine on 1/5/16.
 */
public class ServerHandler implements Runnable{
    private final DatagramSocket socket;

    public ServerHandler(final DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true) {
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
                System.out.println(new String(packet.getData(), 0, packet.getLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
