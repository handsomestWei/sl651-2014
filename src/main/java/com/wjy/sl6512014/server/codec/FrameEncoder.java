package com.wjy.sl6512014.server.codec;

import com.wjy.sl6512014.dto.req.FrameReqWrapper;

import io.netty.buffer.ByteBuf;

/**
 * @author weijiayu
 * @date 2022/7/22 9:10
 */
public interface FrameEncoder {

    ByteBuf encodeRsp(FrameReqWrapper frameReqWrapper);
}
