package com.example.shifumi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.connection.WifiDirectBroadcastReceiver;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class Salon extends AppCompatActivity {

    private Button wifiButton;
    private Button discoverButton;
    private ListView deviceList;

    private WifiManager wifiManager;
    private WifiP2pManager wifiP2pManager;
    private Channel channel;

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private List<WifiP2pDevice> p2pDeviceList = new ArrayList<WifiP2pDevice>();
    private String[] deviceNameArray;
    private WifiP2pDevice[] deviceArray;

    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon);

        this.wifiButton = findViewById(R.id.wifi_id);
        this.discoverButton = findViewById(R.id.discover_id);
        this.deviceList = findViewById(R.id.liste_devices_id);

        initWifiManager();
        setListeners();
    }


    private void initWifiManager() {
        this.wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        this.wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = wifiP2pManager.initialize(this, getMainLooper(), null);

        this.broadcastReceiver = new WifiDirectBroadcastReceiver(this.wifiP2pManager, this.channel, this);
        this.intentFilter = new IntentFilter();

        this.intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        this.intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        this.intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        this.intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(this.broadcastReceiver, this.intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.broadcastReceiver);
    }


    private void setListeners() {
        this.wifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(getApplicationContext(), "wifi is disabled", LENGTH_SHORT).show();
                    wifiButton.setText("WIFI ON");
                } else {
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(getApplicationContext(), "wifi is enabled", LENGTH_SHORT).show();
                    wifiButton.setText("WIFI OFF");
                }
            }
        });

        this.discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(Salon.this, "Discovery started", LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(Salon.this, "Discovery failed", LENGTH_SHORT).show();
                    }
                });
            }
        });

        this.peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peerList) {
                if(!peerList.getDeviceList().equals(p2pDeviceList)) {
                    p2pDeviceList.clear();
                    p2pDeviceList.addAll(peerList.getDeviceList());

                    deviceNameArray = new String[peerList.getDeviceList().size()];
                    deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                    int index = 0;

                    for(WifiP2pDevice p2pDevice: peerList.getDeviceList()) {
                        deviceNameArray[index] = p2pDevice.deviceName;
                        deviceArray[index] = p2pDevice;
                        index++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_item_list, R.id.device_id, deviceNameArray);
                    deviceList.setAdapter(adapter);

                    if(p2pDeviceList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No Device found !", LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        };

        this.deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final WifiP2pDevice device = deviceArray[position];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Connected successfully to " + device.deviceName, LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(), "Connection failed to " + device.deviceName, LENGTH_SHORT).show();
                    }
                });
            }
        });

        this.connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                InetAddress groupOwnerAdress = info.groupOwnerAddress;

                if(info.groupFormed && info.isGroupOwner) {
                    Toast.makeText(getApplicationContext(), "you are the host !", LENGTH_SHORT).show();
                } else if(info.groupFormed) {
                    Toast.makeText(getApplicationContext(), "you are the client !", LENGTH_SHORT).show();
                }
            }
        };
    }

    public WifiP2pManager.PeerListListener getPeerListListener() {
        return peerListListener;
    }

    public WifiP2pManager.ConnectionInfoListener getConnectionInfoListener() {
        return connectionInfoListener;
    }
}
