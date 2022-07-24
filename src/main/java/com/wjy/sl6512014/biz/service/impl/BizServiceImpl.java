package com.wjy.sl6512014.biz.service.impl;

import org.springframework.stereotype.Service;

import com.wjy.sl6512014.biz.service.BizService;
import com.wjy.sl6512014.dto.req.FrameReqWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/19 17:06
 */
@Service
@Slf4j
public class BizServiceImpl implements BizService {

    @Override
    public boolean handler(FrameReqWrapper frameReqWrapper) {
        return false;
    }
}
