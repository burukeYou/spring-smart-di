package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;
import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SmsServiceProxyFactory implements AnnotationProxyFactory<ProxySPI>, ApplicationContextAware {

    @Value("${system.sms}")
    private String smsClass;

    @Autowired
    private ApplicationContext context;

    @Override
    public Object getProxy(Class<?> targetClass,ProxySPI spi) {
        if (Objects.equals(smsClass, "ASmsService")){
            smsClass = "BSmsService";
        }else {
            smsClass = "ASmsService";
        }
        return context.getBean(smsClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
