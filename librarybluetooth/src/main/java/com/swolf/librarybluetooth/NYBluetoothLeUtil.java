package com.swolf.librarybluetooth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 蓝牙工具
 * Created by LiuYi-15973602714
 */
@SuppressLint("NewApi")
public class NYBluetoothLeUtil {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothLeScanner bluetoothLeScanner;
    BluetoothGatt mBluetoothGatt;
    Activity activity;

    @SuppressLint("WrongConstant")
    public NYBluetoothLeUtil(Activity activity) {
        super();
        this.activity = activity;
        if (!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(activity, "设备不支持蓝牙4.0", Toast.LENGTH_SHORT).show();
            return;
        }
        BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(activity, "设备没有检测到蓝牙设备,不支持蓝牙", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            mBluetoothAdapter.enable();
            bluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }
    }

    public void startScanLeDevice() {
        NYBluetoothData.map.clear();
        Handler handler = new Handler();
        // Stops scanning after a pre-defined scan period.
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScanLeDevice();
            }
        }, 30000); //在这里可以自己进行时间的设置，比如搜索10秒
        bluetoothLeScanner.startScan(scanCallback); //开始搜索
    }

    public void stopScanLeDevice() {
        bluetoothLeScanner.stopScan(scanCallback);
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            NYBluetoothData.map.put(result.getDevice().getAddress(), result.getDevice());
        }
    };

    public boolean connect(final String address, BluetoothGattCallback bluetoothGattCallback) {
        if (mBluetoothAdapter == null || address == null) {
            return false;
        }
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            return false;
        }
        //这里才是真正连接
        mBluetoothGatt = device.connectGatt(activity, false, bluetoothGattCallback);
        return mBluetoothGatt!=null;
    }


    public void disconnect() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        if (mBluetoothAdapter != null && mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothAdapter = null;
        }
    }

    /**
     * 发送数据到蓝牙 ,可写的UUID
     */
    public boolean wirteData(UUID characteristicUUID, byte[] bytes) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return false;
        }
        for (BluetoothGattCharacteristic c : NYBluetoothData.characteristicList) {
            if (c.getUuid() == characteristicUUID) {
                c.setValue(bytes);
                return mBluetoothGatt.writeCharacteristic(c);
            }
        }
        return false;
    }
    /**
     * 可读蓝牙数据,可读的UUID
     */
    public void readCharacteristic(UUID characteristicUUID) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return;
        }
        BluetoothGattCharacteristic c1 = null;
        for (BluetoothGattCharacteristic c : NYBluetoothData.characteristicList) {
            if (c.getUuid() == characteristicUUID) {
                c1 = c;
                break;
            }
        }
        if(c1!=null){
            mBluetoothGatt.readCharacteristic(c1);
        }
    }


}
