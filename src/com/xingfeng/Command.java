package com.xingfeng;

import java.util.Date;

/**
 * 合法命令
 * Created by Xingfeng on 2017-09-11.
 */
public class Command implements Comparable<Command> {

    private Date date;//日期
    private int startTime;//开始时间
    private int endTime;//结束时间

    private boolean isCancel;//取消命令
    private float cost;//命令产生的费用，+表示收入，-表示违约金


    public Command(Date date, int startTime, int endTime, boolean isCancel, float cost) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCancel = isCancel;
        this.cost = cost;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public Date getDate() {
        return date;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;

        Command command = (Command) o;

        if (startTime != command.startTime) return false;
        if (endTime != command.endTime) return false;
        return date != null ? date.equals(command.date) : command.date == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + startTime;
        result = 31 * result + endTime;
        return result;
    }

    @Override
    public int compareTo(Command o) {

        int result = date.compareTo(o.date);
        if (result == 0) {

            result = startTime - o.startTime;
            if (result == 0) {

                result = endTime - o.endTime;
                //日期和时间均相同，那么预定的在后
                if (result == 0) {
                    result = isCancel ? 0 : 1;
                }

            }

        }

        return result;
    }


}
