package com.amazon.scrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.amazon")
public class AmazonScrapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonScrapperApplication.class, args);
	}

}
