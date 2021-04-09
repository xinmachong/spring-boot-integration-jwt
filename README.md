# spring-boot-integration-jwt

Spring Boot 集成 JWT 的 Demo，项目框架已搭好，下载即用。

Demo的代码虽少，但是项目架构是较为完整的，故而做简单记录，主要分为以下几步：

1、项目结构

2、代码细节

在 JWTUtils 中，从配置文件 .yml 中获取 JWT 的有效期和私钥的时候，没有选择直接用注解@Value注入

```java
@Value("${mydefine.token.expire}")
private int expire;
@Value("${mydefine.token.signature}")
private String signature;
```

而是使用更为费劲的间接注入

```java
@Resource
private TokenPropertiesBO initTokenPropertiesBO;
private static TokenPropertiesBO tokenPropertiesBO;

//@PostConstruct 意思就是在完成构造函数实例化后就调用该方法，该方法会对 TokenPropertiesBO 对象实例化。
@PostConstruct
public void init(){
	tokenPropertiesBO = initTokenPropertiesBO;
}
```

其主要原因是：JWTUtils 中生成 token 以及验证 token 的方法是用的 static 修饰，而 @Value 直接注入的值没法给static修饰的方法调用，所以只能通过注解 @PostConstruct 来提前实例化注入的值。

那为啥要将 JWTUtils 中的方法都用static修饰呢？因为这里牵涉到了一个 Interceptor 拦截器，在 common -> jwt -> JWTInterceptor 中，需要调用 JWTUtils.verify(token)，即 token 的验证，如果此处的 verify() 不是static修饰的方法的话，那 JWTUtils 这个对象将无法注入；直白点说就是 SpringBoot 拦截器中 Bean 无法注入。



