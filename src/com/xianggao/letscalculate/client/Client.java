package com.xianggao.letscalculate.client;

import com.xianggao.letscalculate.commons.Constants;
import com.xianggao.letscalculate.commons.SocketUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Acesine on 1/4/16.
 */
public class Client {
    public static void main(String [] args) throws IOException {
        System.out.println("What's server address?");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String hostName = stdIn.readLine();
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(Constants.CLIENT_PORT_NUM);
            new Thread(new ServerHandler(socket)).start();
            SocketUtils.sendMessage(socket, "hello", InetAddress.getByName(hostName), Constants.SERVER_PORT_NUM);
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                SocketUtils.sendMessage(socket, userInput, InetAddress.getByName(hostName), Constants.SERVER_PORT_NUM);
            }
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server: " + hostName);
            System.exit(1);
        } finally {
            if (socket != null)
                SocketUtils.sendMessage(socket, "bye", InetAddress.getByName(hostName), Constants.SERVER_PORT_NUM);
        }
    }
}
