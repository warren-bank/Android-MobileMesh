package com.github.warren_bank.one_hop;

import com.github.warren_bank.one_hop.fragments.EdgeListFragment;
import com.github.warren_bank.one_hop.fragments.LogListFragment;
import com.github.warren_bank.one_hop.fragments.SendMessageFragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity implements PermissionsMgr.PermissionsListener {

  public static enum Fragments {
    ALL_EDGES, EDGE_CHAT, EDGE_LOG
  }

  private boolean didEnableBT = false;
  private boolean permissionsGranted = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    didEnableBT = !App.btAdapter.isEnabled();

    requestPermissions();
  }

  private void requestPermissions() {
    PermissionsMgr.requestPermissions(this, this);
  }

  @Override
  public void onPermissionsGranted() {
    permissionsGranted = true;

    PrefsMgr.init(this);
    initBluetooth();
    startBluetooth();

    showFragment(Fragments.ALL_EDGES, null);

    if (App.macAddress != null)
      setTitle(
        getString(R.string.app_name) + " (" + App.macAddress + ")"
      );
  }

  @Override
  public void onPermissionsDenied() {
    Toast.makeText(this, R.string.permissions_denied, Toast.LENGTH_SHORT).show();
    requestPermissions();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();

    if (permissionsGranted) {
      stopBluetooth();
      releaseBluetooth();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    PermissionsMgr.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
  }

  @Override
  public void onBackPressed() {
    Fragment fragment = getFragmentManager().findFragmentById(android.R.id.content);

    if (!permissionsGranted || (fragment == null) || (fragment instanceof EdgeListFragment)) {
      // default: exit
      super.onBackPressed();
    }
    else {
      // replace all other fragments
      showFragment(Fragments.ALL_EDGES, null);
    }
  }

  public void showFragment(Fragments which, String macAddress) {
    Fragment fragment = null;

    switch(which) {
      case ALL_EDGES:
        fragment = new EdgeListFragment(App.edges);
        break;
      case EDGE_CHAT:
        fragment = new SendMessageFragment(macAddress);
        break;
      case EDGE_LOG:
        Utils.initLog(macAddress);
        fragment = new LogListFragment(App.logs.get(macAddress));
        break;
    }

    if (fragment == null) return;

    getFragmentManager()
      .beginTransaction()
      .replace(android.R.id.content, fragment)
      .commit();
  }

  private void enableBluetooth() {
    if (!didEnableBT) return;

    if (!App.btAdapter.isEnabled())
      App.btAdapter.enable();
  }

  private void disableBluetooth() {
    if (!didEnableBT) return;

    if (App.btAdapter.isEnabled())
      App.btAdapter.disable();
  }

  private void initBluetooth() {
    enableBluetooth();
    WakeLockMgr.acquire(this);

    BluetoothLowEnergyClient.initCentralManager(this);
    BluetoothLowEnergyServer.initPeripheralManager(this);
    BluetoothClassicServer.init(this);
  }

  private void startBluetooth() {
    BluetoothLowEnergyClient.startScanning();
    BluetoothLowEnergyServer.startAdvertising();
  }

  private void stopBluetooth() {
    BluetoothLowEnergyClient.stopScanning();
    BluetoothLowEnergyServer.stopAdvertising();
  }

  private void releaseBluetooth() {
    BluetoothLowEnergyClient.close();
    BluetoothLowEnergyServer.close();
    BluetoothClassicServer.close();

    WakeLockMgr.release();
    disableBluetooth();
  }

  // Menu > Settings:

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.menu_settings) {
      Intent in = new Intent(this, PrefsActivity.class);
      startActivity(in);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
