package com.github.warren_bank.one_hop;

// support library
import android.databinding.ObservableArrayList;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Utils {

  // ---------------------------------------------------------------------------
  // databinding helpers

  public static List<String> getObservableStringList() {
    return new ObservableArrayList<String>();
  }

  // ---------------------------------------------------------------------------
  // data type helpers

  private static long macStringToLong(String macAddress) {
    long result = 0;
    try {
      String[] parts = macAddress.split("[:-]");
      for (String part : parts) {
        result = result << 8;
        result |= Integer.parseInt(part, 16);
      }
    }
    catch(Exception e) {}
    return result;
  }

  private static UUID macStringToUUID(String macAddress) {
    return new UUID(
      macStringToLong(macAddress),
      0L
    );
  }

  public static UUID getUUID(String text) {
    return getUUID(text, false);
  }

  public static UUID getUUID(String text, boolean isMacAddress) {
    try {
      return isMacAddress
        ? macStringToUUID(text)
        : UUID.fromString(text);
    }
    catch(Exception e) {}
    return null;
  }

  public static UUID[] getUUIDs(String text) {
    return getUUIDs(text, false);
  }

  public static UUID[] getUUIDs(String text, boolean isMacAddress) {
    UUID uuid = getUUID(text, isMacAddress);
    return (uuid != null)
      ? new UUID[]{ uuid }
      : null;
  }

  // ---------------------------------------------------------------------------
  // stream helpers

  public static byte[] readAllBytes(InputStream inputStream) throws IOException {
    final int bufferSize = 1024;
    byte[] buffer = new byte[bufferSize];
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }
    return outputStream.toByteArray();
  }

  // ---------------------------------------------------------------------------
  // application state helpers

  public static void addEdge(String macAddress) {
    try {
      if (!App.edges.contains(macAddress)) {
        App.edges.add(macAddress);
      }
    }
    catch(Exception e) {}
  }

  public static void addLogMessage(String macAddress, String message) {
    try {
      if (macAddress == null)
        macAddress = App.macAddress;

      if (macAddress == null) return;

      // add metadata to message
      message = "[" + getTimestamp() + "] " + message;

      initLog(macAddress);
      List<String> log = App.logs.get(macAddress);
      log.add(message);
      App.logs.put(macAddress, log);
    }
    catch(Exception e) {}
  }

  public static void initLog(String macAddress) {
    try {
      if (macAddress == null) return;
      if (App.logs.containsKey(macAddress)) return;

      List<String> log = getObservableStringList();
      App.logs.put(macAddress, log);
    }
    catch(Exception e) {}
  }

  // ---------------------------------------------------------------------------
  // event log helpers

  public static String getTimestamp() {
    Date now = new Date();
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    return timeFormat.format(now);
  }

}
