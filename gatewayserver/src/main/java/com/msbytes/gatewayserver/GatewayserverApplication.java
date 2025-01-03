package com.msbytes.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator abcBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
				.route(p -> p.path("/abcbank/accounts/**")
						.filters(f -> f.rewritePath("/abcbank/accounts/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p.path("/abcbank/loans/**")
						.filters(f -> f.rewritePath("/abcbank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p.path("/abcbank/cards/**")
						.filters(f -> f.rewritePath("/abcbank/loans/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.build();

	}

}
