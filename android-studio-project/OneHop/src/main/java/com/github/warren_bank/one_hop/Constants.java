package com.github.warren_bank.one_hop;

import java.util.UUID;

public class Constants {

  public static String BluetoothLowEnergyServiceUUID = "767d937d-9d52-4b68-ab71-c5bb2ae1e1ce";

  public static UUID getBleServiceUUID() {
    try {
      return UUID.fromString(BluetoothLowEnergyServiceUUID);
    }
    catch(Exception e) {}
    return null;
  }

  public static UUID[] getBleServiceUUIDs() {
    UUID serviceUUID = getBleServiceUUID();
    return new UUID[]{ serviceUUID };
  }

}
