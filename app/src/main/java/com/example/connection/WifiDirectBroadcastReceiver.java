package com.example.connection;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shifumi.Salon;
import com.example.shifumi.acceuil;

import static android.net.wifi.p2p.WifiP2pManager.*;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private static final int PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION = 100;
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
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(salonActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(salonActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);
                    //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method

                }else{
                    wifiP2pManager.requestPeers(channel, salonActivity.getPeerListListener());                    //do something, permission was previously granted; or legacy device
                }
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
