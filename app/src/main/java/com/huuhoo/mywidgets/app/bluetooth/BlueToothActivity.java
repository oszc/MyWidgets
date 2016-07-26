package com.huuhoo.mywidgets.app.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.huuhoo.mywidgets.app.R;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class BlueToothActivity extends AppCompatActivity {

    public BluetoothAdapter defaultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);


        defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去

        if(defaultAdapter == null){
            //no bluetooth device
        }else{
            defaultAdapter.isEnabled();
        }
        defaultAdapter.isDiscovering();
        //  getPairedDevices();

    }

    public void startScanBluetoothDevices(){
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(receiver, intentFilter);
        defaultAdapter.startDiscovery();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blue_tooth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void getPairedDevices(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();

        for (BluetoothDevice nextDevice : devices) {
            System.out.println(nextDevice.getName());
        }

    }

    public void startScan(){

    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 获取查找到的蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                System.out.println("ACTION_FOUND --> "+device.getName()+" "+device.getAddress()+"  "+((device.getBondState()==BluetoothDevice.BOND_NONE)?"Available":(device.getBondState()==BluetoothDevice.BOND_BONDING)?"Bounding":"Bounded"));
                // 如果查找到的设备符合要连接的设备，处理

            } else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                // 状态改变的广播
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                System.out.println("ACTION_BOND_STATE_CHANGED--> "+device.getName()+" "+device.getAddress()+"  "+((device.getBondState()==BluetoothDevice.BOND_NONE)?"Available":(device.getBondState()==BluetoothDevice.BOND_BONDING)?"Bounding":"Bounded"));
            }else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)){
                System.out.println("ACTION_DISCOVERY_STARTED");

            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                System.out.println("ACTION_DISCOVERY_FINISHED");
            }
        }
    };

    private void connect(BluetoothDevice device) throws IOException {
        // 固定的UUID
        final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
        UUID uuid = UUID.fromString(SPP_UUID);
        BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
        socket.connect();
    }


}
