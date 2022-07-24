package com.wjy.sl6512014.client.udp.handler;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/18 11:48
 */
@Slf4j
public class UdpNettyClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String data =
            "7e7e010011111112000032002b02002b200321153057f1f1001111111248f0f020032115303922000098362019000000261900000038122400037b54";
        ByteBuf byteBuf = Unpooled.copiedBuffer(Hex.decodeHex(data));
        ctx.writeAndFlush(byteBuf);
        log.debug("udp netty client send msg id>>>" + ctx.channel().id().toString());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws Exception {
        ByteBuf buf = datagramPacket.content();
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        log.debug("udp netty client receive msg id>>>" + ctx.channel().id().toString() + " msg content>>>"
            + new String(buffer));
    }
}
