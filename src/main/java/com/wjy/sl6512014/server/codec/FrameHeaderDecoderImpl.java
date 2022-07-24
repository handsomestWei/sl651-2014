package com.wjy.sl6512014.server.codec;

import org.springframework.stereotype.Service;

import com.wjy.sl6512014.dto.req.FrameReqHeader;
import com.wjy.sl6512014.util.FrameUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/19 18:21
 */
@Service
@Slf4j
public class FrameHeaderDecoderImpl implements FrameHeaderDecoder {

    @Override
    public FrameReqHeader decodeHeader(char[] frame) {
        FrameReqHeader header = new FrameReqHeader();
        // 跳过帧起始的固定两字节
        // 中心站
        header.setCenterStationAddr(FrameUtil.getHeaderCenterStationAddr(frame));
        // 遥测站
        header.setRemoteStationAddr(FrameUtil.getHeaderRemoteStationAddr(frame));
        // 密码
        header.setPwd(FrameUtil.getHeaderPwd(frame));
        // 功能码
        header.setFuncCode(FrameUtil.getHeaderFuncCode(frame));
        // 报文正文长度
        header.setBodyLen(FrameUtil.getBodyLen(frame));
        // 传输是否为M3多包模式
        header.setM3Mode(FrameUtil.isM3Mode(frame));
        if (header.isM3Mode()) {
            // TODO 解析m3模式报文头特殊参数
            header.setFrameCnt(0);
            header.setFrameSerialNo(null);
        }
        return header;
    }
}
