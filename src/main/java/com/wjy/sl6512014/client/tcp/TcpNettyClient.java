package com.wjy.sl6512014.client.tcp;

import com.wjy.sl6512014.client.tcp.handler.TcpNettyClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/18 16:08
 */
@Slf4j
public class TcpNettyClient {

    private ChannelFuture channelFuture;
    private String address;
    private int port;

    public TcpNettyClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap clientBootstrap = new Bootstrap();
            clientBootstrap = clientBootstrap.group(workerGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true).handler(new TcpNettyClientHandler());
            channelFuture = clientBootstrap.connect(address, port).sync();
            if (channelFuture.isSuccess()) {
                log.info("tcp netty客户端连接服务端成功");
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
