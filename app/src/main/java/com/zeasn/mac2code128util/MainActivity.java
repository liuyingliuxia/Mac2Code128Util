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
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;


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
        String mac = CommonUtil.getWifiMacAddress(this);
        tvMac.setText(getString(R.string.mac, mac));
        toCode128(mac);
    }

    private void toCode128(String mac) {
        try {
            Bitmap mBitmap = CodeUtils.createBarCode(this, BarcodeFormat.CODE_128,800,200);
            ivCode.setImageBitmap(mBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
