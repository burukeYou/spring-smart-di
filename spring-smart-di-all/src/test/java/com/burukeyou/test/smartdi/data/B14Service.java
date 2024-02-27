package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.proxyspi.annotation.AutowiredProxySPI;
import com.burukeyou.smartdi.proxyspi.annotation.AutowiredSPI;
import com.burukeyou.smartdi.smart.SmartAutowired;
import com.burukeyou.test.smartdi.smartautowired.IService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class B14Service {


    @AutowiredSPI
    private SmsService smsService1;

    @AutowiredProxySPI
    private SmsService smsService2;

    @Value("${system.sms}")
    private String value1;


    @SmartAutowired(propValue = "${smart.live}")
    private IService e;


}
