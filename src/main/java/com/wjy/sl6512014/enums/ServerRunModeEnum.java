package com.wjy.sl6512014.enums;

/**
 * 服务端运行模式
 * 
 * @author weijiayu
 * @date 2022/7/19 15:17
 */
public enum ServerRunModeEnum {

    SERVER_MODE_TCP("tcp"), SERVER_MODE_UDP("udp");

    private String code;

    ServerRunModeEnum(String code) {
        this.code = code;
    }

    public static ServerRunModeEnum getServerRunModeEnum(String code) {
        for (ServerRunModeEnum modeEnum : ServerRunModeEnum.values()) {
            if (modeEnum.code.equals(code)) {
                return modeEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }
}
