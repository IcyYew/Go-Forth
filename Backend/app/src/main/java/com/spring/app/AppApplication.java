package com.spring.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application Class.
 * @author Michael Geltz
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.spring.app", "player", "clans"})
@EnableJpaRepositories(basePackages = {"player", "resources", "troops", "clans"})
@EnableTransactionManagement
@EntityScan(basePackages = {"player", "resources", "troops", "clans"})
public class AppApplication {

	/**
	 * Runs the application.
	 * @param args
	 */
	public static void main(String[] args)  {
		SpringApplication.run(AppApplication.class, args);
	}

}
