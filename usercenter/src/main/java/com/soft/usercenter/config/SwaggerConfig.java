package com.soft.usercenter.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName SwaggerConfig.java
 * @Description TODO
 * @createTime 2021年03月25日 11:22:00
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.niit.soft.client.api.controller"))
//注释 swagger文档配成 注释模式
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("骸冰项目在线接口文档。")
                .description("swagger-bootstrap-ui")
                .contact(new Contact("骸冰", "https://github.com/1914866205", "1836686674@qq.com"))
                .termsOfServiceUrl("http://localhost:8088/")
                .version("1.0")
                .build();
    }

}
