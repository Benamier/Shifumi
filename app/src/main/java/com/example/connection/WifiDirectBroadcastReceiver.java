package com.example.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shifumi.Salon;

import static android.net.wifi.p2p.WifiP2pManager.*;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager wifiP2pManager;
    private Channel channel;
    private Salon salonActivity;


    public WifiDirectBroadcastReceiver(WifiP2pManager wifiP2pManager, Channel channel, Salon salonActivity) {
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.salonActivity = salonActivity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(wifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(EXTRA_WIFI_STATE, -1);

            if(state == WIFI_P2P_STATE_ENABLED) {
                Toast.makeText(context, "Wifi is on", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wifi is off", Toast.LENGTH_SHORT).show();
            }
        } else if(wifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if(wifiP2pManager != null) {
                wifiP2pManager.requestPeers(channel, salonActivity.getPeerListListener());
            }
        } else if(wifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if(wifiP2pManager == null)
                return;
            NetworkInfo networkInfo = intent.getParcelableExtra(wifiP2pManager.EXTRA_NETWORK_INFO);

            if(networkInfo.isConnected()) {
                wifiP2pManager.requestConnectionInfo(channel, salonActivity.getConnectionInfoListener());
            } else {
                Toast.makeText(salonActivity, "Device disconnected !", Toast.LENGTH_SHORT).show();
            }
        } else if(wifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
