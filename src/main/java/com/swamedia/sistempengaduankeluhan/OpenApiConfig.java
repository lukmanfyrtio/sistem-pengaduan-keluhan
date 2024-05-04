package com.swamedia.sistempengaduankeluhan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Sistem Aplikasi Pengaduan Warga").description("\r\n"
						+ "The \"Sistem Aplikasi Pengaduan Warga\" (Citizen Complaint Management System) is a web-based application designed to facilitate the process of reporting and managing complaints from citizens. It serves as a centralized platform for citizens to submit their complaints regarding various issues, such as public services, infrastructure problems, or community concerns.")
						.contact(new Contact().name("Lukman").url("https://github.com/lukmanfyrtio")));
	}
}
