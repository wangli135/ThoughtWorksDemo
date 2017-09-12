package com.xingfeng;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Xingfeng on 2017-09-11.
 */
public class BadmintonBooking {

    public static final String SUCCESS = "Success: the booking is accepted";
    public static final String ERROR = "Error: the booking is invalid!";
    public static final String CONFLICT = "Error:the booking conflicts with existing bookings!";
    public static final String CANCEL_ERROR = "Error:the booking being cancelled dose not exist!";


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, BadmintonField> badmintonFieldMap = new HashMap<>();
        badmintonFieldMap.put("A", new BadmintonField());
        badmintonFieldMap.put("B", new BadmintonField());
        badmintonFieldMap.put("C", new BadmintonField());
        badmintonFieldMap.put("D", new BadmintonField());

        while (true) {

            String line = scanner.nextLine().trim();
            if (line.equals("")) {
                break;
            }

            String[] array = line.split(" ");
            if (!isRight(array)) {
                System.out.println(ERROR);
                continue;
            }

            String user = array[0];
            Date date = TimeUtils.conertDate(array[1]);
            int[] time = TimeUtils.convertTime(array[2]);
            String field = array[3];
            BadmintonField badmintonField = badmintonFieldMap.get(field);
            int flag = 0;
            if (array.length == 4) {
                flag = badmintonField.orderField(date, time[0], time[1], new Order(user));
                if (flag == 1) {
                    System.out.println(CONFLICT);
                } else {
                    System.out.println(SUCCESS);
                }
            } else {
                flag = badmintonField.cancelField(date, time[0], time[1], new Order(user));
                if (flag == 1) {
                    System.out.println(CANCEL_ERROR);
                } else {
                    System.out.println(SUCCESS);
                }
            }


        }

        printResult(badmintonFieldMap);

    }

    private static void printResult(Map<String, BadmintonField> badmintonFieldMap) {

        int total = 0;
        System.out.println("收入汇总");
        System.out.println("---");

        BadmintonField badmintonField = null;

        String[] field = new String[]{"A", "B", "C", "D"};
        for (int i = 0; i < field.length; i++) {
            System.out.println("场地:" + field[i]);
            badmintonField = badmintonFieldMap.get(field[i]);
            total += badmintonField.getCostAndPrintCmds();
            if (i != field.length-1)
                System.out.println();
        }
        System.out.println("---");
        System.out.println("总计:" + total);


    }

    /**
     * 判断输入是否合法
     *
     * @param array
     * @return
     */
    private static boolean isRight(String[] array) {

        if (array.length == 4)
            return isOrderRight(array);
        else if (array.length == 5)
            return isCancelRight(array);
        else
            return false;

    }

    /**
     * 判断取消参数是否合法
     *
     * @param array
     * @return
     */
    private static boolean isCancelRight(String[] array) {

        String user = array[0];
        String date = array[1];
        String time = array[2];
        String field = array[3];
        String cancel = array[4];

        if (!TimeUtils.isDateRight(date))
            return false;

        if (!TimeUtils.isRightTime(time))
            return false;

        if (!"C".equals(cancel))
            return false;

        return true;


    }


    /**
     * 判断预定参数是否合法
     *
     * @param array
     * @return
     */
    private static boolean isOrderRight(String[] array) {

        String user = array[0];
        String date = array[1];
        String time = array[2];
        String field = array[3];

        if (!TimeUtils.isDateRight(date))
            return false;

        if (!TimeUtils.isRightTime(time))
            return false;

        return true;

    }

}
