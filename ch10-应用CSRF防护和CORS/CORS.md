# CORS

跨域资源共享（Cross-Origin Resource Sharing）

指的是用户在example.com网站，在浏览器默认情况下是不允许访问example.org的资源的。

但可以通过CORS机制完成上述的访问。这是浏览器的一种机制，是用于舒缓跨域调用的严格约束

如果某浏览器进行对后端的请求，**对应MVC的方法会执行**，但是由于跨域约束，浏览器不会将结果响应



## CORS相关请求头

### Origin

Origin：https://foo.example

### Access-Control-Request-Method

出现在预检请求的请求头中，告知实际请求的请求方法

```
Access-Control-Request-Method: <method>
```

### Access-Control-Request-Headers

出现在预检请求的请求头中，告知实际请求会带的请求头

```
Access-Control-Request-Headers: <field-name>[, <field-name>]*
```



## CORS相关响应头

### Access-Control-Allow-Origin

```http
Access-Control-Allow-Origin: <origin> | *
```

对于非*值的响应头值则响应中必须包含Origin，这将告诉客户端：服务器对不同的源站返回不同的内容

```http
Access-Control-Allow-Origin: https://mozilla.org
Vary: Origin
```

### Access-Control-Expose-Headers

在跨域访问资源时，浏览器只能获取到一些基本的响应头

```
Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma
```

如果希望浏览器能获取到更多自定义的响应头则要通过该响应头设置

```
Access-Control-Expose-Headers: X-My-Custom-Header, X-Another-Custom-Header
```

### Access-Control-Max-Age

指的是预检请求的响应结果缓存时间

```
Access-Control-Max-Age: <delta-seconds>
```

### Access-Control-Allow-Credentials

这用于简单请求，简单请求如果设定为带有凭据的请求，若响应中没有`Access-Control-Allow-Credentials：true`即使是简单请求，浏览器也不会响应

```
Access-Control-Allow-Credentials: true
```

### Access-Control-Allow-Methods

预检请求的响应来限制客户端能请求的方法

### Access-Control-Allow-Headers

预检请求的响应来限制客户端能携带的请求头



## 预检请求

<img src=".CORS.assets/preflight_correct.png" alt="img" style="zoom:75%;" />



## 简单请求

何为简单请求？这些请求就不会进行预检请求，而是直接发起实际请求

常见如下

+ 使用下列方法之一：
  + GET
  + HEAD
  + POST
+ 特定请求头：
  + Accept
  + Accept-Language
  + Content-Language
  + Content-Type （需要注意额外的限制）Content-Type 的值仅限于下列三者之一：
    + text/plain
    + multipart/form-data
    + application/x-www-form-urlencoded

