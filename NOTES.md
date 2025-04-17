### [BluetoothChat](https://github.com/warren-bank/Android-MobileMesh/tree/proof-of-concept/3rd-party/bluetooth-le-chat/google/api-28/main)

original:

* built from: [git tree](https://github.com/android/connectivity-samples/tree/c9b9b476b70efee373a37c6185cc098f16210d51/BluetoothLeChat)
* minSDK: [28](https://github.com/android/connectivity-samples/blob/c9b9b476b70efee373a37c6185cc098f16210d51/BluetoothLeChat/app/build.gradle#L25)
* license: [Apache 2.0](https://github.com/android/connectivity-samples/blob/c9b9b476b70efee373a37c6185cc098f16210d51/BluetoothLeChat/app/src/main/java/com/example/bluetoothlechat/bluetooth/ChatServer.kt#L4)

modifications:

* updated build tools
* updated proguard rules and enabled minify
* remove the `kotlin-android-extensions` Gradle plugin
  - causes a compiler error
  - is no longer supported
  - was not needed
