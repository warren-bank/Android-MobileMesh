package com.github.warren_bank.one_hop;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
  public static BluetoothAdapter          btAdapter;
  public static String                    macAddress;
  public static List<String>              edges;
  public static Map<String, List<String>> logs;

  @Override
  public void onCreate() {
    super.onCreate();

    btAdapter = BluetoothAdapter.getDefaultAdapter();
    if (btAdapter == null) return;

    // https://developer.android.com/about/versions/marshmallow/android-6.0-changes.html#behavior-hardware-id
    macAddress = (Build.VERSION.SDK_INT < 23)
      ? btAdapter.getAddress()
      : Settings.Secure.getString(getContentResolver(), "bluetooth_address");

    edges = Utils.getObservableStringList();
    logs  = new HashMap<String, List<String>>();

    Utils.addEdge(Constants.LocalDevice);
    Utils.addLogMessage(Constants.LocalDevice, "Application: started");
  }
}
