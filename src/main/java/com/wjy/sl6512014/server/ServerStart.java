package com.wjy.sl6512014.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.wjy.sl6512014.enums.ServerRunModeEnum;
import com.wjy.sl6512014.server.config.NettyServerConfig;
import com.wjy.sl6512014.server.tcp.TcpNettyServer;
import com.wjy.sl6512014.server.udp.UdpNettyServer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2022/5/13 10:11
 */
@Component
@Slf4j
public class ServerStart implements CommandLineRunner {

    @Autowired
    private NettyServerConfig config;

    @Value("${server_run_mode:tcp}")
    private String runMode;

    @Override
    public void run(String... args) throws Exception {
        ServerRunModeEnum runModeEnum = ServerRunModeEnum.getServerRunModeEnum(runMode);
        if (runModeEnum == null) {
            throw new Exception("un support server run mod " + runMode);
        }
        switch (runModeEnum) {
            case SERVER_MODE_TCP:
                new TcpNettyServer(config).run();
                break;
            case SERVER_MODE_UDP:
                new UdpNettyServer(config).run();
                break;
            default:
                throw new Exception("un support server run mod " + runMode);
        }
    }
}
