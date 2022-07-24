package com.wjy.sl6512014.biz.service;

import com.wjy.sl6512014.dto.req.FrameReqWrapper;

/**
 * 业务处理接口
 * 
 * @author weijiayu
 * @date 2022/7/19 17:04
 */
public interface BizService {

    boolean handler(FrameReqWrapper frameReqWrapper);
}
