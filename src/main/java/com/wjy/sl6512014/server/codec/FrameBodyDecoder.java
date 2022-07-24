package com.wjy.sl6512014.server.codec;

import com.wjy.sl6512014.dto.req.FrameReqBody;
import com.wjy.sl6512014.enums.FrameFuncCodeEnum;

/**
 * @author weijiayu
 * @date 2022/7/19 18:24
 */
public interface FrameBodyDecoder {

    FrameReqBody decodeM3Body(char[] bodyFrame);

    FrameReqBody decodeM124Body(char[] bodyFrame, FrameFuncCodeEnum funcEnum);
}
