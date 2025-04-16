package com.github.warren_bank.one_hop;

/*
 * https://github.com/weliem/blessed-android/blob/2.5.0/SERVER.md
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothPeripheralManager.java
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothPeripheralManagerCallback.java
 * https://github.com/weliem/blessed-android/raw/2.5.0/docs/index.html
 */

import com.github.warren_bank.one_hop.android_internals.AdvertiseHelper;

import com.welie.blessed.BluetoothPeripheralManager;

import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.os.Build;
import android.os.ParcelUuid;

public class BluetoothLowEnergyServer {

  private static BluetoothPeripheralManager peripheralManager;

  public static boolean doesSupportAdvertising() {
    return App.btAdapter.isMultipleAdvertisementSupported();
  }

  public static void initPeripheralManager(Context context) {
    if (!doesSupportAdvertising()) {
      Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Advertising (failed)" + "\nError: not supported by hardware");
      return;
    }
    if (peripheralManager != null) return;

    BluetoothManager mgr = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    peripheralManager = new BluetoothPeripheralManager(context, mgr, new BluetoothLowEnergyServerLogger());

    peripheralManager.setCentralManager(
      BluetoothLowEnergyClient.getCentralManager()
    );
    initService();
  }

  private static void initService() {
    BluetoothGattService service = new BluetoothGattService(
      Constants.getBleServiceUUID(),
      BluetoothGattService.SERVICE_TYPE_PRIMARY
    );

    peripheralManager.add(service);
  }

  public static void startAdvertising() {
    if (!doesSupportAdvertising()) return;
    if (peripheralManager == null) return;

    AdvertiseSettings.Builder asBuilder;

    asBuilder = new AdvertiseSettings.Builder()
      .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
      .setConnectable(true)
      .setTimeout(0)
      .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM);

    if (Build.VERSION.SDK_INT >= 34) {
      asBuilder.setDiscoverable(true);
    }

    AdvertiseSettings advertiseSettings = asBuilder.build();
    asBuilder = null;

    // =========================================================================
    // legacy   BLE 4: total size must be <=  31 bytes
    // extended BLE 5: total size must be <= 254 bytes (Android 8+)
    //   https://novelbits.io/maximum-data-bluetooth-advertising-packet-ble/
    //   https://source.android.com/docs/core/connect/bluetooth/ble_advertising
    // =========================================================================
    // legacy:
    // =========================================================================
    AdvertiseData advertiseData = new AdvertiseData.Builder()
      .setIncludeDeviceName(false)
      .setIncludeTxPowerLevel(false)
      .addServiceUuid(new ParcelUuid(
        Constants.getBleServiceUUID()
      ))
      .build();

    AdvertiseData scanResponse = new AdvertiseData.Builder()
      .setIncludeDeviceName(false)
      .setIncludeTxPowerLevel(false)
      .build();

    String deviceName        = App.btAdapter.getName();
    byte[] advDataBytes      = AdvertiseHelper.advertiseDataToBytes(advertiseData, deviceName);
    byte[] scanResponseBytes = AdvertiseHelper.advertiseDataToBytes(scanResponse,  deviceName);
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Advertising (started)" + "\nData: " + advDataBytes.length + " bytes" + "\nScan Response: " + scanResponseBytes.length + " bytes");

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
