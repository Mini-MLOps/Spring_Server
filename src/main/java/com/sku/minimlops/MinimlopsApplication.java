package com.sku.minimlops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MinimlopsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinimlopsApplication.class, args);
	}

}
