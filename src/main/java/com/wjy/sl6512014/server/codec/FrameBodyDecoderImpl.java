package com.wjy.sl6512014.server.codec;

import org.springframework.stereotype.Service;

import com.wjy.sl6512014.dto.req.FrameReqBody;
import com.wjy.sl6512014.enums.FrameFuncCodeEnum;
import com.wjy.sl6512014.util.FrameM124Util;
import com.wjy.sl6512014.util.FrameUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/19 18:25
 */
@Service
@Slf4j
public class FrameBodyDecoderImpl implements FrameBodyDecoder {

    @Override
    public FrameReqBody decodeM3Body(char[] bodyFrame) {
        return null;
    }

    @Override
    public FrameReqBody decodeM124Body(char[] bodyFrame, FrameFuncCodeEnum funcEnum) {
        if (funcEnum == null) {
            return null;
        }
        switch (funcEnum) {
            case REGULAR_REPORT:
                return decodeM124BodyByRegularReport(bodyFrame);
            case HEART_BEAT:
                return decodeM124BodyByHeartBeat(bodyFrame);
            default:
                return null;
        }
    }

    // 定时上报
    private FrameReqBody decodeM124BodyByRegularReport(char[] bodyFrame) {
        FrameReqBody body = new FrameReqBody();
        body.setSerialNo(FrameM124Util.getBodySerialNo(bodyFrame));
        body.setSendTime(FrameM124Util.getBodySendTime(bodyFrame));
        body.setRemoteStationAddr(FrameM124Util.getBodyRemoteStationAddr(bodyFrame));
        body.setRemoteStationTypeCode(FrameM124Util.getRemoteStationTypeCode(bodyFrame));
        body.setObserveTime(FrameM124Util.getBodyObserveTime(bodyFrame));
        // TODO 解析自定义扩展参数
        body.setElementMap(FrameUtil.getBodyElement(FrameM124Util.getBodyElementByRegularReport(bodyFrame)));
        return body;
    }

    // 链路维持
    private FrameReqBody decodeM124BodyByHeartBeat(char[] bodyFrame) {
        FrameReqBody body = new FrameReqBody();
        body.setSerialNo(FrameM124Util.getBodySerialNo(bodyFrame));
        body.setSendTime(FrameM124Util.getBodySendTime(bodyFrame));
        return body;
    }
}
