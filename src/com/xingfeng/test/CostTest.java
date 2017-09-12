package com.xingfeng.test;

import com.xingfeng.BadmintonField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试计算价格
 * Created by Xingfeng on 2017-09-11.
 */

public class CostTest {

    BadmintonField badmintonField = null;

    @Before
    public void init() {
        badmintonField = new BadmintonField();
    }


    @Test
    public void test() {

        //工作日
        Assert.assertEquals(60, badmintonField.getCostAtWorkday(9, 11));
        Assert.assertEquals(240, badmintonField.getCostAtWorkday(9, 15));
        Assert.assertEquals(470, badmintonField.getCostAtWorkday(9, 19));
        Assert.assertEquals(670, badmintonField.getCostAtWorkday(9, 22));

        Assert.assertEquals(200, badmintonField.getCostAtWorkday(13, 17));
        Assert.assertEquals(410, badmintonField.getCostAtWorkday(13, 20));
        Assert.assertEquals(470, badmintonField.getCostAtWorkday(13, 21));

        Assert.assertEquals(80, badmintonField.getCostAtWorkday(19, 20));
        Assert.assertEquals(140, badmintonField.getCostAtWorkday(19, 21));

        Assert.assertEquals(60, badmintonField.getCostAtWorkday(20, 21));

        //周末
        Assert.assertEquals(80, badmintonField.getCostAtWeekend(9, 11));
        Assert.assertEquals(270, badmintonField.getCostAtWeekend(9, 15));
        Assert.assertEquals(660, badmintonField.getCostAtWeekend(9, 22));

        Assert.assertEquals(200, badmintonField.getCostAtWeekend(13, 17));
        Assert.assertEquals(370, badmintonField.getCostAtWeekend(13, 20));

        Assert.assertEquals(60, badmintonField.getCostAtWeekend(19, 20));

    }

}
