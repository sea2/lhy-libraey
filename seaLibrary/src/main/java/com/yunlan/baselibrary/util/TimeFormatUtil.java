package com.yunlan.baselibrary.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeFormatUtil {


    public static String getTime() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime_MMDD() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static String getData_Time(Date date) {//截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getData_Time_History(Date date) {//截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }



    public static Long getTimeTurnMs(String time) {
        if (TextUtils.isEmpty(time))
            return 0l;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(time.toString()).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    public static String getNormalTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date(value));
        return time;
    }

    public static String getHomeQuesDyTime(long value) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        String time = format.format(new Date(value));
        return time;
    }

    public static String getDate(String dateMills) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        long birthdayMills;
        if (dateMills != null && !TextUtils.isEmpty(dateMills)) {
            birthdayMills = Long.parseLong(dateMills);
            return format.format(new Date(birthdayMills));
        } else {
            return null;
        }
    }


    // 字符串转时间缀
    public static long stringToDate(String string) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = Date(format.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static Date Date(Date date) {
        Date datetimeDate;
        datetimeDate = new Date(date.getTime());
        return datetimeDate;
    }

    /**
     * 获取天数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static float getDateNmu(String startTime, String endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = dateFormat.parse(endTime);
            Date date2 = dateFormat.parse(startTime);
            float day = (date1.getTime() - date2.getTime()) / (24 * 3600 * 1000);
            return day;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
