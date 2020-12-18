package com.zeasn.mac2code128util;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;

import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;


public class MainActivity extends Activity {
    TextView tvMac;
    ImageView ivCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
//        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_main);

        tvMac = findViewById(R.id.tvMac);
        ivCode = findViewById(R.id.ivCode);
        String mac = CommonUtil.getWifiMacAddress(this, "eth0");
        if (mac.isEmpty()) {
            mac = CommonUtil.getWifiMacAddress(this, "wlan0");
            if (mac.isEmpty()) {
                tvMac.setText(getString(R.string.no_mac));
                ivCode.setBackgroundResource(R.mipmap.banner);
            } else {
                tvMac.setText(getString(R.string.wifi_mac, mac));
                createBarCode(mac);
            }
        } else {
            tvMac.setText(getString(R.string.e_mac, mac));
            createBarCode(mac);
        }
    }


    /**
     * 生成条形码
     *
     * @param content
     */
    private void createBarCode(String content) {
        new Thread(() -> {
            //生成条形码相关放在子线程里面
            Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_128, 800, 200, null, false);
            runOnUiThread(() -> {
                //显示条形码
                ivCode.setImageBitmap(bitmap);
            });
        }).start();
    }


}
