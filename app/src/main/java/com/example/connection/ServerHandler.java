package com.example.connection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.model.Game;
import com.example.shifumi.MainActivity;
import com.example.utils.Constants;

public class ServerHandler extends Handler {


    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        Bundle messageData = msg.getData();

        Object gameObject = messageData.getSerializable(Constants.DATA_KEY);

        if (gameObject instanceof Game) {
            if (MainActivity.game != null) {
                MainActivity.game = (Game) gameObject;
                //GameFragment.updatePlayerStatus();
                //GameFragment.updateTable();
                //sendToAll(gameObject);
            }
        }
    }
}
