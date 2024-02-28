# spring-smart-di
Spring 动态依赖注入， 不再局限于简单的Autowired


## @SmartAutowired
与Autowired区别是
1）当依赖的具体的类为非接口时，而是具体类并且该具体类还多个子类，并且具体类本身是Bean，会将具体类注入
2）可以根据指定环境变量属性的值进行注入。并且该属性值可以为 @BeanAliasName 配置的别名



## AutowiredSPI
当需要注入某个接口有多个实现类，可以根据该接口类标记的@ProxySPI注解、@EnvironmentProxySPI去标记动态注入的逻辑

## AutowiredProxySPI
与@AutowiredSPI不同的事，注入的是一个代理类。 可以实时根据配置去获取具体的实现类而无需重启服务器。

比如你的系统接入了多个短信服务商，然后需要切换不同的服务商，然后你只需要修改一下某个地方的配置不管是配置文件还是数据库，他都会立刻生效
而无需修改代码去注入不同的对象
