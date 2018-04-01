package utils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static java.lang.Integer.parseInt;

/**
 * Created by LFZ on 2017/12/5.
 */

public class EncryptionUtils {


    /**
     * 对字符串进行加密 {@link #decrypt}
     * @static
     * @param str 原始字符串
     * @param pwd 密钥
     * @returns {String} 加密后的字符串
     */
    public static String encrypt(String str, String pwd) {
        if (str == "") {
            return "";
        }
        str = escape(str);
        if (TextUtils.isEmpty(pwd)) {
            pwd = "655";
        }
        pwd = escape(pwd);
        if (pwd == null || pwd.length() <= 0) {
            Log.e("","Please enter a password with which to encrypt the message.");
            return null;
        }
        String prand = "";
        for (int I = 0; I < pwd.length(); I++) {
            prand += pwd.codePointAt(I);
        }
        int sPos = (int)Math.floor(prand.length() / 5);
        int mult = Integer.valueOf(prand.charAt(sPos) + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4) + prand.charAt(sPos * 5));

        int incr = (int) Math.ceil(pwd.length() / 2);
        int modu = (int) (Math.pow(2, 31) - 1);
        if (mult < 2) {
            return null;
        }
        int salt = (int) (Math.round(Math.random() * 1000000000) % 100000000);
        prand += salt;
        int newPrand = 1;
        while (prand.length() > 10) {
            newPrand = parseInt(prand.substring(0, 10)) + parseInt(prand.substring(10, prand.length()), 10);
        }
        newPrand = (mult * newPrand + incr) % modu;
        int enc_chr;
        String enc_str = "";
        for (int I = 0; I < str.length(); I++) {
            enc_chr = str.codePointAt(I) ^ (int)Math.floor((newPrand / modu) * 255);
            if (enc_chr < 16) {
                enc_str += "0" + Integer.toHexString(enc_chr);
            } else {
                enc_str += Integer.toHexString(enc_chr);
            }
            newPrand = (mult * newPrand + incr) % modu;
        }
        String newSalt = "";
        newSalt = Integer.toHexString(salt);
        while (newSalt.length() < 8) {
            newSalt = "0" + newSalt;
        }
        enc_str += newSalt;
        return enc_str;
    }

    /**
     * 对加密后的字符串解密 {@link #encrypt}
     * @static
     * @param str 加密过的字符串
     * @param pwd 密钥
     * @returns {String} 解密后的字符串
     */
    public static String decrypt(String str, String pwd) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        if (TextUtils.isEmpty(pwd)) {
            pwd = "655";
        }
        pwd = escape(pwd);
        if (str == null || str.length() < 8) {
            return null;
        }
        if (pwd == null || pwd.length() <= 0) {
            return null;
        }
        String prand = "";
        for (int i = 0; i < pwd.length(); i++) {
            prand += pwd.codePointAt(i);
        }
        int sPos = (int) Math.floor(prand.length() / 5);
        int tempmult = prand.charAt(sPos) + prand.charAt(sPos * 2) + prand.charAt(sPos * 3) + prand.charAt(sPos * 4);
        if (sPos * 5 < prand.length()) {
            tempmult += prand.charAt(sPos * 5);
        }
        int mult = tempmult;
        int incr = Math.round(pwd.length() / 2);
        int modu = (int) (Math.pow(2, 31) - 1);
        int salt = parseInt(str.substring(str.length() - 8, str.length()), 16);
        str = str.substring(0, str.length() - 8);
        prand += salt;
        int newPrand = 1;
        while (prand.length() > 10) {
            newPrand = parseInt(prand.substring(0, 10), 10) + parseInt(prand.substring(10, prand.length()), 10);
        }
        newPrand = (mult * newPrand + incr) % modu;
        int enc_chr ;
        String enc_str = "";
        for (int i = 0; i < str.length(); i += 2) {
            enc_chr = parseInt(str.substring(i, i + 2), 16) ^ (int)Math.floor((newPrand / modu) * 255);
            enc_str += unicode2String(String.valueOf(enc_chr));
            newPrand = (mult * newPrand + incr) % modu;
        }
        return unescape(enc_str);
    }

    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }

    public static String unescape(String src) {
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length());
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < src.length()) {
            pos = src.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (src.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 2, pos + 6), 16);
                    tmp.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(src
                            .substring(pos + 1, pos + 3), 16);
                    tmp.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    tmp.append(src.substring(lastPos));
                    lastPos = src.length();
                } else {
                    tmp.append(src.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return tmp.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }



    /**
     * 对字符串解密
     *
     * @param key  密钥
     * @param data 已被加密的字符串
     * @return 解密得到的字符串
     */
    public static String decode(String key, String data) throws Exception {
        try {
            byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance(CipherMode);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec, new IvParameterSpec(
                    new byte[cipher.getBlockSize()]));
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //    private static final String CipherMode = "AES/ECB/PKCS5Padding";使用ECB加密，不需要设置IV，但是不安全
    private static final String CipherMode = "AES/CFB/NoPadding";//使用CFB加密，需要设置IV

    // /** 创建密钥 **/
    private static SecretKeySpec createKey(String password) {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append(password);
        while (sb.length() < 32) {
            sb.append("0");
        }
        if (sb.length() > 32) {
            sb.setLength(32);
        }

        try {
            data = sb.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(data, "AES");
    }

    // /** 加密字节数据 **/
    private static byte[] encrypt1(byte[] content, String password) {
        try {
            SecretKeySpec key = createKey(password);
            System.out.println(key);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(
                    new byte[cipher.getBlockSize()]));
            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // /** 加密(结果为16进制字符串) **/
    public static String encrypt1(String password,String content ) {
        Log.d("加密前","seed="+password+"\ncontent="+content);
        byte[] data = null;
        try {
            data = content.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = encrypt1(data, password);
        String result = byte2hex(data);
        Log.d("加密后","result="+result);
        return result;
    }

    // /** 解密字节数组 **/
    private static byte[] decrypt1(byte[] content, String password) {

        try {
            SecretKeySpec key = createKey(password);
            Cipher cipher = Cipher.getInstance(CipherMode);
            cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(
                    new byte[cipher.getBlockSize()]));

            return cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // /** 解密16进制的字符串为字符串 **/
    public static String decrypt1(String password,String content) {
        Log.d("解密前","seed="+password+"\ncontent="+content);
        byte[] data = null;
        try {
            data = hex2byte(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        data = decrypt1(data, password);
        if (data == null)
            return null;
        String result = null;
        try {
            result = new String(data, "UTF-8");
            Log.d("解密后","result="+result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    // /** 字节数组转成16进制字符串 **/
    private static String byte2hex(byte[] b) { // 一个字节的数，
        StringBuilder sb = new StringBuilder(b.length * 2);
        String tmp = "";
        for (byte aB : b) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(aB & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase(); // 转成大写
    }

    // /** 将hex字符串转换成字节数组 **/
    private static byte[] hex2byte(String inputString) {
        if (inputString == null || inputString.length() < 2) {
            return new byte[0];
        }
        inputString = inputString.toLowerCase();
        int l = inputString.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = inputString.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }
}
