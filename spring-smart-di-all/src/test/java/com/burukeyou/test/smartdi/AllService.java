package com.burukeyou.test.smartdi;

import com.burukeyou.smartdi.proxyspi.spi.AutowiredProxySPI;
import com.burukeyou.smartdi.proxyspi.spi.AutowiredSPI;
import com.burukeyou.smartdi.smart.SmartAutowired;
import com.burukeyou.test.smartdi.data.DBSmsService;
import com.burukeyou.test.smartdi.data.EnvironmentSmsService;
import com.burukeyou.test.smartdi.data.SmsService;
import com.burukeyou.test.smartdi.smartautowired.AService;
import com.burukeyou.test.smartdi.smartautowired.IService;
import org.springframework.stereotype.Component;

@Component
public class AllService {

    @AutowiredSPI
    public DBSmsService dbSmsService;

    @AutowiredProxySPI
    public SmsService proxySmsService;

    @AutowiredSPI
    public EnvironmentSmsService environmentSmsService;

    @SmartAutowired(value = "${smart.live}")
    public IService propSmartService;

    @SmartAutowired
    public AService aService;

}
