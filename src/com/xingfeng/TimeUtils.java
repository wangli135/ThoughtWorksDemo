package com.xingfeng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Xingfeng on 2017-09-11.
 */
public class TimeUtils {

    /**
     * 判断日期是否正确
     *
     * @param date
     * @return
     */
    public static boolean isDateRight(String date) {
        return conertDate(date) != null;
    }

    public static Date conertDate(String s) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
        }
        return date;

    }

    /**
     * 判断时间是否正确
     *
     * @param time
     * @return
     */
    public static boolean isRightTime(String time) {

        int[] result = convertTime(time);
        if (result[0] < result[1] && result[0] >= 9 && result[1] <= 22)
            return true;
        return false;
    }

    /**
     * 转换时间
     *
     * @param time
     * @return time[0]表示起始时间，time[1]表示结束时间
     */
    public static int[] convertTime(String time) {

        int[] result = new int[2];
        String[] array = time.split("~");

        if (array.length != 2)
            return result;

        for (int i = 0; i < array.length; i++) {

            String[] timeArray = array[i].split(":");
            if (timeArray.length != 2)
                break;

            if (!timeArray[1].equals("00"))
                break;

            int intTime = 0;

            try {
                intTime = Integer.parseInt(timeArray[0]);
            } catch (Exception e) {
                break;
            }

            result[i] = intTime;

        }


        return result;
    }


}
