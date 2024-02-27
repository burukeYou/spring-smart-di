package com.burukeyou.smartdi.smart;

import com.burukeyou.smartdi.proxyspi.register.BaseSpringAware;
import com.burukeyou.smartdi.register.AutowiredBeanFactory;
import com.burukeyou.smartdi.support.AutowiredInvocation;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.Map;

//@Component
public class SmartAutowiredFactory extends BaseSpringAware implements AutowiredBeanFactory<SmartAutowired> {
    
    @Override
    public void registerBeanDefinition(AutowiredInvocation invocation) {
        
    }

    @Override
    public Object getCustomBean(AutowiredInvocation invocation,SmartAutowired smartAutowired) throws Exception {
        Object bean = null;
        if (!StringUtils.isEmpty(smartAutowired.propValue())){
            bean = getFromProp(smartAutowired.propValue());
        }else if (smartAutowired.defaultType() != Void.class && smartAutowired.defaultType() !=null) {
            bean = getBean(smartAutowired.defaultType());
        }else {
            bean = autowireGetBean(invocation.getInjectedType(),smartAutowired);
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
        return getBeanByAliasName(beanName);
    }

    private Object autowireGetBean(Class<?> injectedType, SmartAutowired smartAutowired) {
        Object beanValue;
        if (injectedType.isInterface() || Modifier.isAbstract(injectedType.getModifiers())){
            // 如果是接口按 传统autowired注入. 但是可能会有多个实现类. 一般只有一个实现类就不用指定 type了
            try {
                beanValue = getBean(injectedType);
            } catch (NoUniqueBeanDefinitionException e) {
                Class<?> type = smartAutowired.defaultType();
                if (type != Void.class && type != null){
                    beanValue = getSameBean(type);
                }else{
                    throw e;
                }
            }
        }else{
            // 如果是具体类则具体注入
            beanValue = getSameBean(injectedType);
        }
        return beanValue;
    }

    private Object getSameBean(Class<?> fieldType) {
        Map<String, ?> beansOfType = springContext.getBeansOfType(fieldType);
        if (beansOfType.size() <= 0){
            throw new RuntimeException("没有找到实现类: " + fieldType.getName());
        }

        for (Object value : beansOfType.values()) {
            if (isSameClass(value.getClass(), fieldType)) {
                return value;
            }
        }
        throw new RuntimeException("有多个实现类请指定: " + fieldType.getName());
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
