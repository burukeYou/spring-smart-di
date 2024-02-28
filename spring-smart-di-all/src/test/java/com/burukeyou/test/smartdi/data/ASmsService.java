package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.stereotype.Component;

@BeanAliasName("aSMS_service")
@Component
public class ASmsService implements SmsService,DBSmsService,EnvironmentSmsService {

    @Override
    public void send(String message) {
        System.out.println("A 发送消息 " + message);
    }
}
