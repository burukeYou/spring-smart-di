package com.burukeyou.test.smartdi;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.IOException;

public class BaseSpringTest {

    public static AnnotationConfigApplicationContext context;


    public static void  initContext(Class<?>...registerClass){
        try {
            context = createContext("com.burukeyou.test.smartdi",registerClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnnotationConfigApplicationContext createContext(String packageName,Class<?>...registerClass) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();

        ClassPathResource pathResource = new ClassPathResource("application.properties");
        ResourcePropertySource propertySource = new ResourcePropertySource(new EncodedResource(pathResource, "UTF-8"));

        environment.getPropertySources().addLast(propertySource);

        context.setEnvironment(environment);
        context.scan(packageName);

        for (Class<?> aClass : registerClass) {
            context.register(aClass);
        }

        context.refresh();
        return context;
    }

}
