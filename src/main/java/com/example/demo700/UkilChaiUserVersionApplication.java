package com.example.demo700;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync; // 🟢 Add this!
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync // 🟢 This tells Spring to use background threads
public class UkilChaiUserVersionApplication {

	public static void main(String[] args) {
		SpringApplication.run(UkilChaiUserVersionApplication.class, args);
	}

}
