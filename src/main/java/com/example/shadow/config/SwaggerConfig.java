package com.example.shadow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


//http://localhost:9000/swagger-ui.html
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 这里主要是配置API的显示
     * A builder which is intended to be the primary interface into the Springfox framework. Provides sensible defaults and convenience methods for configuration.
     * 其实就是swagger的配置,一次注入Docket Bean对应一个组，可以配置多个组进行多次注入
     *
     * @return
     */
    @Bean
    public Docket docket(Environment environment) {
        /**
         * 判断项目是否处于开发环境下,正式发布的时候需要关闭swagger
         *    Profiles profiles=Profiles.of("dev","test");
         *    boolean isDevOrTest=environment.acceptsProfiles(profiles);
         */


        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name("token").description("用户token，标识用户身份")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数

        ApiInfo apiInfo = new ApiInfo(
                "shAdow Api文档",
                "",
                "1.0", "www.myTeamUrl.com",
                new Contact("", "www.myWeb.com", "1290232854@qq.com"), "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)  //这里配置了信息
                .groupName("secondHand")
                .globalOperationParameters(pars)
                .select()
                .paths(PathSelectors.any())
                .build();
        //.enable(false)   是否在浏览器启用swagger
//                .select()
//                //这里配置了要扫描接口的方式,默认全部
//                //.withClassAnnotation(Controller.class)//扫描所有带有Controller的类
//                .apis(RequestHandlerSelectors.basePackage("org.scut.v1.controller"))
//
//                //路径过滤，api里面符合规范的才会被扫描
//                .paths(PathSelectors.any())
//                .build();
    }
}

/**
 * @ApiModel("这是一个User实体类") public class User{
 * @ApiModelProperty("这是name属性") public String name;
 * @ApiModelProperty("这是password属性") public String password;
 * }
 * @ApiParam("给参数加注释")
 * @ApiOperation("用在方法前面，给方法加注释")
 */
