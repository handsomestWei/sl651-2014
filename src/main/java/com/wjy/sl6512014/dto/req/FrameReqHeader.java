package com.wjy.sl6512014.dto.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/5/11 17:54
 */
@Data
@ToString
public class FrameReqHeader {

    private String centerStationAddr;
    private String remoteStationAddr;
    private String pwd;
    private String funcCode;
    private Long bodyLen;
    private boolean m3Mode;
    // 包总数，m3模式才有
    private Integer frameCnt;
    // 包序列号，m3模式才有
    private String frameSerialNo;
}
