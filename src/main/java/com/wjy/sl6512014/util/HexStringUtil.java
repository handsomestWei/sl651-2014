package com.wjy.sl6512014.util;

import io.netty.buffer.ByteBuf;

/**
 * @author weijiayu
 * @date 2022/5/12 22:57
 */
public class HexStringUtil {

    private static final char[] HEX_CHAR =
        {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    // "7e7e" => [7e,7e]
    public static char[] hexStr2CharArray(String hexStr) {
        String[] hexStrs = hexStr.split("(?=(..)+$)");
        char[] results = new char[hexStrs.length];
        for (int i = 0; i < hexStrs.length; i++) {
            results[i] = (char)(int)(Integer.parseInt(hexStrs[i], 16));
        }
        return results;
    }

    // [7e,7e] => "7e7e"
    public static String int2HexStr(char[] frame, int startIndex, int endIndex) {
        StringBuffer sb = new StringBuffer();
        for (int i = startIndex; i <= endIndex; i++) {
            sb.append(int2HexStr(frame[i]));
        }
        return sb.toString();
    }

    // '0x02' => "02"
    public static String int2HexStr(char c) {
        String str = Integer.toHexString(c);
        if (str.length() == 1) {
            // 补前导0
            str = "0" + str;
        }
        return str;
    }

    public static String bytes2HexStr(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        return new String(buf);
    }

    public static byte[] hexStr2Bytes(String str) {
        if (str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte)Integer.parseInt(subStr, 16);
        }
        return bytes;
    }

    public static String byteBuf2Str(ByteBuf byteBuf) {
        String str = null;
        try {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.getBytes(byteBuf.readerIndex(), bytes);
            str = new String(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
