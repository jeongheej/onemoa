package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class Project01Application {

	public static void main(String[] args) {
		SpringApplication.run(Project01Application.class, args);
	}

}
