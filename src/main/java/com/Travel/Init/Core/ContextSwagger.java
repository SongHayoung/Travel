package com.Travel.Init.Core;

import com.Travel.Core.Annotations.TODO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@TODO("url 만들어야함")
@Configuration
@EnableSwagger2
public class ContextSwagger {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .pathMapping("/");
    }

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "TRAVEL REST API",
                "API 리스트",
                "API v1.0",
                "Terms of service",
                "lovelydays95@gmail.com",
                "APACHE 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html");
        return apiInfo;
    }
}
