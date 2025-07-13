package com.research.assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ResearchAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchAssistantApplication.class, args);
	}

}
