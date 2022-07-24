package com.wjy.sl6512014.constant;

/**
 * @author weijiayu
 * @date 2022/5/11 16:53
 */
public class FrameConstant {

    public static final Integer MAX_FRAME_LEN = 4095;

    // 帧起始符，2字节
    public static final String HEADER_START_HEX = "7E7E";

    // M3模式报文起始符SYN，1字节
    public static final String HEADER_START_BODY_M3_FLAG_HEX = "16";

    // 报文控制符EOT，传输结束退出通信
    public static final String HEADER_RSP_BODY_END_EOT_HEX = "04";

    // 传输正文起始标记STX
    public static final String HEADER_RSP_BODY_END_STX_HEX = "02";

    // 下行报文标识和0长报文体
    public static final String HEADER_RSP_DOWN_SYMBOL_AND_ZERO_LEN_HEX = "8000";

    // 遥测站分类码：48河道
    public static final String BODY_STATION_FUN_CODE_REVIER = "48";

    // 遥测站分类码：50降水
    public static final String BODY_STATION_FUN_CODE_RAIN = "50";

    // 标识引导符：39河道瞬时水位
    public static final String BODY_ELEMENT_REVIER = "39";

    // 标识引导符：20当前降雨量
    public static final String BODY_ELEMENT_RAIN = "20";
}
