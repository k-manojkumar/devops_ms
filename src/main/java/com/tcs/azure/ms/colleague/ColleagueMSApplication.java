package com.tcs.azure.ms.colleague;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.tcs.azure.ms.colleague",
	"com.tcs.poc.service",
	"com.tcs.poc.controller",
	"com.tcs.poc.hibernate",
	"com.tcs.poc.pojo"})
public class ColleagueMSApplication extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure (SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(ColleagueMSApplication.class);
	}
}
