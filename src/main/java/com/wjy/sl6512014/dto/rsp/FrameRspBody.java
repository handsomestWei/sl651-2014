package com.wjy.sl6512014.dto.rsp;

import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/7/19 10:17
 */
@Data
@ToString
public class FrameRspBody {

    private HashMap<String, List<FrameRspBodyElement>> elementMap;
}
