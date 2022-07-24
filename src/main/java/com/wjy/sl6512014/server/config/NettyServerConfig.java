package com.wjy.sl6512014.server.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.wjy.sl6512014.biz.service.BizService;
import com.wjy.sl6512014.server.codec.FrameBodyDecoder;
import com.wjy.sl6512014.server.codec.FrameEncoder;
import com.wjy.sl6512014.server.codec.FrameHeaderDecoder;

import lombok.Data;

/**
 * @author weijiayu
 * @date 2022/7/19 15:45
 */
@Configuration
@Data
public class NettyServerConfig {

    @Value("${server_socket_ip:127.0.0.1}")
    private String ipAddr;

    @Value("${server_socket_port:11111}")
    private int port;

    @Value("${crc_verify_flag:false}")
    private boolean crcVerifyFlag;

    @Value("${header_delimiter_flag:false}")
    private boolean headerDelimiterFlag;

    @Value("${netty_so_backlog:50}")
    private int nettySoBackLog;

    @Resource
    private FrameHeaderDecoder headerDecoder;
    @Resource
    private FrameBodyDecoder bodyDecoder;
    @Resource
    private BizService bizService;
    @Resource
    private FrameEncoder frameEncoder;
}
