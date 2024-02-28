package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;


@ProxySPI(SmsServiceProxyFactory.class)
public interface SmsService {

    void send(String message);


}
