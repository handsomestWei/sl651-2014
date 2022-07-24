package com.wjy.sl6512014.client.tcp.handler;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/18 16:19
 */
@Slf4j
public class TcpNettyClientHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String data =
            "7e7e010011111112000032002b02002b200321153057f1f1001111111248f0f020032115303922000098362019000000261900000038122400037b54";
        data += data;
        ByteBuf byteBuf = Unpooled.copiedBuffer(Hex.decodeHex(data));
        ctx.writeAndFlush(byteBuf);
        log.debug("tcp netty client send msg id>>>" + ctx.channel().id().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        log.debug("tcp netty client receive msg id>>>" + ctx.channel().id().toString() + " msg content>>>"
            + new String(buffer));
    }
}
