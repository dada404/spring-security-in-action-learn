# CSRF是什么

> Cross-Site Request Forgery

用户A登录了系统X，然后A访问了一个恶意网站，触发脚本使用A原先登录系统X后完成认证的凭据来发送一些恶意请求给服务器（如转账等）

<img src="https://cs8.intsig.net/sync/enhance_jpg?token=4F936634BB414145h7A9K7Kf&file_name=gAVLQP0FRJb2A2Fg6E1892SF.jpg&t=1653016415710&rotate=0&mode=0" style="zoom:80%;" />





# CSRF防护

CSRF的实现是在用户登录的正常网页**外**的恶意网页触发执行脚本，所有我们要**保证用户的某个修改请求是用户在正常页面发出的请求**

具体的防护方式：

+ 用户必须使用一个GET请求（非修改性质的请求）来获取一个唯一令牌（由应用程序生成）

+ 用户后序的修改请求（POST,GET,DELETE）必须带上这个令牌



# CSRF在Spring Security中

**CSRF在SpringSecurity是默认开启的**

![image-20220520111433831](.%E5%BA%94%E7%94%A8CSRF%E9%98%B2%E6%8A%A4%E5%92%8CCORS.assets/image-20220520111433831.png)

## 禁用方式

```java
http.csrf().disable();
```



# 自定义

相关自定义不做阐述





