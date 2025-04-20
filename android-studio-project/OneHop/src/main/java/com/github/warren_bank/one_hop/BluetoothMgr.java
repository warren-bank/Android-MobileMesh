package com.github.warren_bank.one_hop;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

public final class BluetoothMgr {

  public interface BluetoothListener {
    public void onBluetoothEnabled (boolean didEnable);
    public void onBluetoothDenied  ();
  }

  public static boolean isSupported() {
    return (App.btAdapter != null);
  }

  public static boolean isEnabled() {
    return App.btAdapter.isEnabled();
  }

  public static boolean enable() {
    return App.btAdapter.enable();
  }

  public static boolean disable() {
    return App.btAdapter.disable();
  }

  public static void requestEnable(Activity activity, BluetoothListener listener) {
    if (isEnabled())
      listener.onBluetoothEnabled(false);
    else {
      Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
      activity.startActivityForResult(intent, Constants.REQUEST_CODE_ENABLE_BT);
    }
  }

  public static void onActivityResult(BluetoothListener listener, int requestCode, int resultCode, Intent data) {
    if (requestCode == Constants.REQUEST_CODE_ENABLE_BT) {
      if (resultCode == Activity.RESULT_OK)
        listener.onBluetoothEnabled(true);
      else
        listener.onBluetoothDenied();
    }
  }

}
