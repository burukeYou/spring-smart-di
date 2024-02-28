package com.burukeyou.test.smartdi.data;

import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;
import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SmsServiceProxyFactory implements AnnotationProxyFactory<ProxySPI>, ApplicationContextAware {


    @Autowired
    private ApplicationContext context;

    private String[] arr = {"ASmsService","BSmsService"};
    private static int index = 0;

    @Override
    public Object getProxy(Class<?> targetClass,ProxySPI spi) {
        if (index == 0){
            index = 1;
        }else if (index == 1){
            index = 0;
        }
        return context.getBean(arr[index]);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
