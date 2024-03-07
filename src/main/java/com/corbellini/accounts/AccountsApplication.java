package com.corbellini.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import com.corbellini.accounts.config.EnvironmentConfig;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

// @formatter:off
@OpenAPIDefinition(
	info = @Info(
	  title = "Accounts microservice REST API Documentation",
		description = "Accounts microservice REST API Documentation", 
		version = "v1",
		contact = @Contact(
		  name = "Arthur Corbellini", 
			email = "email.placeholder@test.com",
			url = "https://www.genericUrlPlaceholder.com"),
		license = @License(
		  name = "Apache 2.0", 
			url = "https://www.genericUrlPlaceholder.com")),
	externalDocs = @ExternalDocumentation(
		description = "Accounts microservice REST API Documentation",
		url = "https://www.genericUrlPlaceholder.com/swagger-ui.html"))
// @formatter:on
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {EnvironmentConfig.class})
@SpringBootApplication
public class AccountsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }

}
