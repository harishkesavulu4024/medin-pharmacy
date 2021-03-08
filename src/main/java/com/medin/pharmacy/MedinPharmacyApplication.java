package com.medin.pharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="com.medin.pharmacy.*")
@EntityScan(basePackages = "com.medin.pharmacy.*")
@EnableJpaRepositories(basePackages = "com.medin.pharmacy.*")
public class MedinPharmacyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedinPharmacyApplication.class, args);
	}

}
