package com.burukeyou.smartdi.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class SpringBeanContext implements ApplicationContextAware {


    protected static ApplicationContext springContext;

    private static final Map<String,String> BEAN_SPI_NAME = new ConcurrentHashMap<>();

    public static void registerBeanAlias(String aliasName,String beanName){
        if (BEAN_SPI_NAME.containsKey(aliasName)){
            throw new RuntimeException("bean name ["+aliasName+"] already exist");
        }
        BEAN_SPI_NAME.put(aliasName,beanName);
    }

    public static <T> T getBean(String name){
        try {
            return (T) springContext.getBean(name);
        } catch (BeansException e) {
            return null;
        }
    }

    public static <T> T getBeanByAliasName(String name){
        try {
            return (T) springContext.getBean(name);
        } catch (BeansException e) {
            String originBeanName = BEAN_SPI_NAME.get(name);
            if (StringUtils.isEmpty(originBeanName)){
                throw new RuntimeException("can not find bean for alias name ["+name+"] , because it not exist");
            }

            return getBean(originBeanName);
        }
    }

    public static <T> T getBean(Class<T> clz) {
        try {
            return springContext.getBean(clz);
        } catch (NoUniqueBeanDefinitionException e) {
          return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (springContext == null){
            springContext = applicationContext;
        }
    }
}
