package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String mostSimpleIndexPage() {
		return "<!DOCTYPE html>" +
				"<html>" +
					"<head>" +
						"<title>Spring-Tomcat-Test</title>" +
					"</head>" +
					"<body>" +
						"<h2>Der Test von Spring auf Tomcat war erfolgreich!</h2>" +
					"</body>" +
				"</html>";
	}

}
