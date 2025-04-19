package com.github.warren_bank.one_hop;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class BluetoothClassicClient {

  public static void send(String macAddress, String message) {
    byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
    send(macAddress, bytes);
  }

  public static void send(String macAddress, byte[] bytes) {
    Utils.addLogMessage(Constants.LocalDevice, "BluetoothClassicClient: opening connection" + "\nTo: " + macAddress + "\nSending: " + bytes.length + " bytes");

    (new MessageSenderThread(macAddress, bytes)).start();
  }

  private static class MessageSenderThread extends Thread {
    private final String macAddress;
    private final byte[] bytes;

    public MessageSenderThread(String macAddress, byte[] bytes) {
      this.macAddress = macAddress;
      this.bytes = bytes;
    }

    public void run() {
      try {
        BluetoothDevice btDevice = App.btAdapter.getRemoteDevice(macAddress);
        BluetoothSocket btSocket = btDevice.createInsecureRfcommSocketToServiceRecord(
          Utils.getUUID(macAddress, true)
        );

        btSocket.connect();
        OutputStream out = btSocket.getOutputStream();
        out.write(bytes);
        out.flush();
        out.close();
        btSocket.close();
      }
      catch(Exception e) {}
    }
  }

}
