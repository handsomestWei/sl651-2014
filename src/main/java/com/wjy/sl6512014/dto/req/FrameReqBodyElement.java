package com.wjy.sl6512014.dto.req;

import lombok.Data;
import lombok.ToString;

/**
 * @author weijiayu
 * @date 2022/5/16 17:29
 */
@Data
@ToString
public class FrameReqBodyElement {

    // 标识符引导符
    private String typeCode;
    // 数据字节数
    private Integer dataSize;
    // 小数位数
    private Integer decimalSize;
    // 原始数据
    private String originalData;
    // 实际值
    private Double val;

    public FrameReqBodyElement(String typeCode, Integer dataSize, Integer decimalSize, String originalData) {
        this.typeCode = typeCode;
        this.dataSize = dataSize;
        this.decimalSize = decimalSize;
        this.originalData = originalData;
        try {
            val = calDoubleVal();
        } catch (Exception e) {
            // ignore
        }
    }

    public Double calDoubleVal() {
        // 插入小数点并转换为双精度类型
        return Double.parseDouble(new StringBuffer(originalData).insert(dataSize * 2 - decimalSize, ".").toString());
    }
}
