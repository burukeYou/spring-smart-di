package com.burukeyou.smartdi.proxyspi;

import com.burukeyou.smartdi.config.SpringBeanContext;
import com.burukeyou.smartdi.exceptions.SPIBeanTypeMismatchException;
import com.burukeyou.smartdi.proxyspi.annotation.ProxySPI;
import com.burukeyou.smartdi.proxyspi.factory.AnnotationProxyFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;

/**
 * @author caizhihao
 */
public class ProxySpiLoader {

    private ProxySpiLoader(){}

    public static <T> T load(Class<T> clz){
        ProxySPI proxySPI = AnnotatedElementUtils.getMergedAnnotation(clz, ProxySPI.class);
        Annotation obj = getSPIObject(clz);
        if (obj == null){
            throw new RuntimeException(clz.getSimpleName() + "请标记ProxySPI的注解或者复合注解");
        }

        Class<? extends AnnotationProxyFactory<?>> proxyFactory = proxySPI.value();
        AnnotationProxyFactory<Annotation> springBean = (AnnotationProxyFactory<Annotation>) SpringBeanContext.getBean(proxyFactory);
        Object proxy = springBean.getProxy(clz, obj);
        if (proxy == null){
            return null;
        }

        if (!clz.isAssignableFrom(proxy.getClass())){
            throw new SPIBeanTypeMismatchException(springBean.getClass().getSimpleName() + " get the bean type  is "+proxy.getClass().getSimpleName()+", need inject class type " + clz.getSimpleName());
        }

        return (T)proxy;
    }


    public  static Annotation getSPIObject(Class<?> clz){
        Annotation[] annotations = clz.getAnnotations();
        for (Annotation element : annotations) {
            Class<? extends Annotation> aClass = element.annotationType();
            if(aClass == ProxySPI.class || aClass.isAnnotationPresent(ProxySPI.class)){
                return element;
            }
        }

       return null;
    }
}
