package com.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spring.app", "player"})
public class AppApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppApplication.class, args);
	}

}
