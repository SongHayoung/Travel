package com.Travel.Init.Core;

import com.Travel.Core.Annotations.TODO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:/properties/swagger.properties")
@EnableSwagger2
public class ContextSwagger {
    @Value("${swagger.title}") String title;
    @Value("${swagger.description}") String description;
    @Value("${swagger.version}") String version;
    @Value("${swagger.termsOfServiceUrl}") String termsOfServiceUrl;
    @Value("${swagger.contact.name}") String contactName;
    @Value("${swagger.contact.url}") String contactUrl;
    @Value("${swagger.contact.email}") String contactEmail;
    @Value("${swagger.license}") String license;
    @Value("${swagger.licenseUrl}") String licenseUrl;

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
        StringVendorExtension vendorExtension1 = new StringVendorExtension("name1", "value1");
        StringVendorExtension vendorExtension2 = new StringVendorExtension("name2", "value2");
        List<VendorExtension> vendorExtensionList = new ArrayList<>();
        vendorExtensionList.add(vendorExtension1);
        vendorExtensionList.add(vendorExtension2);

        ApiInfo apiInfo = new ApiInfo(
                title,
                description,
                version,
                termsOfServiceUrl,
                new Contact(contactName, contactUrl, contactEmail),
                license,
                licenseUrl,
                vendorExtensionList);
        return apiInfo;
    }
}