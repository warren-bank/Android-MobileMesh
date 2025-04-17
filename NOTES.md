### [BluetoothChat](https://github.com/warren-bank/Android-MobileMesh/tree/proof-of-concept/3rd-party/bluetooth-chat/aosp/api-11/main)

original:

* built from: [git tree](https://github.com/googlearchive/android-BluetoothChat/tree/62adaa391f4d6714172451b42f6f665f39fbe7bb)
* minSDK: [11](https://github.com/googlearchive/android-BluetoothChat/blob/62adaa391f4d6714172451b42f6f665f39fbe7bb/Application/build.gradle#L41)
* license: [Apache 2.0](https://github.com/googlearchive/android-BluetoothChat/blob/62adaa391f4d6714172451b42f6f665f39fbe7bb/LICENSE)

modifications:

* updated build tools
* updated dependency versions:
  - `com.android.support:support-v4`
    * why:
      - originally: [20.*](https://github.com/googlearchive/android-BluetoothChat/blob/ac03683a0ccbbab6c2d86d697dc116dac5457fb1/Application/build.gradle#L17)
      * automatically updated to: [27.0.2](https://github.com/googlearchive/android-BluetoothChat/blob/62adaa391f4d6714172451b42f6f665f39fbe7bb/Application/build.gradle#L21)
    * minSDK:
      * 26.0.0 (and higher): [14](https://mvnrepository.com/artifact/com.android.support/support-v4/26.0.0)
      * 25.4.0 (and lower): [4](https://mvnrepository.com/artifact/com.android.support/support-v4/25.4.0)
  - `com.android.support:gridlayout-v7`
    * why:
      * originally: [20.*](https://github.com/googlearchive/android-BluetoothChat/blob/ac03683a0ccbbab6c2d86d697dc116dac5457fb1/Application/build.gradle#L18)
      * automatically updated to: [27.0.2](https://github.com/googlearchive/android-BluetoothChat/blob/62adaa391f4d6714172451b42f6f665f39fbe7bb/Application/build.gradle#L22)
    * minSDK:
      * 26.0.0 (and higher): [14](https://mvnrepository.com/artifact/com.android.support/gridlayout-v7/26.0.0)
      * 25.4.0 (and lower): [4](https://mvnrepository.com/artifact/com.android.support/gridlayout-v7/25.4.0)
  - summary:
    * the version of both support libraries was reduced from 27.0.2 to 25.4.0
