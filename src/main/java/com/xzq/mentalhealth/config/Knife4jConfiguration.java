package com.xzq.mentalhealth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置
 *
 *
 * @author xzq
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class Knife4jConfiguration {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String groupName="3.X版本";
        //设置只在开发中环境中启动swagger
        Docket docket=new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("心理健康咨询系统 ")
                        .description("组队项目")
//                        .termsOfServiceUrl("http://yaomaoyang.com")
                        .contact(new Contact("谢志强",null,"2498529257@qq.com"))
                        .version("3.0")
                        .build())
                //分组名称
                .groupName(groupName)
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xzq.mentalhealth.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}