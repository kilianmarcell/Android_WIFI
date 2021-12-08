package com.example.wifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textViewInfo;

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.wifi_on:
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES) {
                        textViewInfo.setText("Nincs jogosultságod a wifi átállításához!");
                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        startActivityForResult(panelIntent, 0);
                    } else {
                        wifiManager.setWifiEnabled(true);
                        textViewInfo.setText("Wifi bekapcsolva!");
                    }
                    break;
                case R.id.wifi_off:
                    break;
                case R.id.wifi_info:
                    break;
            }
            return true;
        });
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        textViewInfo = findViewById(R.id.textViewInfo);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiInfo = wifiManager.getConnectionInfo();
    }
}