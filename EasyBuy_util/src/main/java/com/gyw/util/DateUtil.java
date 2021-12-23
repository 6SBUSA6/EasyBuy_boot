package com.gyw.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 高源蔚
 * @date 2021/12/20-19:59
 * @describe
 */
public class DateUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

   public static String getYMDate(){
        Date date = new Date();
        String ym = sdf.format(date);
        return ym;
    }

}
