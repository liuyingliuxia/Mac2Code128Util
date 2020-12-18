package com.zeasn.mac2code128util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by ben_zhao on 2017/5/31.
 */
public class CommonUtil {
    /**
     *
     *
     * @param context
     * @param interfaceName 有线/无线 可选：eth0 / wlan0
     * @return
     */
    public static String getWifiMacAddress(Context context, String interfaceName) {
        String ret = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                String interfaceName = "eth0";
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                NetworkInterface intf = null;
                while (interfaces.hasMoreElements()) {
                    intf = interfaces.nextElement();
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) {
                        continue;
                    }
                    byte[] mac = intf.getHardwareAddress();
                    if (mac != null) {
                        StringBuilder buf = new StringBuilder();
                        for (byte aMac : mac) {
                            buf.append(String.format("%02X:", aMac));
                        }
                        if (buf.length() > 0) {
                            buf.deleteCharAt(buf.length() - 1);
                        }
                        ret = buf.toString();
                    }
                    break;
                }
            } else {
                WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifi != null) {
                    WifiInfo wifiInfo = wifi.getConnectionInfo();
                    if (wifiInfo != null) {
                        ret = wifiInfo.getMacAddress();
                    }
                }
            }
            return ret;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * wlan0 MAC地址获取，适用api9 - api24
     */
    public static String getWlan0Mac() {

        String Mac = "";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "Didn\'t get Wlan0 MAC address";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                    Mac = res1.toString();
                }
                return Mac;
            }
        } catch (Exception ex) {
        }
        return "Didn\'t get Wlan0 address";
    }

    /**
     * eth0 MAC地址获取，适用api9 - api24
     */
    public static String getEth0Mac() {

        String Mac = "";
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("eth0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "Didn\'t get eth0 MAC address";
                }
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }
                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                    Mac = res1.toString();
                }
                return Mac;
            }
        } catch (Exception ex) {
        }
        return "Didn\'t get eth0 MAC address";
    }


    /**
     * Deprecated please use the response in fetchToken method
     * witch is the field mandevid in TokenBean
     * Device unique identifier.
     * It is created by taking the hexadecimal representation of the Manufacturer ID,
     * expressed in 6 characters, and appending to it the decimal representation of the Device ID,
     * expressed in 10 characters.
     * <p>
     * manufacturerid去掉前缀0x补全六位+deviceid转换为10进制补全十位，然后拼接起来就是mandevid
     * deviceid:	0x4a82617
     * manufacturerid:	0x8c6
     * mandevId:	0008c60078128663
     *
     * @return
     */
    @Deprecated
    public static String getMandevId(String deviceid, String manufacturerid) throws Exception {

        String _manufacturerid = manufacturerid.substring(2);

        int prefix = 6 - _manufacturerid.length();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < prefix; i++) {
            builder.append("0");
        }
        builder.append(_manufacturerid);

        String _deviceid = Integer.parseInt(deviceid.substring(2), 16) + "";
        prefix = 10 - _deviceid.length();

        for (int i = 0; i < prefix; i++) {
            builder.append("0");
        }

        return builder.append(_deviceid).toString();
    }

    /**
     * dateformater
     * 2017-03-22T10:01:10
     */
    static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Get the date str by SimpleDateFormat
     *
     * @return
     */
    public static String getFormatDate() {

        return dateformat.format(new Date());
    }
}
