package com.wjy.sl6512014.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author weijiayu
 * @date 2022/5/17 17:26
 */
public class DateUtil {

    public static final String FORMAT_YMD_HM = "yyyyMMddHHmm";
    public static final String FORMAT_YMD_HMS = "yyyyMMddHHmmss";

    public static Date strYMDHM2Date(String dateStr) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat(FORMAT_YMD_HM);
            return sd.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date strYMDHMS2Date(String dateStr) {
        try {
            SimpleDateFormat sd = new SimpleDateFormat(FORMAT_YMD_HMS);
            return sd.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
