package com.wjy.sl6512014.dto.rsp;

import lombok.Data;
import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/7/19 10:16
 */
@Data
@ToString
public class FrameRsp {

    private FrameRspHeader header;
    private FrameRspBody body;
    // 报文结束符
    private String bodyEndSymbol;
    // CRC16校验码
    private String crcCode;
}
