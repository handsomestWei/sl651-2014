package com.wjy.sl6512014.server.codec;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import com.wjy.sl6512014.constant.FrameConstant;
import com.wjy.sl6512014.dto.req.FrameReq;
import com.wjy.sl6512014.dto.req.FrameReqHeader;
import com.wjy.sl6512014.dto.req.FrameReqWrapper;
import com.wjy.sl6512014.enums.FrameFuncCodeEnum;
import com.wjy.sl6512014.enums.ServiceCodeEnum;
import com.wjy.sl6512014.util.FrameUtil;
import com.wjy.sl6512014.util.HexStringUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/5/13 9:49
 */
@Slf4j
public class NettyFrameDecoder extends ByteToMessageDecoder {

    private boolean crcVerifyFlag = false;
    private FrameHeaderDecoder headerDecoder;
    private FrameBodyDecoder bodyDecoder;

    public NettyFrameDecoder(boolean crcVerifyFlag, FrameHeaderDecoder headerDecoder, FrameBodyDecoder bodyDecoder) {
        this.crcVerifyFlag = crcVerifyFlag;
        this.headerDecoder = headerDecoder;
        this.bodyDecoder = bodyDecoder;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
        throws Exception {
        FrameReqWrapper wrapper = this.decodeFrame(byteBuf);
        list.add(wrapper);
    }

    public FrameReqWrapper decodeFrame(ByteBuf byteBuf) {
        FrameReqWrapper wrapper = new FrameReqWrapper();
        FrameReq frameReq = new FrameReq();
        wrapper.setReqDto(frameReq);
        try {
            String frameHexStr = ByteBufUtil.hexDump(byteBuf);
            log.debug("sl651-2014 frame hex content>>>" + frameHexStr);
            wrapper.setOriginalFrame(frameHexStr);
            char[] frame = HexStringUtil.hexStr2CharArray(frameHexStr);
            if (crcVerifyFlag && !FrameUtil.verifyCRC16Code(frame)) {
                log.warn("sl651-2014 frame crc verify fail>>>" + frameHexStr);
                wrapper.setWrapCodeEnum(ServiceCodeEnum.SERVICE_CRC_VERIFY_FAIL);
                return wrapper;
            }
            FrameReqHeader header = headerDecoder.decodeHeader(frame);
            frameReq.setHeader(header);
            int bodyLen = header.getBodyLen().intValue();
            if (header.isM3Mode()) {
                frameReq.setBody(bodyDecoder.decodeM3Body(FrameUtil.getM3Body(frame, bodyLen)));
            } else {
                frameReq.setBody(bodyDecoder.decodeM124Body(FrameUtil.getM124Body(frame, bodyLen),
                    FrameFuncCodeEnum.getFrameFuncEnum(header.getFuncCode())));
            }
            frameReq.setBodyEndSymbol(FrameUtil.getBodyEndSymbol(frame));
            frameReq.setCrcCode(FrameUtil.getCRC16Code(frame));
            wrapper.setWrapCodeEnum(ServiceCodeEnum.SERVICE_SUCCESS);
            log.debug("sl651-2014 frame decode content>>>" + frameReq.toString());
        } catch (Exception e) {
            log.error("sl651-2014 frame decode error", e);
            wrapper.setWrapCodeEnum(ServiceCodeEnum.SERVICE_ERROR);
        } finally {
            byteBuf.skipBytes(byteBuf.readableBytes());
        }
        return wrapper;
    }

    public static void main(String[] args) throws Exception {
        String data =
            "7e7e010011111112000032002b02002b200321153057f1f1001111111248f0f020032115303922000098362019000000261900000038122400037b54";
        ByteBuf buffer = Unpooled.copiedBuffer(Hex.decodeHex(data));
        System.out.println(ByteBufUtil.hexDump(buffer));

        NettyFrameDecoder decoder =
            new NettyFrameDecoder(false, new FrameHeaderDecoderImpl(), new FrameBodyDecoderImpl());
        FrameReqWrapper wrapper = decoder.decodeFrame(buffer);
        System.out.println(
            wrapper.getReqDto().getBody().getElementMap().get(FrameConstant.BODY_ELEMENT_REVIER).get(0).getVal());
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmm");
        System.out.println(sd.parse(wrapper.getReqDto().getBody().getObserveTime()));
        return;
    }
}
