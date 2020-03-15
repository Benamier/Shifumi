package com.example.connection;

import android.util.Log;

import com.example.model.Game;
import com.example.shifumi.MainActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurThread extends Thread {

    private static final String TAG = "SERVEUR";
    private static final int SOCKET_SERVEUR_PORT = 8887;
    private Game game;
    private ServerSocket serverSocket;
    private Socket socket;


    private InetAddress inetAddress;
    private SendReceive sendReceiveThread;


    public ServeurThread(Game game) {
        try {
            serverSocket = new ServerSocket(SOCKET_SERVEUR_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game = game;
    }


    @Override
    public void run() {
        try {
            socket = serverSocket.accept();
            sendReceiveThread = new SendReceive(socket, game, 0);
            sendReceiveThread.start();
            Log.i(TAG, "socket client vaut " + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void write(byte[] bytes) {
        /*try {
            //sendReceiveThread.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }


}
