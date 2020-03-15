package com.example.connection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.model.Game;
import com.example.shifumi.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendReceive extends Thread {

    private int playerId;
    private Game game;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private byte[] buffer = new byte[8];

    //private MainActivity mainActivity;

    public SendReceive(Socket socket, Game game, int playerId) {
        this.socket = socket;
        this.game = game;
        this.playerId = playerId;

        try {
            this.inputStream = socket.getInputStream();
            this.outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        buffer = new byte[8];
        int bytes;

        while(socket != null) {
            try {
                bytes = inputStream.read(buffer);
                if(!game.hasPlayerPlayed(playerId)) {
                    if(bytes > 0) {
                        obtainMessage(bytes, buffer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(byte[] bytes) throws IOException {
        //outputStream.write(bytes);
    }

    public void obtainMessage(int bytes, byte[] buffer) {
        MainActivity.obtainMessage(bytes, buffer);
    }

}
