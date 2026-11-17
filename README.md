# springboot-demo

一个最小可运行的 Spring Boot 3 + Java 17 示例服务，提供 3 个 REST 接口。

## 环境要求

- JDK 17+
- Maven 3.6+

## 快速开始

```bash
# 编译
mvn clean package

# 运行
mvn spring-boot:run
# 或
java -jar target/springboot-demo-1.0.0.jar
```

服务默认监听 `http://localhost:8080`。

## 接口说明

所有接口统一返回如下结构：

```json
{
  "code": 0,
  "message": "ok",
  "data": { },
  "timestamp": "2026-11-17T08:30:00Z"
}
```

### 1. 问候 `GET /api/hello`

| 参数 | 类型 | 必填 | 默认值 | 说明 |
| --- | --- | --- | --- | --- |
| name | string | 否 | World | 问候对象 |

```bash
curl "http://localhost:8080/api/hello?name=WorkBuddy"
```

```json
{
  "code": 0,
  "message": "ok",
  "data": { "greeting": "Hello, WorkBuddy!", "service": "springboot-demo" }
}
```

### 2. 服务器时间 `GET /api/time`

返回格式化时间、ISO8601、时区、毫秒时间戳以及服务运行时长。

```bash
curl "http://localhost:8080/api/time"
```

### 3. 内容回显 `POST /api/echo`

请求体：

| 字段 | 类型 | 必填 | 说明 |
| --- | --- | --- | --- |
| message | string | 是 | 需要回显的内容，最长 500 字符 |
| upper | boolean | 否 | 是否转为大写，默认 false |

```bash
curl -X POST "http://localhost:8080/api/echo" \
  -H "Content-Type: application/json" \
  -d '{"message":"hello spring","upper":true}'
```

```json
{
  "code": 0,
  "message": "ok",
  "data": { "echo": "HELLO SPRING", "length": 12, "upperCase": true }
}
```

参数校验失败时返回 `400`，结构一致，`code` 为 `400`。

## 测试

```bash
mvn test
```

## 项目结构

```
src/main/java/com/example/demo
├── DemoApplication.java        # 启动类
├── common
│   ├── ApiResponse.java        # 统一响应体
│   └── GlobalExceptionHandler.java
├── controller
│   └── ApiController.java      # 三个接口
└── dto
    └── EchoRequest.java
```
