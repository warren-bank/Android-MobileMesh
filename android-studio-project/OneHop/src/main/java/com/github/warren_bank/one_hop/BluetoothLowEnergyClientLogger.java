package com.github.warren_bank.one_hop;

/*
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothCentralManagerCallback.java
 */

import com.welie.blessed.BluetoothCentralManagerCallback;
import com.welie.blessed.BluetoothPeripheral;
import com.welie.blessed.HciStatus;
import com.welie.blessed.ScanFailure;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;

public class BluetoothLowEnergyClientLogger extends BluetoothCentralManagerCallback {
  @Override
  public void onConnectingPeripheral(BluetoothPeripheral peripheral) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Peripheral connection (started)" + "\nMAC: " + peripheral.getAddress());
    super.onConnectingPeripheral(peripheral);
  }

  @Override
  public void onConnectedPeripheral(BluetoothPeripheral peripheral) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Peripheral connection (success)" + "\nMAC: " + peripheral.getAddress());
    super.onConnectedPeripheral(peripheral);
  }

  @Override
  public void onConnectionFailed(BluetoothPeripheral peripheral, HciStatus status) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Peripheral connection (failed)" + "\nCause: " + status.name() + "\nMAC: " + peripheral.getAddress());
    super.onConnectionFailed(peripheral, status);
  }

  @Override
  public void onDisconnectingPeripheral(BluetoothPeripheral peripheral) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Peripheral disconnection (started)" + "\nMAC: " + peripheral.getAddress());
    super.onDisconnectingPeripheral(peripheral);
  }

  @Override
  public void onDisconnectedPeripheral(BluetoothPeripheral peripheral, HciStatus status) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Peripheral connection (complete)" + "\nCause: " + status.name() + "\nMAC: " + peripheral.getAddress());
    super.onDisconnectedPeripheral(peripheral, status);
  }

  @Override
  public void onDiscoveredPeripheral(BluetoothPeripheral peripheral, ScanResult scanResult) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Peripheral discovered" + "\nMAC: " + peripheral.getAddress());
    super.onDiscoveredPeripheral(peripheral, scanResult);
  }

  @Override
  public void onScanFailed(ScanFailure scanFailure) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Scan (failed)" + "\nCause: " + scanFailure.name());
    super.onScanFailed(scanFailure);
  }

  @Override
  public void onBluetoothAdapterStateChanged(int state) {
    // https://developer.android.com/reference/android/bluetooth/BluetoothAdapter#EXTRA_STATE
    String new_state = "";
    switch(state) {
      case BluetoothAdapter.STATE_TURNING_ON:
        new_state = "TURNING_ON";
        break;
      case BluetoothAdapter.STATE_ON:
        new_state = "ON";
        break;
      case BluetoothAdapter.STATE_TURNING_OFF:
        new_state = "TURNING_OFF";
        break;
      case BluetoothAdapter.STATE_OFF:
        new_state = "OFF";
        break;
    }

    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyClient: Adapter state changed" + "\nNow: " + new_state);
    super.onBluetoothAdapterStateChanged(state);
  }
}
