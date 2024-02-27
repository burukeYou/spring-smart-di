package com.burukeyou.smartdi.register;

import com.burukeyou.smartdi.proxyspi.register.BaseSpringAware;
import com.burukeyou.smartdi.support.AnnotationMeta;
import com.burukeyou.smartdi.support.AutowiredInvocation;
import com.burukeyou.smartdi.utils.AnnotationUtils;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author caizhihao
 */
@Getter
public abstract class BaseAutowiredBeanPostProcessor extends BaseSpringAware implements  Ordered,
        MergedBeanDefinitionPostProcessor,
        InstantiationAwareBeanPostProcessor, BeanFactoryPostProcessor {

    private final List<Class<? extends Annotation>> annotationTypes = new ArrayList<>();

    private final ConcurrentMap<String,AnnotatedInjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>(32);



    protected BaseAutowiredBeanPostProcessor() {
        this.annotationTypes.addAll(processAnnotation());
    }

    protected abstract List<Class<? extends Annotation>> processAnnotation();

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (isNotProcess(beanType)){
            return;
        }
        AnnotatedInjectionMetadata injectionMetadata = findInjectionMetadata(beanName, beanType, null);
        injectionMetadata.checkConfigMembers(beanDefinition);

//        prepareInjection(injectionMetadata);
    }

    @Override
    public void resetBeanDefinition(String beanName) {
        this.injectionMetadataCache.remove(beanName);
    }

    protected void beforeInjection(AnnotatedInjectionMetadata metadata) {

    }


    // 判断某个并是否需要执行注入
    private boolean isNotProcess(Class<?> beanType){
        List<Field> fieldList = new ArrayList<>();
        ReflectionUtils.doWithFields(beanType,field -> {
            for (Class<? extends Annotation> annotationType : annotationTypes) {
                if (field.isAnnotationPresent(annotationType)){
                    fieldList.add(field);
                }else {
                    Annotation annotation = AnnotatedElementUtils.findMergedAnnotation(field, annotationType);
                    if (annotation != null){
                        fieldList.add(field);
                    }
                }
            }
        });
        return fieldList.isEmpty();
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        // todo
        if (isNotProcess(bean.getClass())){
            return pvs;
        }

        AnnotatedInjectionMetadata metadata = findInjectionMetadata(beanName, bean.getClass(), pvs);
        try {
//            prepareInjection(metadata);
            metadata.inject(bean, beanName, pvs);
        }
        catch (BeanCreationException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
        return pvs;
    }

    protected AnnotatedInjectionMetadata findInjectionMetadata(String beanName, Class<?> clazz, PropertyValues pvs){
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        AnnotatedInjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (needsRefreshInjectionMetadata(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (needsRefreshInjectionMetadata(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    try {
                        metadata = buildAnnotatedMetadata(clazz);
                        if (metadata != null){
                            this.injectionMetadataCache.put(cacheKey, metadata);
                        }
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect object class [" + clazz.getName() +
                                "] for annotation metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return metadata;
    }

    private AnnotatedInjectionMetadata buildAnnotatedMetadata(Class<?> beanClass) {
        // find field
        Collection<AnnotatedFieldElement> elements = findFieldAnnotationMetadata(beanClass);
        // find method

        return  new AnnotatedInjectionMetadata(beanClass,elements);
    }


    protected List<AnnotatedFieldElement> findFieldAnnotationMetadata(final Class<?> beanClass){
        List<AnnotatedFieldElement> elements = new ArrayList<>();
        ReflectionUtils.doWithFields(beanClass,(field -> {
            for (Class<? extends Annotation> annotationType : annotationTypes) {
                AnnotationMeta annotationMeta = AnnotationUtils.getAnnotationMetaBy(field, annotationType, getEnvironment(), false, true);
                if (annotationMeta != null) {
//                    if (Modifier.isStatic(field.getModifiers())){
//
//                    }

                    //Annotation annotation = AnnotationUtils.tryGetMergedAnnotation(field, annotationType);
                    elements.add(new AnnotatedFieldElement(field, annotationMeta));
                }
            }
        }));
        return elements;
    }

    private boolean needsRefreshInjectionMetadata(AnnotatedInjectionMetadata metadata, Class<?> clazz) {
        return (metadata == null || metadata.needsRefresh(clazz));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 2;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Class<?> beanType = beanFactory.getType(beanName);
            if (beanType == null){
                continue;
            }

            // todo
            if (isNotProcess(beanType)){
                continue;
            }

            AnnotatedInjectionMetadata injectionMetadata = findInjectionMetadata(beanName, beanType, null);
            beforeInjection(injectionMetadata);
        }
    }


    protected static class AnnotatedInjectionMetadata extends InjectionMetadata {

        private Class<?> targetClass;

        private final Collection<AnnotatedFieldElement> fieldElements;

        public AnnotatedInjectionMetadata(Class<?> targetClass, Collection<AnnotatedFieldElement> fieldElements) {
            super(targetClass, combine(fieldElements));
            this.targetClass = targetClass;
            this.fieldElements = fieldElements;
        }

        protected boolean needsRefresh(Class<?> clazz) {
            if (this.targetClass == clazz) {
                return false;
            }
            //IGNORE Spring CGLIB enhanced class
            if (targetClass.isAssignableFrom(clazz) &&  clazz.getName().contains("$$EnhancerBySpringCGLIB$$")) {
                return false;
            }
            return true;
        }

        public Collection<AnnotatedFieldElement> getFieldElements() {
            return fieldElements;
        }
    }

    private static <T> Collection<T> combine(Collection<? extends T>... elements) {
        List<T> allElements = new ArrayList<T>();
        for (Collection<? extends T> e : elements) {
            allElements.addAll(e);
        }
        return allElements;
    }


    protected class AnnotatedInjectElement extends InjectionMetadata.InjectedElement {

        protected final AnnotationMeta annotationMeta;

        private Class<?> injectedType;

        protected AnnotatedInjectElement(Member member, PropertyDescriptor pd,AnnotationMeta annotationMeta) {
            super(member, pd);
            this.annotationMeta = annotationMeta;
            try {
                this.injectedType = getInjectedTypeFormMember();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {

            AutowiredInvocation invocation = new AutowiredInvocation();
            invocation.setTargetBean(bean);
            invocation.setTargetBeanName(beanName);
            invocation.setAnnotationMeta(annotationMeta);
            invocation.setInjectedType(injectedType);
            invocation.setInjectedMember(member);

            Object injectedObject = doGetInjectedBean(invocation);
            if (member instanceof Field) {
                Field field = (Field) member;
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field,bean,injectedObject);
            } else if (member instanceof Method) {
                Method method = (Method) member;
                ReflectionUtils.makeAccessible(method);
                method.invoke(bean, injectedObject);
            }
        }

        public Class<?> getInjectedTypeFormMember() throws ClassNotFoundException {
            if (injectedType == null) {
                if (this.isField) {
                    injectedType = ((Field) this.member).getType();
                }
                else if (this.pd != null) {
                    return this.pd.getPropertyType();
                }
                else {
                    Method method = (Method) this.member;
                    if (method.getParameterTypes().length > 0) {
                        injectedType = method.getParameterTypes()[0];
                    } else {
                        throw new IllegalStateException("get injected type failed");
                    }
                }
            }
            return injectedType;
        }

        public AnnotationMeta getAnnotationMeta() {
            return annotationMeta;
        }
    }

    protected abstract  Object doGetInjectedBean(AutowiredInvocation invocation) throws Exception;

    public class AnnotatedFieldElement extends AnnotatedInjectElement {

        protected final Field field;

        public AnnotatedFieldElement(Field field, AnnotationMeta annotationMeta) {
            super(field,null,annotationMeta);
            this.field = field;
        }

        public Field getField() {
            return field;
        }
    }

}
