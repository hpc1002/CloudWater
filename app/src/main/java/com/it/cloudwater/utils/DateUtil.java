package com.it.cloudwater.utils;

import java.text.SimpleDateFormat;

/**
 * Created by hpc on 2017/8/29.
 */

public class DateUtil {
    public static String toDate(long number) {
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format(number);
        System.out.println("Format To String(Date):" + d);
        return d;
    }
}
