package com.github.warren_bank.one_hop;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Build;

import java.util.ArrayList;
import java.util.List;

public final class PermissionsMgr {

  public interface PermissionsListener {
    public void onPermissionsGranted ();
    public void onPermissionsDenied  ();
  }

  private static int REQUEST_CODE_PERMISSIONS = 1;

  private static String[] getAllPermissions() {
    if (Build.VERSION.SDK_INT < 23)
      return null;

    List<String> allPermissions = new ArrayList<String>();

    if (Build.VERSION.SDK_INT <= 30) {
      allPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
      allPermissions.add(Manifest.permission.BLUETOOTH);
      allPermissions.add(Manifest.permission.BLUETOOTH_ADMIN);

      if (Build.VERSION.SDK_INT >= 29) {
        allPermissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
      }
    }
    else {
      allPermissions.add(Manifest.permission.BLUETOOTH_ADVERTISE);
      allPermissions.add(Manifest.permission.BLUETOOTH_CONNECT);
      allPermissions.add(Manifest.permission.BLUETOOTH_SCAN);
    }
    allPermissions.add(Manifest.permission.WAKE_LOCK);

    return allPermissions.toArray(new String[allPermissions.size()]);
  }

  private static String[] getMissingPermissions(Activity activity) {
    String[] allPermissions = getAllPermissions();

    if ((allPermissions == null) || (allPermissions.length == 0))
      return null;

    List<String> missingPermissions = new ArrayList<String>();

    for (String permission : allPermissions) {
      if (activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
        missingPermissions.add(permission);
      }
    }

    if (missingPermissions.isEmpty())
      return null;

    return missingPermissions.toArray(new String[missingPermissions.size()]);
  }

  public static void requestPermissions(Activity activity, PermissionsListener listener) {
    String[] permissions = getMissingPermissions(activity);

    if ((permissions == null) || (permissions.length == 0))
      listener.onPermissionsGranted();
    else
      activity.requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
  }

  public static void onRequestPermissionsResult(PermissionsListener listener, int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == REQUEST_CODE_PERMISSIONS) {
      boolean OK = true;
      for (int result : grantResults) {
        OK = OK && (result == PackageManager.PERMISSION_GRANTED);
      }

      if (OK)
        listener.onPermissionsGranted();
      else
        listener.onPermissionsDenied();
    }
  }

}
