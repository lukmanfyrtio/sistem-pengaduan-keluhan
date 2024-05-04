package com.swamedia.sistempengaduankeluhan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.swamedia.sistempengaduankeluhan.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sistem Aplikasi Pengaduan Warga")
                .description("\r\n"
                		+ "The \"Sistem Aplikasi Pengaduan Warga\" (Citizen Complaint Management System) is a web-based application designed to facilitate the process of reporting and managing complaints from citizens. It serves as a centralized platform for citizens to submit their complaints regarding various issues, such as public services, infrastructure problems, or community concerns.")
                .version("1.0")
                .build();
    }
}
