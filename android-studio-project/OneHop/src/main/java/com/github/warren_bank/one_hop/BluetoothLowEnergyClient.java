package com.github.warren_bank.one_hop;

/*
 * https://github.com/weliem/blessed-android/blob/2.5.0/README.md
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothCentralManager.java
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothCentralManagerCallback.java
 * https://github.com/weliem/blessed-android/raw/2.5.0/docs/index.html
 */

import com.welie.blessed.BluetoothCentralManager;
import com.welie.blessed.BluetoothCentralManagerCallback;
import com.welie.blessed.BluetoothPeripheral;

import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Handler;

public class BluetoothLowEnergyClient {

  private static BluetoothCentralManager centralManager;

  public static void initCentralManager(Context context) {
    if (centralManager != null) return;

    BluetoothCentralManagerCallback cb = (BluetoothCentralManagerCallback) new CustomBluetoothCentralManagerCallback();
    Handler handler = new Handler();

    centralManager = new BluetoothCentralManager(context, cb, handler);
    centralManager.disableLogging();
  }

  public static BluetoothCentralManager getCentralManager() {
    return centralManager;
  }

  public static void startScanning() {
    if (centralManager == null) return;

    centralManager.scanForPeripheralsWithServices(
      Constants.getBleServiceUUIDs()
    );
  }

  public static void stopScanning() {
    if (centralManager == null) return;

    centralManager.stopScan();
  }

  public static void close() {
    if (centralManager == null) return;

    centralManager.close();
    centralManager = null;
  }

  // ---------------------------------------------------------------------------
  // customization

  private static class CustomBluetoothCentralManagerCallback extends BluetoothCentralManagerCallback {
    @Override
    public void onDiscoveredPeripheral(BluetoothPeripheral peripheral, ScanResult scanResult) {
      String macAddress = peripheral.getAddress();

      Utils.addEdge(macAddress);

      if (PrefsMgr.log_ble_peripheral_discovery) {
        String name    = peripheral.getName();
        String message = "BluetoothLowEnergyClient: Discovered Peripheral";

        if ((name != null) && !name.isEmpty())
          message += "\n" + name;

        Utils.addLogMessage(macAddress, message);
      }
    }
  }

}
