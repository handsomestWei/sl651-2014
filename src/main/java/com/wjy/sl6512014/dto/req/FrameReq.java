package com.wjy.sl6512014.dto.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/5/11 17:02
 */
@Data
@ToString
public class FrameReq {

    private FrameReqHeader header;
    private FrameReqBody body;
    // 报文结束符
    private String bodyEndSymbol;
    // CRC16校验码
    private String crcCode;
}
