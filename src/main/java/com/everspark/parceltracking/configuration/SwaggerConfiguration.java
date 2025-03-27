package com.everspark.parceltracking.configuration;

import com.everspark.parceltracking.constant.SwaggerConstant;
import com.everspark.parceltracking.constant.PackageConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage(PackageConstant.CONTROLLER_PACKAGE))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact apiContactInfo = new Contact(SwaggerConstant.API_INFO_CONTACT_NAME,
                                      SwaggerConstant.API_INFO_CONTACT_WEBSITE,
                                      SwaggerConstant.API_INFO_CONTACT_EMAIL);
        return new ApiInfo(
                SwaggerConstant.API_INFO_TITLE,
                SwaggerConstant.API_INFO_DESCRIPTION,
                SwaggerConstant.API_INFO_VERSION,
                SwaggerConstant.API_INFO_TERM_OF_SERVICE,
                apiContactInfo,
                SwaggerConstant.API_INFO_LICENSE_TITLE,
                SwaggerConstant.API_INFO_LICENSE_URL, Collections.emptyList());
    }
}
