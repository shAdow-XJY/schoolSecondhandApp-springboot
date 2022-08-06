package com.example.shadow;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 *
 <!-- https://mvnrepository.com/artifact/io.swagger/swagger-annotations -->
 <dependency>
 <groupId>io.swagger</groupId>
 <artifactId>swagger-annotations</artifactId>
 <version>1.6.2</version>
 </dependency>
 <dependency>
 <groupId>mysql</groupId>
 <artifactId>mysql-connector-java</artifactId>
 <scope>runtime</scope>
 </dependency>
 <dependency>
 <groupId>org.apache.velocity</groupId>
 <artifactId>velocity-engine-core</artifactId>
 <version>2.3</version>
 </dependency>
 <dependency>
 <groupId>com.baomidou</groupId>
 <artifactId>mybatis-plus-generator</artifactId>
 <version>3.4.1</version>
 </dependency>
 <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
 <dependency>
 <groupId>com.baomidou</groupId>
 <artifactId>mybatis-plus-boot-starter</artifactId>
 <version>3.4.2</version>
 </dependency>

 */
public class CodeGenerate {
    public static void main(String[] args) {
        //构建代码生成器对象
        AutoGenerator autoGenerator=new AutoGenerator();
        //配置策略
        //1。全局配置
        GlobalConfig config=new GlobalConfig();
        //2。获取系统当前目录
        String projectPath=System.getProperty("user.dir");
        //3。设置代码输出文件夹
        config.setOutputDir(projectPath+"/src/main/java");
//        4.设置作者
        config.setAuthor("shAdow");
        //5.设置是否打开资源目录文件
        config.setOpen(false);
        //6.设置是否覆盖原来手工编写的代码
        config.setFileOverride(false);
        //7。设置是否要去掉生成的Service I前缀
        config.setServiceName("%sService");
        //8。
        config.setIdType(IdType.ASSIGN_ID);
        //9.设置时间类型
        config.setDateType(DateType.ONLY_DATE);
        //10。设置是否自动生成Swagger文档
        config.setSwagger2(true);
        //11。将配置配置到代码生成器上
        autoGenerator.setGlobalConfig(config);

        //12。设置数据源
        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        String url="jdbc:mysql://localhost:3306/twohand?characterEncoding=utf-8&useSSL=false&serverTimezon=GMT%2B8&allowPublicKeyRetrieval=true";
        String userName="root";
        String password="xjying11";
        String dataSourceDriver="com.mysql.cj.jdbc.Driver";
        DbType dbType=DbType.MYSQL;
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setUsername(userName);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setDriverName(dataSourceDriver);
        dataSourceConfig.setDbType(dbType);
        autoGenerator.setDataSource(dataSourceConfig);
        //13。包的配置
        PackageConfig packageConfig=new PackageConfig();
        packageConfig.setParent("com.example");
        packageConfig.setModuleName("shadow");
        packageConfig.setController("controller");
        packageConfig.setService("service");
        packageConfig.setMapper("mapper");
        packageConfig.setEntity("entity");
        autoGenerator.setPackageInfo(packageConfig);

        //14.策略的配置
        StrategyConfig strategyConfig=new StrategyConfig();
        strategyConfig.setInclude();
        //strategyConfig.setInclude("User");
        //strategyConfig.setExclude("User");
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);//下划线转驼峰命名
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategyConfig.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);//Rest风格的controller
        strategyConfig.setControllerMappingHyphenStyle(true);//将网站链接转化为下划线命名格式

//        strategyConfig.setLogicDeleteFieldName("deleted"); //设置逻辑删除字段

//        //自动填充策略
//        TableFill gcTime=new TableFill("gcTime", FieldFill.INSERT);
//        TableFill gmTime=new TableFill("gmTime",FieldFill.INSERT_UPDATE);
//        strategyConfig.setTableFillList(Arrays.asList(gcTime,gmTime));
//        //乐观锁的配置
//        strategyConfig.setVersionFieldName("version");
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.execute();
    }
}
/**
 * spring:
 *   #数据源配置
 *   datasource:
 *     driver-class-name: com.mysql.cj.jdbc.Driver
 *     url: jdbc:mysql://112.74.166.85:3306/springboot?characterEncoding=utf-8&useSSL=false&serverTimezon=GMT%2B8 #mysql8以上要配置时区，东八区
 *     username: root
 *     password: 12345678
 *
 *  #MyBatis属性配置
 * mybatis-plus:
 *   configuration:
 *     map-underscore-to-camel-case: false #取消将大写转化为_小写的自动转化
 *     log-impl: org.apache.ibatis.logging.stdout.StdOutImpl #配置日志打印
 *   global-config:
 *     db-config:
 *       logic-delete-field: deleted  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
 *       logic-delete-value: 0 # 逻辑已删除值(默认为 1)
 *       logic-not-delete-value: 1 # 逻辑未删除值(默认为 0)
 */