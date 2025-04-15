package com.github.warren_bank.one_hop;

/*
 * https://github.com/weliem/blessed-android/blob/2.5.0/SERVER.md
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothPeripheralManager.java
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothPeripheralManagerCallback.java
 * https://github.com/weliem/blessed-android/raw/2.5.0/docs/index.html
 */

import com.welie.blessed.BluetoothPeripheralManager;
import com.welie.blessed.BluetoothPeripheralManagerCallback;

import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.os.ParcelUuid;

public class BluetoothLowEnergyServer {

  private static BluetoothPeripheralManager peripheralManager;

  public static boolean doesSupportAdvertising() {
    return App.btAdapter.isMultipleAdvertisementSupported();
  }

  public static void initPeripheralManager(Context context) {
    if (!doesSupportAdvertising()) return;
    if (peripheralManager != null) return;

    BluetoothManager mgr = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    BluetoothPeripheralManagerCallback cb = new BluetoothPeripheralManagerCallback(){};

    peripheralManager = new BluetoothPeripheralManager(context, mgr, cb);
    peripheralManager.setCentralManager(
      BluetoothLowEnergyClient.getCentralManager()
    );
    peripheralManager.add(
      new BluetoothGattService(
        Constants.getBleServiceUUID(),
        BluetoothGattService.SERVICE_TYPE_PRIMARY
      )
    );
  }

  public static void startAdvertising() {
    if (!doesSupportAdvertising()) return;
    if (peripheralManager == null) return;

    AdvertiseSettings advertiseSettings = new AdvertiseSettings.Builder()
      .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
      .setConnectable(false)
      .setTimeout(0)
      .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
      .build();

    AdvertiseData advertiseData = new AdvertiseData.Builder()
      .setIncludeTxPowerLevel(true)
      .addServiceUuid(new ParcelUuid(
        Constants.getBleServiceUUID()
      ))
      .build();

    AdvertiseData scanResponse = new AdvertiseData.Builder()
      .setIncludeDeviceName(true)
      .build();

    peripheralManager.startAdvertising(advertiseSettings, advertiseData, scanResponse);
  }

  public static void stopAdvertising() {
    if (!doesSupportAdvertising()) return;
    if (peripheralManager == null) return;

    peripheralManager.stopAdvertising();
  }

  public static void close() {
    if (!doesSupportAdvertising()) return;
    if (peripheralManager == null) return;

    peripheralManager.close();
    peripheralManager = null;
  }

}
