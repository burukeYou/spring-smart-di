package com.burukeyou.smartdi.register;

import com.burukeyou.smartdi.config.SpringBeanContext;
import com.burukeyou.smartdi.annotations.BeanAliasName;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProxySPIAliasNameRegister implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (StringUtils.isEmpty(beanClassName)){
                continue;
            }
            try {
                Class<?> beanClass = Class.forName(beanClassName);
                BeanAliasName proxySPIName = beanClass.getAnnotation(BeanAliasName.class);
                if (proxySPIName != null){
                    SpringBeanContext.registerBeanAlias(beanDefinitionName,proxySPIName.value());
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can not find class for beanDefinitionName " + beanDefinitionName,e);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {


    }
}
