package com.xingfeng;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 羽毛球场地
 * Created by Xingfeng on 2017-09-11.
 */
public class BadmintonField {

    //以日期为key，场馆的时间间隔列表为value
    private Map<Date, List<Period>> periodMaps = new HashMap<>();
    //维护着合法的命令列表
    private List<Command> commands = new ArrayList<>();

    /**
     * 预定场地
     *
     * @param date
     * @param start
     * @param end
     * @param order
     * @return 0表示成功，1表示冲突
     */
    public int orderField(Date date, int start, int end, Order order) {

        List<Period> periods = periodMaps.get(date);
        if (periods == null) {
            periods = initPeriods();
            periodMaps.put(date, periods);
        }

        //判断是否预定过，该区间只要有用户预定了，就冲突
        boolean hasOrder = false;
        for (int i = start; i < end; i++) {
            if (periods.get(i - 9).getOrder() != null) {
                hasOrder = true;
                break;
            }
        }

        if (hasOrder)
            return 1;

        //将该区间设为已预定
        for (int i = start; i < end; i++) {
            periods.get(i - 9).setOrder(order);
        }
        //设置起始时段的开始时长
        periods.get(start - 9).setPeriod(end - start);

        //添加命令
        Command command = new Command(date, start, end, false, getCost(date, start, end));
        commands.add(command);

        return 0;
    }

    /**
     * 初始化时刻表
     *
     * @return
     */
    private List<Period> initPeriods() {

        List<Period> periods = new ArrayList<>();

        for (int i = 9; i < 22; i++) {

            periods.add(new Period(i, null, 0));

        }

        return periods;

    }

    /**
     * 取消场地
     *
     * @param date
     * @param start
     * @param end
     * @param order
     * @return 0表示成功，1表示失败
     */
    public int cancelField(Date date, int start, int end, Order order) {

        List<Period> periods = periodMaps.get(date);
        if (periods == null)
            return 1;

        //判断是否可以取消
        boolean canCanel = true;
        for (int i = start; i < end; i++) {

            if (!order.equals(periods.get(i - 9).getOrder())) {
                canCanel = false;
                break;
            }

        }

        if (!canCanel)
            return 1;

        //判断起始时长是否一致
        canCanel = periods.get(start - 9).getPeriod() == end - start;
        if (!canCanel)
            return 1;

        //清除状态
        for (int i = start; i < end; i++) {
            periods.get(i - 9).setOrder(null);
            periods.get(i - 9).setPeriod(0);
        }

        //可以取消，获取违约金
        float cost = getExtraCost(date, start, end);
        Command command = new Command(date, start, end, true, cost);
        addCancelCommand(command);

        return 0;
    }

    private float getExtraCost(Date date, int start, int end) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
            return getCostAtWeekend(start, end) * 0.25f;
        } else {
            return getCostAtWorkday(start, end) * 0.5f;
        }

    }


    /**
     * 添加预定命令
     *
     * @param command
     */
    private void addOrderCommand(Command command) {
        commands.add(command);
    }

    /**
     * 添加删除命令，需要将之前的预定命令删除
     *
     * @param command
     */
    private void addCancelCommand(Command command) {

        Iterator iterator = commands.iterator();
        while (iterator.hasNext()) {

            Command cmd = (Command) iterator.next();
            //时间区间相同且是约定命令，则删除该命令
            if (cmd.equals(command) && !cmd.isCancel()) {
                iterator.remove();
                break;
            }

        }
        commands.add(command);

    }


    /**
     * 记录场地费用
     *
     * @param date
     * @param start
     * @param end
     * @return
     */
    private int getCost(Date date, int start, int end) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
            return getCostAtWeekend(start, end);
        } else {
            return getCostAtWorkday(start, end);
        }

    }

    /**
     * 计算工作日的费用
     *
     * @param start
     * @param end
     * @return
     */
    public int getCostAtWorkday(int start, int end) {

        int cost = 0;

        if (start >= 9 && start < 12) {

            if (end > 9 && end <= 12) {
                cost = 30 * (end - start);
            } else if (end > 12 && end <= 18) {
                cost = 30 * (12 - start) + 50 * (end - 12);
            } else if (end >= 18 && end < 20) {
                cost = 30 * (12 - start) + 50 * (18 - 12) + 80 * (end - 18);
            } else {
                cost = 30 * (12 - start) + 50 * (18 - 12) + 80 * (20 - 18) + 60 * (end - 20);
            }


        } else if (start >= 12 && start < 18) {

            if (end > 12 && end <= 18) {
                cost = 50 * (end - start);
            } else if (end >= 18 && end < 20) {
                cost = 50 * (18 - start) + 80 * (end - 18);
            } else {
                cost = 50 * (18 - start) + 80 * (20 - 18) + 60 * (end - 20);
            }

        } else if (start >= 18 && start < 20) {

            if (end > 18 && end <= 20) {
                cost = 80 * (end - start);
            } else {
                cost = 80 * (20 - start) + 60 * (end - 20);
            }

        } else {
            cost = 60 * (end - start);
        }


        return cost;
    }

    /**
     * 计算周末的费用
     *
     * @param start
     * @param end
     * @return
     */
    public int getCostAtWeekend(int start, int end) {
        int cost = 0;

        if (start >= 9 && start < 12) {

            if (end > 9 && end <= 12) {
                cost = 40 * (end - start);
            } else if (end > 12 && end <= 18) {
                cost = 40 * (12 - start) + 50 * (end - 12);
            } else {
                cost = 40 * (12 - start) + 50 * (18 - 12) + 60 * (end - 18);
            }


        } else if (start >= 12 && start < 18) {

            if (end > 12 && end <= 18) {
                cost = 50 * (end - start);
            } else {
                cost = 50 * (18 - start) + 60 * (end - 18);
            }

        } else {
            cost = 60 * (end - start);
        }


        return cost;
    }


    public float getCostAndPrintCmds() {

        //对Command进行排序
        Collections.sort(commands);

        float total = 0;

        for (Command command : commands) {

            Date date = command.getDate();
            int start = command.getStartTime();
            int end = command.getEndTime();
            float cost = command.getCost();
            boolean isCancel = command.isCancel();

            total += cost;

            printCmd(date, start, end, cost, isCancel);


        }

        System.out.println("小计:" + total);

        return total;

    }

    private void printCmd(Date date, int start, int end, float cost, boolean isCancel) {

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sb.append(sdf.format(date)).append(" " + start + ":00")
                .append(" ~ ").append(end + ":00 ");

        if (isCancel) {

            sb.append("违约金 ").append(cost + "元");

        } else {

            sb.append(cost + "元");

        }

        System.out.println(sb);

    }


}
