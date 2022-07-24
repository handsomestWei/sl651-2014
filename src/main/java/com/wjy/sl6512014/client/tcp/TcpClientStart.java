package com.wjy.sl6512014.client.tcp;

/**
 * @author weijiayu
 * @date 2022/7/18 16:24
 */
public class TcpClientStart {

    public static void main(String[] args) throws Exception {
        String ipAddr = "127.0.0.1";
        int port = 11111;

        TcpNettyClient tcpClient = new TcpNettyClient(ipAddr, port);
        tcpClient.connect();
    }
}
