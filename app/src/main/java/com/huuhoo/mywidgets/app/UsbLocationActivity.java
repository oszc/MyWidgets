package com.huuhoo.mywidgets.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.huuhoo.mywidgets.app.utils.StorageUtils;

import java.util.List;

public class UsbLocationActivity extends Activity{

    private static final String TAG = UsbLocationActivity.class.getSimpleName();
    private TextView mTvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_location);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        List<StorageUtils.StorageInfo> storageList = StorageUtils.getStorageList();
        for(StorageUtils.StorageInfo storageInfo: storageList){
            mTvContent.append(storageInfo.path+ "   "+storageInfo.getDisplayName()+"\n");
            mTvContent.append("--------------------------------------\n");
        }

    }

}
