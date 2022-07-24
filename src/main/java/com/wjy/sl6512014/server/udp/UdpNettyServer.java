package com.wjy.sl6512014.server.udp;

import com.wjy.sl6512014.server.codec.NettyFrameDecoder;
import com.wjy.sl6512014.server.config.NettyServerConfig;
import com.wjy.sl6512014.server.handler.NettyUdpServerHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/6/7 17:22
 */
@Slf4j
public class UdpNettyServer {

    private NettyServerConfig config;

    public UdpNettyServer(NettyServerConfig config) {
        this.config = config;
    }

    public void run() {
        NioEventLoopGroup nioEventLoopGroup = null;
        try {
            nioEventLoopGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            // SO_BACKLOG，未连接+已连接队列和的最大长度，超过长度后请求会被拒绝
            bootstrap.group(nioEventLoopGroup).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BACKLOG, config.getNettySoBackLog()).option(ChannelOption.SO_BROADCAST, false)
                .handler(new LoggingHandler(LogLevel.INFO)).handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        NettyUdpServerHandler handler =
                            new NettyUdpServerHandler((new NettyFrameDecoder(config.isCrcVerifyFlag(),
                                config.getHeaderDecoder(), config.getBodyDecoder())));
                        handler.setBizService(config.getBizService());
                        handler.setFrameEncoder(config.getFrameEncoder());
                        ch.pipeline().addLast(handler);
                    }
                });
            ChannelFuture cf = bootstrap.bind(config.getIpAddr(), config.getPort()).sync();
            if (cf.isSuccess()) {
                log.info("udp netty服务端启动成功");
            }
            cf.channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (nioEventLoopGroup != null) {
                nioEventLoopGroup.shutdownGracefully();
            }
        }
    }
}
