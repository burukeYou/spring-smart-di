package com.burukeyou.test.smartdi;

import com.burukeyou.smartdi.proxyspi.ProxySpiLoader;
import com.burukeyou.test.smartdi.data.SmsService;
import org.junit.Before;
import org.junit.Test;

public class TestAPI extends BaseSpringTest {

    @Before
    public void before(){
        initContext();
    }

    @Test
    public void testProxySPI() {
        SmsService load = ProxySpiLoader.load(SmsService.class);
        System.out.println();
    }


    @Test
    public void testAutowiredAnnotation() {
        AllService bean = context.getBean(AllService.class);
        System.out.println();
    }

    @Test
    public void testProxyAutowiredAnnotation() {
        AllService bean = context.getBean(AllService.class);
        for (int i = 0; i < 10; i++) {
            bean.proxySmsService.send("333");
        }
        System.out.println();
    }
}
