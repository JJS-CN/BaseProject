package com.common.core.uuid;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class:从小爱那边复制过来的加密方法。对设备id进行加密
 * Other:
 * Create by jsji on  2020/4/3.
 */
public class CryptoUtils {
    public CryptoUtils() {
    }

    public static String MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for(int i = 0; i < charArray.length; ++i) {
                byteArray[i] = (byte)charArray[i];
            }

            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();

            for(int i = 0; i < md5Bytes.length; ++i) {
                int val = md5Bytes[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            return hexValue.toString();
        } catch (Exception var8) {
            var8.printStackTrace();
            return "";
        }
    }

    public static String md5File(String filepath) {
        try {
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024];
            boolean var5 = true;

            int length;
            while((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }

            BigInteger bigInt = new BigInteger(1, md.digest());
            return bigInt.toString(16);
        } catch (FileNotFoundException var7) {
            var7.printStackTrace();
        } catch (NoSuchAlgorithmException var8) {
            var8.printStackTrace();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return null;
    }
}
