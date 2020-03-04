package com.example.shifumi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;


public class Salon extends AppCompatActivity {

    private Button wifiButton;
    private Button discoverButton;
    private ListView deviceList;

    private WifiManager wifiManager;

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
    }
}
