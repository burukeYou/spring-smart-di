# spring-smart-di
Spring 动态依赖注入扩展， 不再局限于简单的@Autowired

扩展注入注解如下
- @SmartAutowired
- @AutowiredProxySPI
- @AutowiredSPI


# 2、快速开始
引入依赖
```xml
    <dependency>
       <groupId>com.burukeyou</groupId>
        <artifactId>spring-smart-di-all</artifactId>
        <version>0.1.0</version>
    </dependency>
```

在spring配置类上标记 @EnableSmartDI 注解


## 2.1 @SmartAutowired注解使用


### 2.1.1 默认的注入逻辑
与Autowired区别是
不就相当于 @Primay
1）当依赖的具体的类为非接口时，而是具体类并且该具体类还多个子类，并且具体类本身是Bean，会将具体类注入。 如果@Autowired 会抛出异常因为有多个实现类
2）可以根据指定环境变量属性的值进行注入。并且该属性值可以为 @BeanAliasName 配置的别名


比如有以下三个SpringBean
```java
@Component
public class Weather {
}

@Component
@BeanAliasName("天气A服务商")
public class WeatherA extends Weather {
}

@Component
@BeanAliasName("天气B服务商")
public class WeatherB extends Weather {
}
```

然后使用@SmartAutowired注解进行依赖注入依然能够注入成功，注入的是Weather本身。 
当然如果Weather是非SpringBean，与@Autowired一样会抛出有多个实现类的异常无法自动注入。
```java
  @SmartAutowired
  private Weather weather;
```

为什么要加入这个注入逻辑呢，因为我们的做抽象设计的时候，往往会写出非常复杂的类图关系的，一个类往往有多个实现类。
但我想注入那个类又不想搭配@Qualifier注解去硬编码指定注入的beanName（硬编码这对代码洁癖的来说非常重要）,也不想每次使用@Primary
去指定默认注入哪个，因为是确定的，所以"智能化"了一点去帮助我们的依赖注入


### 2.1.2 使用环境变量去配置注入的Bean
假如有以下配置
```yml
weather:
  impl: 天气A服务商
```

然后指定环境变量的key，则会使用该属性的具体值去执行依赖注入。 这个属性值可以是beanName，也可以是全路径类名,也可以是使用@BeanAliasName注解标记的名字
```java
  @SmartAutowired("${weather.impl}")
  private Weather weather;
```



## 2.2 AutowiredProxySPI注解
当需要注入某个接口有多个实现类，可以根据该接口类标记的`@ProxySPI`注解、`@EnvironmentProxySPI`去标记动态注入的逻辑

让我们以一个场景去看如何使用， 假如系统接入了多个短信服务商，然后用户可以在页面动态的切换不同的服务商，

如果让我们手写会如何实现。

- 第一步先在某个位置（不管是nacos还是数据库）配置当前使用的服务商的对应值比如 `sms.impl = "某腾短信"`

- 第二步，在代码里执行发短信的时候，手动获取该`sms.impl`对应的服务商的实现类，伪代码可能如下
```java
 // 1、获取当前使用的服务商
 String name = get("sms.impl");
// 2、获取对应的实现类
 SmsService smsService = springContext.getBean(name);
 // 3、使用smsService执行具体业务逻辑
  smsService.sendMsg()
```

但是现在让我们来 `@AutowiredProxySPI` 是如何自动屏蔽这些细节
需要搭配`@ProxySPI`来处理，假设我们的当前使用的服务商在环境变量中`${sms.impl}`, 便可以使用默认实现 `@EnvironmentProxySPI`

比如存在配置
```yaml
sms:
  impl: 某移短信服务
```

然后在接口类上标记`@EnvironmentProxySPI`即可， 会根据该属性值去获取具体注入哪个实现类。然后就可以使用我们的
@AutowiredProxySPI注解进行依赖注入了,并且注入的是一个代理对象，代理的逻辑是每次执行SmsService的任何方法时，都会去重新获取一次
当前使用的实现类，所以即使你修改了`${sms.impl}`配置，也能实时生效而无需重启服务器。

```java
@EnvironmentProxySPI("${sms.impl}")
public interface SmsService {
}

@BeanAliasName("某腾短信服务")
@Component
public class ASmsService implements SmsService {
}

@BeanAliasName("某移短信服务")
@Component
public class BSmsService implements SmsService {
}

// 依赖注入
@AutowiredProxySPI
private SmsService smsService;

```


`@EnvironmentProxySPI`是用来配置环境变量相关注入逻辑，如果想要自定义配置比如在数据库中可实现自己的ProxySPI注解。

比如`自定义DBProxySPI注解`，并标记上@ProxySPI实现并指定具体AnnotationProxyFactory即可。 
然后DBProxySPI就可以像@EnvironmentProxySPI一样去使用了,下面是实现的伪代码

```java
@Inherited
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ProxySPI(DbProxyFactory.class)
public @interface DBProxySPI {
    
    String value();

}

@Component
public class DbProxyFactory implements AnnotationProxyFactory<DBProxySPI> {

    @Autowired
    private SysConfigMapper sysConfigDao;
    
    @Override
    public Object getProxy(Class<?> targetClass,DBProxySPI spi) {
        // todo 根据注解从数据库获取要注入的实现类
        String configName = sysConfigDao.getConfig(spi.value());
        return springContext.getBean(configName);
    }
}




```

## 2.3 AutowiredSPI注解
与@AutowiredProxySPI注解在使用上基本一致，区别是@AutowiredProxySPI注入的是代理对象， AutowiredSPI注入的当前使用的
具体的实现类，只在服务器启动的时候注入，也就是说更改配置后需要重启服务切换实现类才会生效。







