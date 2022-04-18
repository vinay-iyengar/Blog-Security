package com.learning.blogSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@ApiIgnore
@Controller
public class BlogSecurityApplication {

	public static void main(String[] args) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Current Date" + dtf.format(now));
		SpringApplication.run(BlogSecurityApplication.class, args);
	}

	@RequestMapping("/")
	public String getDocument() {
		return "redirect:/swagger-ui.html";
	}
}