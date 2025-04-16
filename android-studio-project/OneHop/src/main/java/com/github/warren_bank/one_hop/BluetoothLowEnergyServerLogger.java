package com.github.warren_bank.one_hop;

/*
 * https://github.com/weliem/blessed-android/blob/2.5.0/blessed/src/main/java/com/welie/blessed/BluetoothPeripheralManagerCallback.java
 */

import com.welie.blessed.AdvertiseError;
import com.welie.blessed.BluetoothCentral;
import com.welie.blessed.BluetoothPeripheralManagerCallback;
import com.welie.blessed.GattStatus;
import com.welie.blessed.ReadResponse;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.AdvertiseSettings;

public class BluetoothLowEnergyServerLogger extends BluetoothPeripheralManagerCallback {
  @Override
  public void onServiceAdded(GattStatus status, BluetoothGattService service) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Service added" + "\nStatus: " + status.name());
    super.onServiceAdded(status, service);
  }

  @Override
  public ReadResponse onCharacteristicRead(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Characteristic read");
    return super.onCharacteristicRead(bluetoothCentral, characteristic);
  }

  @Override
  public GattStatus onCharacteristicWrite(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic, byte[] value) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Characteristic write");
    return super.onCharacteristicWrite(bluetoothCentral, characteristic, value);
  }

  @Override
  public void onCharacteristicWriteCompleted(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic, byte[] value) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Characteristic write (complete)");
    super.onCharacteristicWriteCompleted(bluetoothCentral, characteristic, value);
  }

  @Override
  public ReadResponse onDescriptorRead(BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Descriptor read");
    return super.onDescriptorRead(bluetoothCentral, descriptor);
  }

  @Override
  public GattStatus onDescriptorWrite(BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor, byte[] value) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Descriptor write");
    return super.onDescriptorWrite(bluetoothCentral, descriptor, value);
  }

  @Override
  public void onDescriptorWriteCompleted(BluetoothCentral bluetoothCentral, BluetoothGattDescriptor descriptor, byte[] value) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Descriptor write (complete)");
    super.onDescriptorWriteCompleted(bluetoothCentral, descriptor, value);
  }

  @Override
  public void onNotifyingEnabled(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Characteristic notification (enabled)");
    super.onNotifyingEnabled(bluetoothCentral, characteristic);
  }

  @Override
  public void onNotifyingDisabled(BluetoothCentral bluetoothCentral, BluetoothGattCharacteristic characteristic) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Characteristic notification (disabled)");
    super.onNotifyingDisabled(bluetoothCentral, characteristic);
  }

  @Override
  public void onNotificationSent(BluetoothCentral bluetoothCentral, byte[] value, BluetoothGattCharacteristic characteristic, GattStatus status) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Characteristic notification (sent)");
    super.onNotificationSent(bluetoothCentral, value, characteristic, status);
  }

  @Override
  public void onCentralConnected(BluetoothCentral bluetoothCentral) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: BluetoothCentral (connected)");
    super.onCentralConnected(bluetoothCentral);
  }

  @Override
  public void onCentralDisconnected(BluetoothCentral bluetoothCentral) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: BluetoothCentral (disconnected)");
    super.onCentralDisconnected(bluetoothCentral);
  }

  @Override
  public void onAdvertisingStarted(AdvertiseSettings settingsInEffect) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Advertising (started)");
    super.onAdvertisingStarted(settingsInEffect);
  }

  @Override
  public void onAdvertiseFailure(AdvertiseError advertiseError) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Advertising (failed)" + "\nError: " + advertiseError.name());
    super.onAdvertiseFailure(advertiseError);
  }

  @Override
  public void onAdvertisingStopped() {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothLowEnergyServer: Advertising (stopped)");
    super.onAdvertisingStopped();
  }
}
