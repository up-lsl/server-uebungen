package com.example.SpringApp;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer
 * ==================
 * Wird nur benötigt, um die Applikation als (SpringBoot)Servlet zu kennzeichnen, damit der Build bzw. Compiler korrekt arbeiten
 */

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringAppApplication.class);
	}

}
