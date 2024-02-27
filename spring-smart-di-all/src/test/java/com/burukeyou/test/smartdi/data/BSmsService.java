package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.stereotype.Component;

@BeanAliasName("b10")
@Component
public class BSmsService implements SmsService {


    @Override
    public void send(String message) {
        System.out.println("B 发送消息 " + message);
    }

}
