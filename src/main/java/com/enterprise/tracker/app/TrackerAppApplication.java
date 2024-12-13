package com.enterprise.tracker.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.enterprise.tracker.app"})
public class TrackerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackerAppApplication.class, args);
	}

}
