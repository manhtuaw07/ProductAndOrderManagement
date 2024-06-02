package com.example.productAndOrderManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProductAndOrderManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductAndOrderManagementApplication.class, args);
	}

}
