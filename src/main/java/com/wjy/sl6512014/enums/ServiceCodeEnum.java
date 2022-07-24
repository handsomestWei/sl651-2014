package com.wjy.sl6512014.enums;

/**
 * @author weijiayu
 * @date 2022/5/17 10:02
 */
public enum ServiceCodeEnum {

    SERVICE_SUCCESS("0", "success"), SERVICE_CRC_VERIFY_FAIL("400", "crc verify fail"),
    SERVICE_ERROR("500", "Service error.");

    private String code;
    private String message;

    ServiceCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
