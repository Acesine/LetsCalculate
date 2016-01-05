package com.xianggao.letscalculate.server;

import com.xianggao.letscalculate.commons.Constants;
import com.xianggao.letscalculate.commons.GameRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Acesine on 1/4/16.
 */
public class Server implements Runnable{
    private ServerOchestrator ochestrator;
    private final ProblemGenerator problemGenerator;
    private Map<String, Socket> clients;
    private ServerState state;
    private Problem problem;
    private

    public Server() {
        this.state = ServerState.WAITING_FOR_CONNECTION;
        this.clients = new HashMap<>();
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
        return clients.entrySet().size();
    }

    public void setServerState(ServerState state) {
        this.state = state;
    }

    public Problem getProblem() {
        return this.problem;
    }

    @Override
    public void run() {
        // A simple server
        try (
                ServerSocket serverSocket = new ServerSocket(Constants.PORT_NUM);
        ) {
            // Main loop
            while(true) {
                Socket clientSocket = serverSocket.accept();
                if (clients.values().contains(clientSocket) || clients.keySet().size() < Constants.PLAYER_NUM) {
                    GameRequest request = parseRequest(clientSocket);
                    clients.put(request.getUserName(), clientSocket);
                } else {
                    sendResponse(clientSocket, Constants.SPILL_OVER_MESSAGE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GameRequest parseRequest(Socket clientSocket) throws IOException {
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        try {
            return (GameRequest)in.readObject();
        } catch (ClassNotFoundException e) {
            throw new HandlationException(ErrorCode.INVALID_FORMAT);
        } catch (IOException e) {
            throw new HandlationException(ErrorCode.INVALID_FORMAT);
        }
    }

    private void sendResponse(Socket clientSocket, String msg) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        out.write(msg);
    }

    public void broadcast(String msg) throws IOException {
        for (Socket socket : clients) {
            sendResponse(socket, msg);
        }
    }

    public synchronized void generateProblem() {
        this.problem = problemGenerator.getProblem();
    }
}
