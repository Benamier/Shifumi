package com.example.connection;

import android.util.Log;

import com.example.model.Game;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class GameServeur extends Thread {

    private static final String TAG = "GameServer : ";
    private static final int SOCKET_GAME_PORT = 8888;
    private ServerSocket serverSocket;
    private InetAddress hostAddress;

    public static HashMap<Socket, String> socketUserMap = new HashMap();

    boolean serverStarted = false;

    private Game game;

    public GameServeur(Game game, InetAddress hostAddress) {
        this.game = game;
        this.hostAddress = hostAddress;
        try {
            serverSocket = new ServerSocket(SOCKET_GAME_PORT, 3, hostAddress);
            serverSocket.setReuseAddress(true);
            serverStarted = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        if(serverSocket != null) {
            try {
                Socket socket = serverSocket.accept();
                //String str = socket.getPort() == 8887 ? "serveur" : "client";
                Log.i(TAG, "socket venant de " + socket);
                socketUserMap.put(socket, null);
                Log.i(TAG, "nombre de joueurs connectés size vaut :" + socketUserMap.size());
                if(socketUserMap.size() == 2) {
                    Log.i(TAG, "nombre de joueurs connectés vaut 2 et size vaut :" + socketUserMap.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static HashMap<Socket, String> getSocketUserMap() {
        return socketUserMap;
    }
}
