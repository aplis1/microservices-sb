package com.msbytes.cards;

import com.msbytes.cards.dto.CardsContactInfo;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.msbytes.cards.controller") })
@EnableJpaRepositories("com.msbytes.cards.repository")
@EntityScan("com.msbytes.cards.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = CardsContactInfo.class)
@OpenAPIDefinition(
		info = @Info(
				title = "Cards microservice REST API Documentation",
				description = "EazyBank Cards microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Madan Reddy",
						email = "tutor@msbytes.com",
						url = "https://www.msbytes.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.msbytes.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "EazyBank Cards microservice REST API Documentation",
				url = "https://www.msbytes.com/swagger-ui.html"
		)
)
public class CardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}
}
