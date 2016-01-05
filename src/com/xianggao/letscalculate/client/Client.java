package com.xianggao.letscalculate.client;

import com.xianggao.letscalculate.commons.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Acesine on 1/4/16.
 */
public class Client {
    public static void main(String [] args) throws IOException {
        System.out.println("What's server address?");
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String hostName = stdIn.readLine();
        System.out.println("What's your name?");
        String clientName = stdIn.readLine();
        try (
                Socket clientSocket = new Socket(hostName, Constants.PORT_NUM);
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("[SERVER]: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server: " + hostName);
            System.exit(1);
        }
    }
}
