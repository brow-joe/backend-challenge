package br.com.jonathan.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.inject.Inject;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Value("${spring.application.swagger.title}")
    private String title;
    @Value("${spring.application.swagger.description}")
    private String description;
    @Value("${spring.application.swagger.terms-of-service-url}")
    private String termsOfServiceUrl;
    @Value("${spring.application.swagger.license}")
    private String license;
    @Value("${spring.application.swagger.license-url}")
    private String licenseUrl;
    @Value("${spring.application.version}")
    private String version;

    @Bean
    @Inject
    public Docket getDocket(ApiInfo info) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/" + version + ".*"))
                .build()
                .apiInfo(info);
    }

    @Bean
    public ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .termsOfServiceUrl(license)
                .licenseUrl(licenseUrl)
                .build();
    }

}
