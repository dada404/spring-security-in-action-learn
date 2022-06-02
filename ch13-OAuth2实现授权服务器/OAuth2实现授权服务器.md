# 常见组件

![img](.OAuth2%E5%AE%9E%E7%8E%B0%E6%8E%88%E6%9D%83%E6%9C%8D%E5%8A%A1%E5%99%A8.assets/enhance_jpgtoken=6BEDF1D8784D4708RS95h9E6&file_name=12fBJ33RTrX7YaFY79RP0ra6.jpg&t=1653450535445&rotate=0&mode=0)

# 密码模式

## AuthServerConfig

```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
    extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager manager;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(manager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("password")
                .scopes("read");
    }
}
```

## WebSecurityConfig

```java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        UserDetails u = User.withUsername("tom")
                            .password("123456")
                            .authorities("read")
                            .build();

        manager.createUser(u);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
```

## 启动服务后使用idea的http client进行简单验证

```http
POST http://client:secret@localhost:8080/oauth/token
Content-Type: application/x-www-form-urlencoded

grant_type=password&username=tom&password=123456&scope=read
```

## 测试结果

Oauth默认返回的UUID

```json
{
  "access_token": "b014556d-c73a-4a45-8368-b8c15e0dd226",
  "token_type": "bearer",
  "expires_in": 41596,
  "scope": "read"
}
```



# 授权码模式

## AuthServerConfig

```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
    extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager manager;
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(manager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code")
                .scopes("read")
                .redirectUris("http://localhost:9090/home");
    }
}
```

## WebSecurityConfig

同上



## 测试认证

### 访问url获取code(1)

http://localhost:8080/oauth/ authorize?response_type=code&client_id=client&scope=read

![image-20220525112443038](.OAuth2%E5%AE%9E%E7%8E%B0%E6%8E%88%E6%9D%83%E6%9C%8D%E5%8A%A1%E5%99%A8.assets/image-20220525112443038.png)

### 权限确认(1)

![image-20220525112503508](.OAuth2%E5%AE%9E%E7%8E%B0%E6%8E%88%E6%9D%83%E6%9C%8D%E5%8A%A1%E5%99%A8.assets/image-20220525112503508.png)

重定向到了http://localhost:9090/home?code=m7KDt4

### http client获取token(2)

```http
POST http://client:secret@localhost:8080/oauth/token
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&scope=read&code=m7KDt4
```

```json
{
  "access_token": "c107cc1a-5e5a-43f6-9135-3fae31de81cc",
  "token_type": "bearer",
  "expires_in": 43199,
  "scope": "read"
}
```

多次请求

```json
{
  "error": "invalid_grant",
  "error_description": "Invalid authorization code: m7KDt4"
}
```





# 客户端凭据模式

```java
clients.inMemory()
    .withClient("client1")
    .secret("secret1")
    .authorizedGrantTypes("client_credentials")
    .scopes("info");
```

访问

```http
###
POST http://client1:secret1@localhost:8080/oauth/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials&scope=info
```



# 刷新令牌

```java
clients.inMemory()
    .withClient("client2")
    .secret("secret2")
    .authorizedGrantTypes(
    "password",
    "refresh_token")
    .scopes("read");
```

## 初次获取令牌

会获取到当前令牌和刷新令牌

```json
{
  "access_token": "b5b1055c-4391-46f4-9eb2-726110f39fd0",
  "token_type": "bearer",
  "refresh_token": "29e18400-50b8-40c4-93f0-32614c57c940",
  "expires_in": 43199,
  "scope": "read"
}
```

## 通过刷新令牌获取令牌？

```

```

