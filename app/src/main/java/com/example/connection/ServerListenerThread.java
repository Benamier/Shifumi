package com.example.connection;

import android.os.Bundle;
import android.os.Message;

import com.example.model.Game;
import com.example.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private Socket hostThreadSocket;

    ServerListenerThread(Socket soc) {
        hostThreadSocket = soc;
    }

    @Override
    public void run() {
        while (true) {
            ObjectInputStream objectInputStream;
            try {
                InputStream inputStream = null;
                inputStream = hostThreadSocket.getInputStream();
                objectInputStream = new ObjectInputStream(inputStream);
                Object gameObject;
                Bundle data = new Bundle();
                gameObject = objectInputStream.readObject();
                if (gameObject != null) {
                    /*if (gameObject instanceof PlayerInfo) {
                        //data.putSerializable(Constants.DATA_KEY, (PlayerInfo) gameObject);
                        //data.putInt(Constants.ACTION_KEY, Constants.PLAYER_LIST_UPDATE);
                        //ServerConnectionThread.socketUserMap.put(hostThreadSocket, ((PlayerInfo) gameObject).username);
                    } else {
                        //data.putSerializable(Constants.DATA_KEY, (Game) gameObject);
                    }*/
                    Message msg = new Message();
                    msg.setData(data);
                    //HostFragment.serverHandler.sendMessage(msg);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
