# sl651-2014
sl651-2014水文通讯协议netty服务端实现

## 功能
+ 支持M1、M2、M4三种通讯方式。
+ 实现了常用的2F链路维持、32定时上报两种功能码处理，其他可扩展。

## 配置参数
[配置文件](https://github.com/handsomestWei/sl651-2014/blob/main/src/main/resources/application.properties)
| 配置项 | 说明 |
|--|--|
| server_socket_ip | netty服务端ip |
| server_socket_port | netty服务端监听端口 |
| server_run_mode | netty服务端运行模式，支持tcp或udp |
| crc_verify_flag | 是否开启报文crc校验。默认false关闭 |
| header_delimiter_flag | 包分割开关，解决粘包分包。默认false关闭 |

## 使用说明
+ [服务端启动](https://github.com/handsomestWei/sl651-2014/blob/main/src/main/java/com/wjy/sl6512014/Sl6512014Application.java)
+ [模拟客户端](https://github.com/handsomestWei/sl651-2014/blob/main/src/main/java/com/wjy/sl6512014/client/tcp/TcpClientStart.java) 可自行调整[数据上报](https://github.com/handsomestWei/sl651-2014/blob/main/src/main/java/com/wjy/sl6512014/client/tcp/handler/TcpNettyClientHandler.java)
+ [自定义业务处理](https://github.com/handsomestWei/sl651-2014/blob/main/src/main/java/com/wjy/sl6512014/biz/service/BizService.java) 可按需实现BizService接口

