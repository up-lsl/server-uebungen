package com.example.SpringApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringAppApplication
 * ====================
 * Diese Klasse enthält die Main-Methode des Projekts
 */

@SpringBootApplication
@RestController
public class SpringAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
        SpringApplication.run(SpringAppApplication.class, args);
	}

}
