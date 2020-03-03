package com.example.connection;

import com.example.model.Game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSenderThread extends Thread {

    private Socket hostThreadSocket;
    Object message;

    public ServerSenderThread(Socket socket, Object message) {
        hostThreadSocket = socket;
        this.message = message;
    }

    @Override
    public void run() {
        OutputStream outputStream;
        ObjectOutputStream objectOutputStream;

        try {
            outputStream = hostThreadSocket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(message);
            if (message instanceof Game) {
                //PlayerListFragment.gameObject = (Game) message;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
