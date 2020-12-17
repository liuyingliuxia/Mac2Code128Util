package com.zeasn.mac2code128util;

/**
 * name:miracle.lin
 * date:2020/12/17/ 19:01
 * email:miracle.lin@zeasn.com
 * describe:default
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class WriteQRCode {

    public static void main(String[] args) {
        int width = 200;
        int height = 200;

        // 写 二维码
        TestWriteRCode("d:/temp/a.png", BarcodeFormat.QR_CODE, "二维码文字信息, Hello world", width, height);

        // 写条码
        height = 50;
        TestWriteRCode("d:/temp/b.png", BarcodeFormat.CODE_128, "ABC123456abc", width, height);
    }

    /**
     * 将字符串信息写入 QRCode
     *
     * @param fileName    指定文件名
     * @param strContents 写入的内容
     * @param width       尺寸宽
     * @param height      尺寸高
     */
    public static void TestWriteRCode(String fileName, BarcodeFormat typeCode, String strContents, int width, int height) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 字符集
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");// Constant.CHARACTER);
        // 容错质量
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);

        try {
            // 尺寸
            BitMatrix bitMatrix = new MultiFormatWriter().encode(strContents, typeCode, width, height, hints);
            BufferedOutputStream buffer = null;

            buffer = new BufferedOutputStream(new FileOutputStream(fileName));
            // ok
//            MatrixToImageWriter.writeToStream(bitMatrix, "png", new FileOutputStream(new File(fileName)));
            buffer.close();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
