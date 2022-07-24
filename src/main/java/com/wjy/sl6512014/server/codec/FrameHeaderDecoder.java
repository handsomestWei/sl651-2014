package com.wjy.sl6512014.server.codec;

import com.wjy.sl6512014.dto.req.FrameReqHeader;

/**
 * @author weijiayu
 * @date 2022/7/19 18:27
 */
public interface FrameHeaderDecoder {

    FrameReqHeader decodeHeader(char[] frame);
}
