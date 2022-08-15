package ru.turaev.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    private static final String BEARER_AUTH = "Bearer";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("ru.turaev.user.controller"))
                .build()
                .apiInfo(apiDetails())
                .securitySchemes(List.of(securityScheme()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiDetails() {
        return new ApiInfoBuilder()
                .title("The user service")
                .description("This is API for authentication, registration and user management")
                .version("1.0")
                .contact(new Contact("Denis Turaev", "https://vk.com/denis_turaev", "denis.turaev@bk.ru"))
                .build();
    }

    private SecurityScheme securityScheme() {
        return new ApiKey(BEARER_AUTH, "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(List.of(bearerAuthReference()))
                .build();
    }

    private SecurityReference bearerAuthReference() {
        return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
    }
}
