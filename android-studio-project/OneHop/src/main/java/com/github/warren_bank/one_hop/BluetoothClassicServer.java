package com.github.warren_bank.one_hop;

import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

public class BluetoothClassicServer {

  private static BluetoothServerSocket btServer;
  private static MessageListenerThread messageListenerThread;

  public static void init(Context context) {
    if (App.macAddress == null) return;

    try {
      if (btServer == null) {
        btServer = App.btAdapter.listenUsingInsecureRfcommWithServiceRecord(
          context.getString(R.string.app_name),
          Utils.getUUID(App.macAddress, true)
        );
      }

      if (messageListenerThread == null) {
        messageListenerThread = new MessageListenerThread();
        messageListenerThread.start();
      }
    }
    catch(Exception e) {}
  }

  public static void close() {
    try {
      if (messageListenerThread != null) {
        messageListenerThread.interrupt();
        messageListenerThread = null;
      }

      if (btServer != null) {
        btServer.close();
        btServer = null;
      }
    }
    catch(Exception e) {}
  }

  private static class MessageListenerThread extends Thread {
    public void run() {
      while (!isInterrupted() && (btServer != null)) {
        try {
          BluetoothSocket btSocket = btServer.accept();
          String macAddress = btSocket.getRemoteDevice().getAddress();
          byte[] bytes = Utils.readAllBytes(
            btSocket.getInputStream()
          );
          btSocket.close();

          // OneHop only sends text
          String message = new String(bytes);

          Utils.addLogMessage(macAddress, "BluetoothClassicServer: " + message);
        }
        catch(Exception e) {}
      }
    }
  }

}
