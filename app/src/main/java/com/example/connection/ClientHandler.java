package com.example.connection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.model.Game;
import com.example.shifumi.MainActivity;
import com.example.utils.Constants;

public class ClientHandler extends Handler {

    Bundle messageData;

    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        messageData = msg.getData();
        Object clientObject = messageData.getSerializable(Constants.DATA_KEY);

        if (clientObject instanceof Game) {
            if (MainActivity.game != null) {
                Game game = (Game) clientObject;
                MainActivity.game = game;
                if(game.hasPlayerPlayed(0))
                    MainActivity.hideAnswer(0);
                GameFragment.updateTable();
            }
        }
    }


    public static void sendToServer(Object gameObject) {
        ClientSenderThread clientSenderThread = new ClientSenderThread(ClientConnectionThread.socket, gameObject);
        clientSenderThread.start();
    }
}
