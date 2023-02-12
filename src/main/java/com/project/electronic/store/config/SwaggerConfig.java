package com.project.electronic.store.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket=new Docket(DocumentationType.SWAGGER_2);
        docket.securityContexts(Arrays.asList(securityContext()));
        docket.securitySchemes(Arrays.asList(apiKey()));
        ApiInfo apiInfo=getApiInfo();
        docket.apiInfo(apiInfo);
        return docket;
    }

    private ApiInfo getApiInfo() {
        ApiInfo apiInfo=new ApiInfo(
                "Electronic Store Backend Using Spring Boot",
                "This is a electronics store project",
                "1.0.0V",
                null,
                null,
                null,
                null,
                new ArrayList<>()
        );
        return apiInfo;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
