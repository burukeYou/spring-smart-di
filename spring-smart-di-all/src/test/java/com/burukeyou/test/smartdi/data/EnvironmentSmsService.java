package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.proxyspi.spi.EnvironmentProxySPI;

@EnvironmentProxySPI("${system.sms}")
public interface EnvironmentSmsService {

    void send(String message);

}
