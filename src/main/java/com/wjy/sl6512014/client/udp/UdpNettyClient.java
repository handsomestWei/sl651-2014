package com.wjy.sl6512014.client.udp;

import com.wjy.sl6512014.client.udp.handler.UdpNettyClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/18 10:49
 */
@Slf4j
public class UdpNettyClient {

    private ChannelFuture channelFuture;
    private String address;
    private int port;

    public UdpNettyClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap = clientBootstrap.group(workerGroup);
            clientBootstrap = clientBootstrap.channel(NioDatagramChannel.class);
            clientBootstrap = clientBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            clientBootstrap = clientBootstrap.handler(new UdpNettyClientHandler());
            channelFuture = clientBootstrap.connect(address, port).sync();
            if (channelFuture.isSuccess()) {
                log.info("udp netty客户端连接服务端成功");
            }
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (workerGroup != null) {
                workerGroup.shutdownGracefully();
            }
        }
    }

}
