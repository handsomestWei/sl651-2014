package com.wjy.sl6512014.server.handler;

import com.wjy.sl6512014.biz.service.BizService;
import com.wjy.sl6512014.dto.req.FrameReqWrapper;
import com.wjy.sl6512014.enums.ServiceCodeEnum;
import com.wjy.sl6512014.server.codec.FrameEncoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author weijiayu
 * @date 2022/5/17 10:21
 */
@Slf4j
public class NettyFrameHandler extends ChannelInboundHandlerAdapter {

    private BizService bizService;
    private FrameEncoder frameEncoder;

    public void setBizService(BizService bizService) {
        this.bizService = bizService;
    }

    public void setFrameEncoder(FrameEncoder frameEncoder) {
        this.frameEncoder = frameEncoder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FrameReqWrapper frameReqWrapper = (FrameReqWrapper)msg;
            if (frameReqWrapper.getWrapCodeEnum() != ServiceCodeEnum.SERVICE_SUCCESS) {
                // 报文解析失败，结束处理，等待重发
                return;
            }
            // 自定义业务处理
            if (bizService != null) {
                bizService.handler(frameReqWrapper);
            }
            // 下行报文响应
            ByteBuf byteBuf = null;
            if (frameEncoder != null) {
                byteBuf = frameEncoder.encodeRsp(frameReqWrapper);
            }
            if (byteBuf != null) {
                ctx.writeAndFlush(byteBuf);
            }
        } catch (Exception e) {
            log.error("netty tcp服务端处理失败", e);
        } finally {
            // 手动回收对象，结束流水线
            ReferenceCountUtil.release(msg);
        }
    }
}
