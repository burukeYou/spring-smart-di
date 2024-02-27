package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.stereotype.Component;

@BeanAliasName("a10")
@Component
public class ASmsService implements SmsService {

    @Override
    public void send(String message) {
        System.out.println("A 发送消息 " + message);
    }
}
