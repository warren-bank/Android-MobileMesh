package com.github.warren_bank.one_hop;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public final class PrefsMgr {

  public static boolean log_ble_peripheral_discovery = false;

  public static SharedPreferences getPrefs(Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  // --------------------

  public static void init(final Context context) {
    SharedPreferences prefs = getPrefs(context);
    log_ble_peripheral_discovery = getLogBlePeripheralDiscoveryPreference(context, prefs);

    final String log_ble_peripheral_discovery_key = context.getString(R.string.pref_log_ble_peripheral_discovery_key);

    prefs.registerOnSharedPreferenceChangeListener(
      new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
          if (key.equals(log_ble_peripheral_discovery_key)) {
            log_ble_peripheral_discovery = getLogBlePeripheralDiscoveryPreference(context, prefs);
          }
        }
      }
    );
  }

  // --------------------

  public static boolean getLogBlePeripheralDiscoveryPreference(Context context) {
    return getLogBlePeripheralDiscoveryPreference(context, getPrefs(context));
  }

  private static boolean getLogBlePeripheralDiscoveryPreference(Context context, SharedPreferences prefs) {
    String pref_key     = context.getString(R.string.pref_log_ble_peripheral_discovery_key);
    String pref_default = context.getString(R.string.pref_log_ble_peripheral_discovery_default);
    boolean val_default = "true".equals(pref_default);

    return prefs.getBoolean(pref_key, val_default);
  }

}
