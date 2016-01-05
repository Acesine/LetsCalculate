package com.xianggao.letscalculate.server;

import com.xianggao.letscalculate.commons.Constants;
import com.xianggao.letscalculate.commons.GameRequest;

import java.io.IOException;

import static com.xianggao.letscalculate.server.ServerState.WAITING_FOR_CONNECTION;

/**
 * Created by Acesine on 1/5/16.
 */
public class ServerOchestrator {
    private final Server server;

    public ServerOchestrator(final Server server) {
        this.server = server;
    }

    public void handle(GameRequest request) {
        ServerState state = server.getState();
        switch (state) {
            case WAITING_FOR_CONNECTION:
                break;
            default:
                throw new HandlationException(ErrorCode.INTERNAL_FAILURE);
        }
    }

    private void handleWaitForConnection() throws IOException {
        if (server.getClientsNum() < Constants.PLAYER_NUM) {
            server.broadcast("Waiting for another"+(Constants.PLAYER_NUM-server.getClientsNum())+" players...");
        } else {
            server.setServerState(ServerState.);
        }
    }
}
