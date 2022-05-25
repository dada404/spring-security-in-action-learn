# 单点登录体现在哪

这是一个极其简易的单点登录，我们把Github当作我们自己的网站M，把`localhost:8080/`作为M信任的其他网站B

我们希望登录了Github后（并通过授权），无需再登录B即可访问B的资源（或者想访问B必须先在Github登录）

# 基本架构

**Github相关第三方网站的OAuth2的授权是基于OAuth2的认证码方式**

### OAuth2LoginAuthenticationFilter

![image-20220524101328586](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/image-20220524101328586.png)

### Client类相关

![image-20220524101347970](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/image-20220524101347970.png)



# 基本过程

![image-20220524104219775](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/image-20220524104219775.png)

## 注册github的OAuth2应用

https://github.com/settings/applications/new

![image-20220524100509233](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/image-20220524100509233.png)

## 创建一个Client Secret

![image-20220524100620820](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/image-20220524100620820.png)

## application.yml

```yml
client:
  id: d4ef7df524d951e35762
  secret: e0e47e677afb225cce8638308f883df2f4cd9f52
```

## 配置类

CommonOAuth2Provider是提供了一些常用的认证网站如Google，Github

ClientRegistration则是对一系列ClientRegistration进行管理

**ClientRegistrationRepository会通过clientId查找ClientRegistration进行授权**

```java
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    private ClientRegistration clientRegistration() {
        return CommonOAuth2Provider.GITHUB
            .getBuilder("github")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .build();
    }

    public ClientRegistrationRepository clientRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2Login(c -> {
            c.clientRegistrationRepository(clientRepository());
        });

        http.authorizeRequests()
            .anyRequest()
            .authenticated();
    }
}
```

## 构建controller

OAuth2AuthenticationToken可以通过mvc注入，也可以通过SecurityContextHolder获取

```java
@Controller
public class MainController {
    private Logger log = Logger.getLogger(MainController.class.getName());

    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token) {
        log.info(String.valueOf(token.getPrincipal()));
        return "main.html";
    }
}

```

## 构建main.html

```java
<h1>hello there!</h1>
```



## 访问测试

客户端指的即是我们的Java程序

<img src=".%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/image-20220524102604299.png" alt="image-20220524102604299" style="zoom: 67%;" />

http://localhost:8080/ 会跳出github.com的对应授权网站,我们要关注于其中的请求内容,并回顾一下我们之前的内容

1. 这是我们**登录授权后,出现的请求**,也是授权码过程的第一个步骤

   ![Snipaste_2022-05-24_09-57-31](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/Snipaste_2022-05-24_09-57-31.png)获取到code之后,会出现如下的重定向url,在这里我们可以看到我们的code![Snipaste_2022-05-24_09-59-20](.%E5%AE%9E%E7%8E%B0Github%E7%9A%84OAuth2%E7%9A%84%E6%8E%88%E6%9D%83.assets/Snipaste_2022-05-24_09-59-20.png)

2. 客户端(Java应用进程)到Github的过程没有获取(应该是要抓包) 

3. 第三个步骤则是获取到令牌进行访问

控制台中我们也可以看到输出了对应信息。但是我们为什么看不到token的内容（暂定为通过session获取）



# 其他特殊的认证

见*spring security in action*307页

```java
    private ClientRegistration clientRegistration() {
        ClientRegistration cr = ClientRegistration.withRegistrationId("github")
                .clientId("a7553955a0c534ec5e6b")
                .clientSecret("1795b30b425ebb79e424afa51913f1c724da0dbb")
                .scope(new String[]{"read:user"})
                .authorizationUri("https://github.com/login/oauth/authorize")
                .tokenUri("https://github.com/login/oauth/access_token")
                .userInfoUri("https://api.github.com/user")
                .userNameAttributeName("id")
                .clientName("GitHub")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/{action}/oauth2/code/{registrationId}")
                .build();
        return cr;
    }
```



