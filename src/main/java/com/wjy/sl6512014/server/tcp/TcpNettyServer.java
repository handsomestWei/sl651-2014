package com.wjy.sl6512014.server.tcp;

import com.wjy.sl6512014.constant.FrameConstant;
import com.wjy.sl6512014.server.codec.NettyFrameDecoder;
import com.wjy.sl6512014.server.codec.NettyHeaderDelimiterFrameDecoder;
import com.wjy.sl6512014.server.config.NettyServerConfig;
import com.wjy.sl6512014.server.handler.NettyFrameHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/7/19 10:13
 */
@Slf4j
public class TcpNettyServer {

    private NettyServerConfig config;

    public TcpNettyServer(NettyServerConfig config) {
        this.config = config;
    }

    public void run() {
        EventLoopGroup parentGroup = null;
        EventLoopGroup childGroup = null;
        try {
            // boss线程
            parentGroup = new NioEventLoopGroup(1);
            // 工作线程默认两倍核数
            childGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                // SO_BACKLOG，未连接+已连接队列和的最大长度，超过长度后请求会被拒绝
                .option(ChannelOption.SO_BACKLOG, config.getNettySoBackLog()).handler(new LoggingHandler(LogLevel.INFO))
                // 定义处理流水线
                .childOption(ChannelOption.SO_KEEPALIVE, true).childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        if (config.isHeaderDelimiterFlag()) {
                            // 1、自定义头标识符包分割处理器，解决粘包分包，分割出一个完整数据包
                            ch.pipeline().addLast(new NettyHeaderDelimiterFrameDecoder(FrameConstant.MAX_FRAME_LEN * 3,
                                FrameConstant.HEADER_START_HEX));
                        }
                        // 2、数据包byteBuff转换成报文对象
                        ch.pipeline().addLast(new NettyFrameDecoder(config.isCrcVerifyFlag(), config.getHeaderDecoder(),
                            config.getBodyDecoder()));
                        // 3、处理报文对象
                        NettyFrameHandler frameHandler = new NettyFrameHandler();
                        frameHandler.setBizService(config.getBizService());
                        frameHandler.setFrameEncoder(config.getFrameEncoder());
                        ch.pipeline().addLast(frameHandler);
                    }
                });
            ChannelFuture cf = bootstrap.bind(config.getIpAddr(), config.getPort()).sync();
            if (cf.isSuccess()) {
                log.info("tcp netty服务端启动成功");
            }
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (parentGroup != null) {
                parentGroup.shutdownGracefully();
            }
            if (childGroup != null) {
                childGroup.shutdownGracefully();
            }
        }
    }
}
