package com.wjy.sl6512014.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.wjy.sl6512014.constant.FrameConstant;
import com.wjy.sl6512014.dto.req.FrameReqBodyElement;

/**
 * 水文协议报文工具类
 * 
 * @author weijiayu
 * @date 2022/5/11 22:44
 */
public class FrameUtil {

    /**
     * 判断报文是否为M3多包传输模式
     * 
     * @author weijiayu
     * @date 2022/5/12 11:32
     * @param frame
     * @return boolean
     */
    public static boolean isM3Mode(char[] frame) {
        return FrameConstant.HEADER_START_BODY_M3_FLAG_HEX.equalsIgnoreCase(HexStringUtil.int2HexStr(frame[13]));
    }

    /**
     * 获取中心站地址
     * 
     * @author weijiayu
     * @date 2022/5/12 11:39
     * @param frame
     * @return java.lang.String
     */
    public static String getHeaderCenterStationAddr(char[] frame) {
        return HexStringUtil.int2HexStr(frame[2]);
    }

    /**
     * 获取遥测站地址
     * 
     * @author weijiayu
     * @date 2022/5/12 11:42
     * @param frame
     * @return java.lang.String
     */
    public static String getHeaderRemoteStationAddr(char[] frame) {
        return HexStringUtil.int2HexStr(frame, 3, 7);
    }

    /**
     * 获取密码
     * 
     * @author weijiayu
     * @date 2022/5/12 11:44
     * @param frame
     * @return java.lang.String
     */
    public static String getHeaderPwd(char[] frame) {
        return HexStringUtil.int2HexStr(frame, 8, 9);
    }

    /**
     * 获取功能码
     * 
     * @author weijiayu
     * @date 2022/5/12 11:46
     * @param frame
     * @return java.lang.String
     */
    public static String getHeaderFuncCode(char[] frame) {
        return HexStringUtil.int2HexStr(frame[10]);
    }

    /**
     * 获取报文正文长度
     * 
     * @author weijiayu
     * @date 2022/5/12 11:48
     * @param frame
     * @return java.lang.Long
     */
    public static Long getBodyLen(char[] frame) {
        return Long.parseLong(HexStringUtil.int2HexStr(frame, 11, 12), 16);
    }

    public static char[] getM3Body(char[] frame, int bodyLen) {
        char[] bodyFrame = new char[bodyLen];
        // M3报文头比M124多3个字节
        System.arraycopy(frame, (2 + 1 + 5 + 2 + 1 + 2 + 1 + 3), bodyFrame, 0, bodyLen);
        return bodyFrame;
    }

    public static char[] getM124Body(char[] frame, int bodyLen) {
        char[] bodyFrame = new char[bodyLen];
        System.arraycopy(frame, (2 + 1 + 5 + 2 + 1 + 2 + 1), bodyFrame, 0, bodyLen);
        return bodyFrame;
    }

    /**
     * 获取报文CRC16校验码
     * 
     * @author weijiayu
     * @date 2022/5/13 16:47
     * @param frame
     * @return java.lang.String
     */
    public static String getCRC16Code(char[] frame) {
        // 位于报文末尾，2字节
        return HexStringUtil.int2HexStr(frame, frame.length - 2, frame.length - 1);
    }

    public static String getBodyEndSymbol(char[] frame) {
        // 位于报文末尾，1字节
        return HexStringUtil.int2HexStr(frame, frame.length - 3, frame.length - 3);
    }

    /**
     * 报文crc16校验
     * 
     * @author weijiayu
     * @date 2022/5/16 10:03
     * @param frame
     * @return boolean
     */
    public static boolean verifyCRC16Code(char[] frame) {
        String packageData = new String(frame);
        return packageData.substring(packageData.length() - 4)
            .equals(Crc16Util.crc16(packageData.substring(0, packageData.length() - 4).getBytes(), false));
    }

    public static String computeCRC16Code(char[] data) {
        return Crc16Util.crc16(new String(data).getBytes(), false);
    }

    public static int getBodyElementByteSize(char c) {
        // 16进制字符串转十进制，字节高5位为数据字节数
        return (Integer.parseInt(Integer.toHexString(c), 16) & 0xf8) >> 3;
    }

    public static int getBodyElementDecimalSize(char c) {
        // 16进制字符串转十进制，字节低3位为小数位数
        return Integer.parseInt(Integer.toHexString(c), 16) & 0x07;
    }

    public static HashMap<String, List<FrameReqBodyElement>> getBodyElement(char[] bodyElementFrame) {
        // TODO 优化解析，处理扩展字段
        HashMap<String, List<FrameReqBodyElement>> elementMap = new HashMap<>();
        for (int i = 0; i < bodyElementFrame.length;) {
            // 每组数据，前两字节为标识符，其中第一个字节为标识引导符，第二个字节定义数据信息
            String typeCode = Integer.toHexString(bodyElementFrame[i]);
            char elementInfoChar = bodyElementFrame[i + 1];
            List<FrameReqBodyElement> elementList = elementMap.get(typeCode);
            if (elementList == null) {
                elementList = new LinkedList<>();
                elementMap.put(typeCode, elementList);
            }
            int dataSize = getBodyElementByteSize(elementInfoChar);
            FrameReqBodyElement element =
                new FrameReqBodyElement(typeCode, dataSize, getBodyElementDecimalSize(elementInfoChar),
                    HexStringUtil.int2HexStr(bodyElementFrame, i + 2, i + 2 + dataSize - 1));
            elementList.add(element);
            i += (2 + element.getDataSize());
        }
        return elementMap;
    }

    public static void main(String[] args) throws Exception {
        // 河道瞬时水位39 22 00 00 98 36=>98.36m
        // 前两字节为标识符，其中第一个字节为标识引导符，39为河道水位；第二个字节22定义数据信息，后面数据有4个字节，其中小数位有2位数字
        int c = 0x22;
        System.out.println(getBodyElementByteSize((char)c));
        System.out.println(getBodyElementDecimalSize((char)c));
    }
}
