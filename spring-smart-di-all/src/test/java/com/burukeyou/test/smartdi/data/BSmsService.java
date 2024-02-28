package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.stereotype.Component;

@BeanAliasName("bSMS_service")
@Component
public class BSmsService implements SmsService,DBSmsService,EnvironmentSmsService  {


    @Override
    public void send(String message) {
        System.out.println("B 发送消息 " + message);
    }

}
