package com.wjy.sl6512014.util;

import org.apache.commons.codec.binary.Hex;

/**
 * @author weijiayu
 * @date 2022/5/12 15:20
 */
public class Crc16Util {

    /**
     * 一个字节包含位的数量 8
     */
    private static final int BITS_OF_BYTE = 8;

    /**
     * 多项式
     */
    private static final int POLYNOMIAL = 0xA001;

    /**
     * 初始值
     */
    private static final int INITIAL_VALUE = 0xFFFF;

    /**
     * CRC16 编码
     *
     * @param bytes
     * @return 编码结果
     */
    public static String crc16(int[] bytes, boolean isRevertHighLow) {
        int res = INITIAL_VALUE;
        for (int data : bytes) {
            res = res ^ data;
            for (int i = 0; i < BITS_OF_BYTE; i++) {
                res = (res & 0x0001) == 1 ? (res >> 1) ^ POLYNOMIAL : res >> 1;
            }
        }
        return isRevertHighLow ? Integer.toHexString(revert(res)) : Integer.toHexString(res);
    }

    public static String crc16(byte[] bytes, boolean isRevertHighLow) {
        int res = INITIAL_VALUE;
        for (byte bt : bytes) {
            int data = (int)bt & 0x000000ff;
            res = res ^ data;
            for (int i = 0; i < BITS_OF_BYTE; i++) {
                res = (res & 0x0001) == 1 ? (res >> 1) ^ POLYNOMIAL : res >> 1;
            }
        }
        return isRevertHighLow ? Integer.toHexString(revert(res)) : Integer.toHexString(res);

    }

    /**
     * 翻转16位的高八位和低八位字节
     *
     * @param src
     * @return 翻转结果
     */
    private static int revert(int src) {
        int lowByte = (src & 0xFF00) >> 8;
        int highByte = (src & 0x00FF) << 8;
        return lowByte | highByte;
    }

    public static void main(String[] args) throws Exception {
        String dataStr1 =
            "7E 7E 01 00 10 10 00 01 A0 00 32 00 2B 02 00 0C 20 07 24 17 23 49 F1 F1 00 10 10 00 01 48 F0 F0 20 07 24 17 23 20 19 00 01 30 22 19 00 00 40 39 23 00 00 05 50 38 12 12 68 03";
        String crcVerifyCode1 = "83 97";

        String dataStr2 =
            "7e 7e 01 00 00 00 00 32 00 00 32 00 2b 02 01 c6 22 05 13 11 34 04 f1 f1 00 00 00 00 32 48 f0 f0 22 05 13 11 33 39 23 00 01 49 97 20 19 00 00 00 26 19 00 00 00 38 12 24 00 03";
        String crcVerifyCode2 = "27 30";

        System.out.println(Crc16Util.crc16(Hex.decodeHex(dataStr1.replaceAll(" ", "")), false));
    }
}
