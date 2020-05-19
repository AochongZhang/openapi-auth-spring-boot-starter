<p>
	<h1 align="center">openapi-auth-spring-boot-starter</h1>
</p>
<p align="center">
	<strong>用于开放式接口的请求认证和数据加密解密</strong>
</p>
<p align="center">
	<img alt="GitHub release (latest by date)" src="https://img.shields.io/github/v/release/AochongZhang/openapi-auth-spring-boot-starter">
	<img alt="GitHub" src="https://img.shields.io/github/license/AochongZhang/openapi-auth-spring-boot-starter">
</p>

## 简介
在项目中我们通常会遇到需要提供给第三方调用的接口，由于是开放式接口，所以需要认证和加密机制，避免被他人恶意调用或泄露数据。

本项目提供三种功能

+ **请求认证**: 该功能默认开启, 在服务端配置用户请求唯一key和密钥后，用户调用接口时需要通过唯一key、密钥、时间戳、随机字符串和请求参数拼接后进行摘要运算生成签名
+ **请求参数解密**: 该功能默认关闭, 开启后用户请求时需要对请求参数进行加密
+ **响应加密数据**: 该功能默认关闭, 开启后用户收到响应时需要进行解密

**重要**: 所有功能只对使用**@RequestBody**方式接受参数的接口生效

## 安装
引入Maven依赖
```xml
<dependency>
  <groupId>com.zhangaochong</groupId>
  <artifactId>openapi-auth-spring-boot-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

## 快速使用
1. SpringBoot启动类加@**EnableOpenApiAuth**注解
2. 配置文件添加配置
```yaml
openapi-auth:
  users:
    secret-key-map:
      用户唯一key: 用户密钥
      ...
```
3. 在需要用到的Controller类或方法上加**@OpenApiAuth**注解
4. 此时该接口已具备请求认证功能，用户请求时需生成签名

## 接口请求

### 参数说明

| 参数名    | 说明                       |
| --------- | -------------------------- |
| accessKey | 分配给用户的唯一key        |
| secretKey | 用户的唯一key对应的密钥    |
| timestamp | 当前时间毫秒级时间戳       |
| nonce     | 随机字符串，每次请求不重复 |
| sign      | 签名，签名生成方式见后文   |

### 签名生成规则

1. 将accessKey,secretKey,timestamp,nonce,sign及业务参数按参数名ascii码顺序排序
2. 排序后按key1=value1&key2=value2...拼接字符串
3. 将拼接后字符串进行MD5运算生成签名

### 请求示例


```
使用POST json方式

原有业务参数，如
{
	"name: "zhangsan",
	"age: 20
}

生成签名
sign = md5(accessKey=test&age=20&name=zhangsan&nonce=abcdefg&secretKey=test&timestamp=1589877113000)

加入认证参数
{
	"name: "zhangsan",
	"age: 20,
	"accessKey": "test"
	"timestamp": 1589877113000
	"nonce": "abcdefg"
	"sign": "62c0f590c756089adf3632d18a55e7b6"
}
```
## 自定义功能

### 自定义用户源
默认通过配置文件配置用户唯一key及密钥，可以通过自定义功能从数据库获取。

1. 创建类继承**AbstractAuthHandler**, 实现getUserSecretKey, saveUserNonce, getUserLastNonce方法
2. 配置该类对象为AuthHandler的Bean
```java
@Bean
public AuthHandler authHandler() {
    return new XxxAuthHandler();
}
```

### 开启请求参数解密
默认请求参数解密关闭，通过修改配置文件开启
1. 修改配置文件
```yaml
openapi-auth:
  request-decrypt: enable
```
2. 默认只进行Base64解码，用过实现**DecryptHandler**类的**decrypt**方法配置自定义解密方法
3. 配置该类对象为DecryptHandler的Bean
```java
@Bean
public DecryptHandler decryptHandler() {
    return is -> {
        return 解密方法(is);
    };
}
```