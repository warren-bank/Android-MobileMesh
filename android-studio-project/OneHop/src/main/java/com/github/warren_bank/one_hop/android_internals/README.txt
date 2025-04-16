--------------------------------------------------------------------------------

notes:
======

https://developer.android.com/reference/android/bluetooth/le/AdvertiseData

* unless otherwise noted, all methods are API 21+
  - API 31+ (Android 12)
      getServiceSolicitationUuids()
  - API 33+ (Android 13)
      getTransportDiscoveryData()

considerations:
===============

* if all of the following assertions are true:
    - the following copies of Android internal classes are used on a future version of Android
    - the AdvertiseData class has been updated to include additional data fields
    - the AdvertiseData class has not removed methods from its API
    - this application has not been updated to use any of the new AdvertiseData data fields
  then:
    - no error will occur
    - the output will be correct

--------------------------------------------------------------------------------

internal class: AdvertiseHelper
===============================

https://android.googlesource.com/platform/packages/apps/Bluetooth/
https://android.googlesource.com/platform/packages/apps/Bluetooth/+refs
https://android.googlesource.com/platform/packages/apps/Bluetooth/+/refs/tags/android-12.1.0_r27
https://android.googlesource.com/platform/packages/apps/Bluetooth/+/refs/tags/android-12.1.0_r27/src/com/android/bluetooth/gatt/AdvertiseHelper.java
  old location: <= Android 12

https://android.googlesource.com/platform/packages/modules/Bluetooth/
https://android.googlesource.com/platform/packages/modules/Bluetooth/+refs
https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/tags/android-15.0.0_r25
https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/tags/android-15.0.0_r25/android/app/src/com/android/bluetooth/gatt/AdvertiseHelper.java
  new location: >= Android 13

change visibility:
==================

1. edit:
    search  = class AdvertiseHelper
    replace = public final class AdvertiseHelper

to make safe for use on older devices:
======================================

1. add:
    import android.os.Build;

2. wrap:
    1. find unsafe code:
        if (data.getServiceSolicitationUuids() != null) {...}
    2. wrap with:
        if (Build.VERSION.SDK_INT >= 31) {...}

3. wrap:
    1. find unsafe code:
        for (TransportDiscoveryData transportDiscoveryData : data.getTransportDiscoveryData()) {...}
    2. wrap with:
        if (Build.VERSION.SDK_INT >= 33) {...}

--------------------------------------------------------------------------------

internal class: BluetoothUuid
=============================

https://android.googlesource.com/platform/frameworks/base/
https://android.googlesource.com/platform/frameworks/base/+refs
https://android.googlesource.com/platform/frameworks/base/+/refs/tags/android-12.1.0_r27
https://android.googlesource.com/platform/frameworks/base/+/refs/tags/android-12.1.0_r27/core/java/android/bluetooth/BluetoothUuid.java
  old location: <= Android 12

https://android.googlesource.com/platform/packages/modules/Bluetooth/
https://android.googlesource.com/platform/packages/modules/Bluetooth/+refs
https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/tags/android-15.0.0_r25
https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/tags/android-15.0.0_r25/framework/java/android/bluetooth/BluetoothUuid.java
  new location: >= Android 13

to remove annotations:
======================

1. remove:
    import android.annotation.NonNull;
    import android.annotation.Nullable;
    import android.annotation.SuppressLint;
    import android.annotation.SystemApi;
    import android.compat.annotation.UnsupportedAppUsage;

2. regex:
    search  = @(?:NonNull|Nullable|SuppressLint|SystemApi|UnsupportedAppUsage)(?:\([^\)]+\))?
    replace =

--------------------------------------------------------------------------------
