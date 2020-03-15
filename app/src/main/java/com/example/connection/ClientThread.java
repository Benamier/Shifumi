package com.example.connection;

import android.util.Log;

import com.example.model.Game;
import com.example.shifumi.MainActivity;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {

    private static final String TAG = "CLIENT";
    private static final int SOCKET_CLIENT_PORT = 8889;
    private Game game;
    private Socket socket;
    //private ServerSocket clientSocket;

    private String hostAddress;
    private int dstPort = 8888;

    //private MainActivity mainActivity;
    private SendReceive sendReceiveThread;


    public ClientThread(InetAddress hostAddress, Game game) {
        this.hostAddress = hostAddress.getHostAddress();
        this.socket = new Socket();
        this.game = game;
    }


    @Override
    public void run() {
        try {
            socket.connect(new InetSocketAddress(hostAddress, 8887));
            sendReceiveThread = new SendReceive(socket, game, 1);
            sendReceiveThread.start();
            Log.i(TAG, "connect client -> serveur et socket client vaut" + socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(byte[] bytes) {
        /*try {
            sendReceiveThread.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}
