package com.wjy.sl6512014.dto.req;

import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/5/11 17:55
 */
@Data
@ToString
public class FrameReqBody {
    // 流水号
    private Long serialNo;
    // 发报时间YYMMDDHHmmSS
    private String sendTime;
    // 遥测站地址
    private String remoteStationAddr;
    // 遥测站分类编码
    private String remoteStationTypeCode;
    // 观测时间YYMMDDHHmm
    private String observeTime;
    // 遥测信息
    private HashMap<String, List<FrameReqBodyElement>> elementMap;
}
