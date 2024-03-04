package com.burukeyou.smartdi.smart;

import com.burukeyou.smartdi.exceptions.AutowiredInjectBeanException;
import com.burukeyou.smartdi.register.BaseAutowiredBeanProcessor;
import com.burukeyou.smartdi.support.AutowiredInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SmartAutowiredBeanProcessor extends BaseAutowiredBeanProcessor {

    @Override
    public List<Class<? extends Annotation>> interceptAnnotation() {
        return Collections.singletonList(SmartAutowired.class);
    }

    @Override
    public Object getInjectedBean(AutowiredInvocation invocation) {
        SmartAutowired smartAutowired = invocation.getAnnotationMeta().getAnnotationObj();
        Object bean = null;
        if (!StringUtils.isEmpty(smartAutowired.key())){
            bean =  getFromProp(smartAutowired.value());
        }else{
            bean =   autowireGetBean(invocation.getInjectedType(),smartAutowired);
        }

        if (bean == null && smartAutowired.required()){
            throw new AutowiredInjectBeanException("can not find bean for " + invocation.getInjectedType());
        }
        return bean;
    }

    private Object getFromProp(String propName) {
        String beanName = environment.resolvePlaceholders(propName);
        if (StringUtils.isEmpty(beanName)){
            return null;
        }else if (beanName.contains(".")){
            try {
                Class<?> beanClass = Class.forName(beanName);
                return getBean(beanClass);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return getBeanByAllName(beanName);
    }

    private Object autowireGetBean(Class<?> injectedType, SmartAutowired smartAutowired) {
        Object beanValue;
        if (injectedType.isInterface() || Modifier.isAbstract(injectedType.getModifiers())){
            beanValue = getBean(injectedType);
        }else{
            beanValue = getSameBean(injectedType);
        }
        return beanValue;
    }

    private Object getSameBean(Class<?> fieldType) {
        Map<String, ?> beansOfType = springContext.getBeansOfType(fieldType);
        if (beansOfType.size() <= 0){
           return null;
        }

        // 尝试使用具体类
        for (Object value : beansOfType.values()) {
            if (isSameClass(value.getClass(), fieldType)) {
                return value;
            }
        }

        String msg = beansOfType.values().stream().map(e -> e.getClass().getSimpleName()).collect(Collectors.joining(","));
        throw new AutowiredInjectBeanException(fieldType.getName() + "有多个实现类 " + msg);
    }

    public boolean isSameClass(Class<?> class1, Class<?> class2) {
        if (class1 == class2) {
            return true;
        }
        if (class2.isAssignableFrom(class1) || class1.isAssignableFrom(class2)) {
            return false;
        }
        return false;
    }

}
