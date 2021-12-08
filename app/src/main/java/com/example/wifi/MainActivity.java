package com.example.wifi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.Formatter;
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
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        textViewInfo.setText("Nincs jogosultságod a wifi átállításához!");
                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        startActivityForResult(panelIntent, 0);
                    } else {
                        wifiManager.setWifiEnabled(true);
                        textViewInfo.setText("Wifi bekapcsolva!");
                    }
                    break;
                case R.id.wifi_off:
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                        textViewInfo.setText("Nincs jogosultságod a wifi átállításához!");
                        Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                        startActivityForResult(panelIntent, 0);
                    } else {
                        wifiManager.setWifiEnabled(false);
                        textViewInfo.setText("Wifi kikapcsolva!");
                    }
                    break;
                case R.id.wifi_info:
                    ConnectivityManager connectivityManager = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo =
                            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    if (networkInfo.isConnected()) {
                        int ip_number = wifiInfo.getIpAddress();
                        String ipConverted = Formatter.formatIpAddress(ip_number);
                        textViewInfo.setText("IP cím: " + ipConverted);
                    } else {
                        textViewInfo.setText("Nem csatlakoztál fel a wifire!");
                    }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED ||
                    wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
                textViewInfo.setText("Wifi bekapcsolva");
            }
        } else if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLED ||
                    wifiManager.getWifiState() == WifiManager.WIFI_STATE_DISABLING) {
            textViewInfo.setText("Wifi kikapcsolva");
        }
    }
}