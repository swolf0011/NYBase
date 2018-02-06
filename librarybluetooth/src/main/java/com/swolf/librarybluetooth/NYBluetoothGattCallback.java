package com.swolf.librarybluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.widget.Toast;

import java.util.UUID;


/**
 * 蓝牙连接回调
 * Created by LiuYi-15973602714 on 2017-01-01
 */
@SuppressLint("NewApi")
public class NYBluetoothGattCallback extends BluetoothGattCallback {
    private boolean notifyEnabled = false;
    private Context context;
    private String uuidQppService = "0000FFF0-0000-1000-8000-00805f9b34fb";
    private String uuidQppCharWrite = "0000FFF3-0000-1000-8000-00805f9b34fb";
    private String uuidQppDescriptor = "00002902-0000-1000-8000-00805f9b34fb";

    private BluetoothDataCallback bluetoothCallback;


    public interface BluetoothDataCallback {
        void onReceiveData(BluetoothGatt bluetoothGatt, String qppUUIDForNotifyChar, byte[] qppData);
    }

    public NYBluetoothGattCallback(Context context, BluetoothDataCallback bluetoothCallback, String uuidQppService,
                                   String uuidQppCharWrite, String uuidQppDescriptor) {
        super();
        this.context = context;
        this.bluetoothCallback = bluetoothCallback;
        this.uuidQppService = uuidQppService;
        this.uuidQppCharWrite = uuidQppCharWrite;
        this.uuidQppDescriptor = uuidQppDescriptor;
    }

    /**
     * //当连接上设备或者失去连接时会回调该函数
     *
     * @param gatt
     * @param status
     * @param newState
     */
    @SuppressLint("WrongConstant")
    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            Toast.makeText(context, "蓝牙连接成功", Toast.LENGTH_LONG).show();
            gatt.discoverServices();
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            Toast.makeText(context, "蓝牙连接失败", Toast.LENGTH_LONG).show();
            gatt.disconnect();
        }
    }

    /**
     * //当设备是否找到服务时，会回调该函数
     *
     * @param gatt
     * @param status
     */
    @SuppressLint("WrongConstant")
    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Toast.makeText(context, "服务发现成功", Toast.LENGTH_LONG).show();
            qppEnable(gatt, uuidQppService, uuidQppCharWrite);
        } else {
        }
    }

    /**
     * //当读取设备中的数据时会回调该函数
     *
     * @param gatt
     * @param characteristic
     * @param status
     */
    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

        System.out.println("onCharacteristicRead");
        if (status == BluetoothGatt.GATT_SUCCESS) {
            //读取到的数据存在characteristic当中，可以通过characteristic.getValue();函数取出。然后再进行解析操作。
            int charaProp = characteristic.getProperties();
            //表示可发出通知。  判断该Characteristic属性
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {

            } else {
                //获取到的值
                byte[] bs = characteristic.getValue();
                if(bluetoothCallback!=null&&bs!=null&&bs.length>0){
                    bluetoothCallback.onReceiveData(gatt,characteristic.getUuid().toString(),bs);
                }
            }

        }
    }

    /**
     * //当向设备Descriptor中写数据时，会回调该函数
     *
     * @param gatt
     * @param descriptor
     * @param status
     */
    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        notifyEnabled = true;
    }

    /**
     * //当向Characteristic写数据时会回调该函数
     *
     * @param gatt
     * @param characteristic
     * @param status
     */
    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {


    }

    /**
     * //设备发出通知时会调用到该接口
     *
     * @param gatt
     * @param characteristic
     */
    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        updateValueForNotification(gatt, characteristic);
    }

    /**
     * 成功发现服务
     */
    private boolean qppEnable(BluetoothGatt bluetoothGatt, String qppServiceUUID, String writeCharUUID) {
        NYBluetoothData.characteristicList.clear();
        if (bluetoothGatt == null || qppServiceUUID.isEmpty() || writeCharUUID.isEmpty()) {
            return false;
        }
        BluetoothGattService qppService = bluetoothGatt.getService(UUID.fromString(qppServiceUUID));
        if (qppService == null) {
            return false;
        }
        NYBluetoothData.characteristicList = qppService.getCharacteristics();
        BluetoothGattCharacteristic chara = qppService.getCharacteristic(UUID.fromString(writeCharUUID));
        if (chara == null) {
            return false;
        } else {
            bluetoothGatt.setCharacteristicNotification(chara, true);
            BluetoothGattDescriptor descriptor = chara.getDescriptor(UUID.fromString(uuidQppDescriptor));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            bluetoothGatt.writeDescriptor(descriptor);
            return true;
        }
    }

    /**
     * 修改值关于通知
     */
    private void updateValueForNotification(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic characteristic) {
        if (bluetoothGatt == null || characteristic == null) {
            return;
        }
        if (!notifyEnabled) {
            return;
        }
        String strUUIDForNotifyChar = characteristic.getUuid().toString();
        final byte[] qppData = characteristic.getValue();
        if (bluetoothCallback != null && qppData != null && qppData.length > 0) {
            bluetoothCallback.onReceiveData(bluetoothGatt, strUUIDForNotifyChar, qppData);
        }
    }


}
