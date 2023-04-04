package com.pws.employees;

import com.pws.employees.utilities.AuditAwareImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableDiscoveryClient
@OpenAPIDefinition
@ComponentScan(basePackages = {"com.pws.employees.*"})
@EnableJpaAuditing
@SpringBootApplication
public class EmployeesApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeesApplication.class, args);
	}
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditAwareImpl();
	}




}


