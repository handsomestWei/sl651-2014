package com.wjy.sl6512014.client.udp;

/**
 * @author weijiayu
 * @date 2022/7/18 11:58
 */
public class UdpClientStart {

    public static void main(String[] args) throws Exception {
        String ipAddr = "127.0.0.1";
        int port = 11111;

        UdpNettyClient udpClient = new UdpNettyClient(ipAddr, port);
        udpClient.connect();
    }
}
