--------------------------------------------------------------------------------

https://android.googlesource.com/platform/packages/modules/Bluetooth/+/refs/tags/android-15.0.0_r25/framework/java/android/bluetooth/le/BluetoothLeAdvertiser.java

relevant code:
==============

line: 62
code:
    private static final int MAX_LEGACY_ADVERTISING_DATA_BYTES = 31;
    private static final int FLAGS_FIELD_BYTES = 3;

line: 139
code:
    public void startAdvertising(
      AdvertiseSettings settings,
      AdvertiseData advertiseData,
      AdvertiseData scanResponse,
      final AdvertiseCallback callback) {
          boolean isConnectable = settings.isConnectable();
          boolean isDiscoverable = settings.isDiscoverable();
          boolean hasFlags = isConnectable && isDiscoverable;

          if (
              totalBytes(advertiseData, hasFlags) > MAX_LEGACY_ADVERTISING_DATA_BYTES
           || totalBytes(scanResponse, false) > MAX_LEGACY_ADVERTISING_DATA_BYTES
          ) {
              postStartFailure(callback, AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE);
              return;
          }
    }

line: 695
code:
    private int totalBytes(AdvertiseData data, boolean isFlagsIncluded) {
        if (data == null) return 0;
        // Flags field is omitted if the advertising is not connectable.
        int size = (isFlagsIncluded) ? FLAGS_FIELD_BYTES : 0;
        if (data.getServiceUuids() != null) {
            int num16BitUuids = 0;
            int num32BitUuids = 0;
            int num128BitUuids = 0;
            for (ParcelUuid uuid : data.getServiceUuids()) {
                if (BluetoothUuid.is16BitUuid(uuid)) {
                    ++num16BitUuids;
                } else if (BluetoothUuid.is32BitUuid(uuid)) {
                    ++num32BitUuids;
                } else {
                    ++num128BitUuids;
                }
            }
            // 16 bit service uuids are grouped into one field when doing advertising.
            if (num16BitUuids != 0) {
                size += OVERHEAD_BYTES_PER_FIELD + num16BitUuids * BluetoothUuid.UUID_BYTES_16_BIT;
            }
            // 32 bit service uuids are grouped into one field when doing advertising.
            if (num32BitUuids != 0) {
                size += OVERHEAD_BYTES_PER_FIELD + num32BitUuids * BluetoothUuid.UUID_BYTES_32_BIT;
            }
            // 128 bit service uuids are grouped into one field when doing advertising.
            if (num128BitUuids != 0) {
                size +=
                        OVERHEAD_BYTES_PER_FIELD
                                + num128BitUuids * BluetoothUuid.UUID_BYTES_128_BIT;
            }
        }
        if (data.getServiceSolicitationUuids() != null) {
            int num16BitUuids = 0;
            int num32BitUuids = 0;
            int num128BitUuids = 0;
            for (ParcelUuid uuid : data.getServiceSolicitationUuids()) {
                if (BluetoothUuid.is16BitUuid(uuid)) {
                    ++num16BitUuids;
                } else if (BluetoothUuid.is32BitUuid(uuid)) {
                    ++num32BitUuids;
                } else {
                    ++num128BitUuids;
                }
            }
            // 16 bit service uuids are grouped into one field when doing advertising.
            if (num16BitUuids != 0) {
                size += OVERHEAD_BYTES_PER_FIELD + num16BitUuids * BluetoothUuid.UUID_BYTES_16_BIT;
            }
            // 32 bit service uuids are grouped into one field when doing advertising.
            if (num32BitUuids != 0) {
                size += OVERHEAD_BYTES_PER_FIELD + num32BitUuids * BluetoothUuid.UUID_BYTES_32_BIT;
            }
            // 128 bit service uuids are grouped into one field when doing advertising.
            if (num128BitUuids != 0) {
                size +=
                        OVERHEAD_BYTES_PER_FIELD
                                + num128BitUuids * BluetoothUuid.UUID_BYTES_128_BIT;
            }
        }
        for (TransportDiscoveryData transportDiscoveryData : data.getTransportDiscoveryData()) {
            size += OVERHEAD_BYTES_PER_FIELD + transportDiscoveryData.totalBytes();
        }
        for (ParcelUuid uuid : data.getServiceData().keySet()) {
            int uuidLen = BluetoothUuid.uuidToBytes(uuid).length;
            size +=
                    OVERHEAD_BYTES_PER_FIELD
                            + uuidLen
                            + byteLength(data.getServiceData().get(uuid));
        }
        for (int i = 0; i < data.getManufacturerSpecificData().size(); ++i) {
            size +=
                    OVERHEAD_BYTES_PER_FIELD
                            + MANUFACTURER_SPECIFIC_DATA_LENGTH
                            + byteLength(data.getManufacturerSpecificData().valueAt(i));
        }
        if (data.getIncludeTxPowerLevel()) {
            size += OVERHEAD_BYTES_PER_FIELD + 1; // tx power level value is one byte.
        }
        if (data.getIncludeDeviceName()) {
            final int length = mBluetoothAdapter.getNameLengthForAdvertise();
            if (length >= 0) {
                size += OVERHEAD_BYTES_PER_FIELD + length;
            }
        }
        return size;
    }

    private int byteLength(byte[] array) {
        return array == null ? 0 : array.length;
    }

notes:
======

* the error can be triggered by either "advertiseData" or "scanResponse"
* sizes of each, as reported in the "local device" log:
  - "advertiseData"
    * previous configuration (not connectable nor discoverable) = 18 bytes
    * current configuration  ( is connectable and discoverable) = 18 bytes
  - "scanResponse"
    * previous configuration (includes the device name) = 28 bytes
    * current configuration  (excludes the device name) =  0 bytes
      - advertising error is gone
      - advertising is now working

--------------------------------------------------------------------------------
