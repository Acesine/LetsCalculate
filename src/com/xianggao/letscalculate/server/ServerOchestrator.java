package com.xianggao.letscalculate.server;

import com.xianggao.letscalculate.commons.Constants;
import com.xianggao.letscalculate.commons.SocketUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Acesine on 1/5/16.
 */
public class ServerOchestrator {
    private final Server server;

    public ServerOchestrator(final Server server) {
        this.server = server;
    }

    public void handle(DatagramSocket socket, DatagramPacket request) throws IOException {
        ServerState state = server.getState();
        switch (state) {
            case WAITING_FOR_CONNECTION:
                handleWaitForConnection(socket, request);
                break;
            case WAITING_FOR_CONFIRMATION:
                handleWaitForConfirmation(socket, request);
                break;
            case PROBLEM_POSTED:
                handleProblemPosted(socket, request);
                break;
        }
    }

    private void handleWaitForConnection(DatagramSocket socket, DatagramPacket request) throws IOException {
        String data = new String(request.getData(), 0, request.getLength());
        String ret;
        if (Constants.HELLO.equals(data)) {
            server.addClient(new ClientInfo(request.getAddress(), request.getPort()));
            if (server.getClientsNum() == Constants.PLAYER_NUM) {
                server.setServerState(ServerState.WAITING_FOR_CONFIRMATION);
                server.broadcast(socket, "Please reply \"ready\"");
                ret = null;
            } else {
                ret = "Waiting for another " + (Constants.PLAYER_NUM - server.getClientsNum()) + " players...";
            }
        } else {
            ret = "Invalid message!";
        }
        SocketUtils.sendMessage(socket, ret, request.getAddress(), request.getPort());
    }

    private void handleWaitForConfirmation(DatagramSocket socket, DatagramPacket request) throws IOException {
        String data = new String(request.getData(), 0, request.getLength());
        String ret;
        if (Constants.READY.equals(data)) {
            server.addConfirmedUser(request.getAddress().toString()+request.getPort());
            if (server.isConfirmed()) {
                server.setServerState(ServerState.PROBLEM_POSTED);
                server.postProblem(socket);
            }
            ret = null;
        } else {
            ret = "Please reply \"ready\"";
        }
        SocketUtils.sendMessage(socket, ret, request.getAddress(), request.getPort());
    }

    private void handleProblemPosted(DatagramSocket socket, DatagramPacket request) throws IOException {
        String data = new String(request.getData(), 0, request.getLength());
        String ret;
        int r;
        try {
            String tmp = data;

            r = Evaluator.eval(data);
            if (r == Constants.TARGET) {
                server.setServerState(ServerState.WAITING_FOR_CONFIRMATION);
                server.broadcast(socket, "Congratulate " + request.getAddress().toString()+request.getPort() + "! \nAnswer is: " + data);
                server.broadcast(socket, "Game starts...\nPlease respond \"ready\"");
                ret = null;
            } else {
                ret = "Wrong!";
            }
        } catch (HandlationException e) {
            ret = "Invalid format!";
        }
        SocketUtils.sendMessage(socket, ret, request.getAddress(), request.getPort());
    }
}
