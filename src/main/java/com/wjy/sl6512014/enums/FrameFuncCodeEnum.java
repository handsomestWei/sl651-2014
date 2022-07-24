package com.wjy.sl6512014.enums;

/**
 * @author weijiayu
 * @date 2022/7/19 17:59
 */
public enum FrameFuncCodeEnum {

    HEART_BEAT("2F", "链路维持"), REGULAR_REPORT("32", "定时上报");

    private String code;
    private String desc;

    FrameFuncCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FrameFuncCodeEnum getFrameFuncEnum(String code) {
        for (FrameFuncCodeEnum funCodeEnum : FrameFuncCodeEnum.values()) {
            if (funCodeEnum.code.equals(code)) {
                return funCodeEnum;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
