package com.tvse.uam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * UserAccessApplication class, spring boot starter class of our UAM import
 * org.springframework.data.jpa.repository.config.EnableJpaRepositories;
 * 
 * @author techmango (https://www.techmango.net/)
 *
 */
@EnableEurekaClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "com.tvse.uam.repository" })
public class UserAccessApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAccessApplication.class, args);
	}
}
