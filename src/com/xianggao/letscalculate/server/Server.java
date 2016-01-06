package com.xianggao.letscalculate.server;

import com.xianggao.letscalculate.commons.Constants;
import com.xianggao.letscalculate.commons.GameResponse;
import com.xianggao.letscalculate.commons.SocketUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Acesine on 1/4/16.
 */
public class Server implements Runnable{
    private ServerOchestrator ochestrator;
    private final ProblemGenerator problemGenerator;
    private Set<ClientInfo> clients;
    private ServerState state;
    private Problem problem;
    private Set<String> confirmedUser;

    public Server() {
        this.state = ServerState.WAITING_FOR_CONNECTION;
        this.clients = new HashSet<>();
        this.confirmedUser = new HashSet<>();
        this.problemGenerator = new ProblemGenerator();
        this.ochestrator = new ServerOchestrator(this);
    }

    public static void main(String [] args) {
        new Thread(new Server()).start();
    }

    public ServerState getState() {
        return state;
    }

    public int getClientsNum() {
        return clients.size();
    }

    public void addClient(ClientInfo client) {
        this.clients.add(client);
    }

    public void setServerState(ServerState state) {
        this.state = state;
    }

    public Problem getProblem() {
        return this.problem;
    }

    public void addConfirmedUser(String userName) {
        confirmedUser.add(userName);
    }

    public boolean isConfirmed() {
        return confirmedUser.size() == clients.size();
    }

    @Override
    public void run() {
        try (
                DatagramSocket socket = new DatagramSocket(Constants.SERVER_PORT_NUM);
        ) {
            while(true) {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                ochestrator.handle(socket, packet);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(DatagramSocket socket, String msg) throws IOException {
        for (ClientInfo client : clients) {
            SocketUtils.sendMessage(socket, msg, client.getAddress(), client.getPort());
        }
    }

    public void postProblem(DatagramSocket socket) throws IOException {
        this.problem = problemGenerator.getProblem();
        broadcast(socket, problem.getProblem());
    }
}
