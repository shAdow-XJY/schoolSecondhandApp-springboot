# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.5/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#using-boot-devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.5/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

### leveldb
### 位置util/LevelDBUtils
### private final String dbFolder = "shadow/db/msg.db";
### //dbFolder 是db存储目录
### //不同项目应修改自己的前缀以区别

### mysql mybatisplus
### 位置 resource/application-produce.yaml
### 位置 CodeGenerate mapper/service/entity等文件夹名字配置，包名配置
### 位置MyBatisPlusConfig 和 启动类（本项目为shAdowApplication） 添加@MapperScan("com.example.shadow.mapper")
### 启动mybatisplus 的步骤：1、连接好mysql 2、启动运行Codegenerate 3、自动生成mysql的数据库里的表的controller、mapper、service等文件
### 在CodeGenerate中strategyConfig.setInclude();可以单独设置生成特定的表，不生成全部表的文件

### mail
### 位置 resource/application-produce.yaml

### swagger UI
### 前端接口网站http://localhost:9000/swagger-ui.html#
### 要使用成功必须不被拦截
### 在config/WebMvcConfig.java 中 添加不被拦截的路径
### 不然在接口文档输入都不行

### redis
### redis 需要springboot集成外，还需要自己在本地进行安装，并启动redis-server

### 2021.10.20
### 完善注册登录的功能，基本这个功能已经完善
### 完成聊天功能的整合，但是获取聊天列表只能在数据库contact表手动添加，后续会在订单确认通道时在写入表中双方的聊天id
### 目前可以聊天，以及可以获取与某一个用户的聊天记录（已自动写入数据库）