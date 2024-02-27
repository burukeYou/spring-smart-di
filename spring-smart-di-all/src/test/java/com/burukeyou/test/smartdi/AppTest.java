package com.burukeyou.test.smartdi;

import com.burukeyou.test.smartdi.data.B14Service;
import org.junit.Before;
import org.junit.Test;

public class AppTest extends BaseSpringTest {

    @Before
    public void before(){
        //initContext(SmartDIConfiguration.class);
        initContext();
    }

    @Test
    public void test() {
        //SmsService load = ProxySpiLoader.load(SmsService.class);

        B14Service bean = context.getBean(B14Service.class);
        System.out.println();
    }
}
