package com.burukeyou.test.smartdi.data;

import com.burukeyou.test.smartdi.extend.DBProxySPI;


@DBProxySPI("${db.sms}")
public interface DBSmsService {

    void send(String message);


}
