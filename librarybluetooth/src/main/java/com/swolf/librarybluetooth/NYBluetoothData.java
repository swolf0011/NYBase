package com.swolf.librarybluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 蓝牙数据
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressLint({"NewApi", "UseSparseArrays"})
public class NYBluetoothData {

//    public static final String uuidQppService = "0000FFF0-0000-1000-8000-00805f9b34fb";
//    public static final String uuidQppCharWrite = "0000FFF3-0000-1000-8000-00805f9b34fb";
//    public static final String uuidQppDescriptor = "00002902-0000-1000-8000-00805f9b34fb";

    public static List<BluetoothGattCharacteristic> characteristicList = new ArrayList<BluetoothGattCharacteristic>();
    public static Map<String, BluetoothDevice> map = new HashMap<String, BluetoothDevice>();

}
