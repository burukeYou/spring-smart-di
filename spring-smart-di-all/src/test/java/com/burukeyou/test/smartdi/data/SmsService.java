package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.proxyspi.annotation.EnvironmentProxySPI;

//@ProxySPI(SmsServiceProxyFactory.class)
@EnvironmentProxySPI("${system.sms}")
public interface SmsService {

    void send(String message);


}
