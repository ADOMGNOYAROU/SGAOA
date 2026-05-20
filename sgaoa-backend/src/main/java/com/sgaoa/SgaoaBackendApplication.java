package com.sgaoa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
public class SgaoaBackendApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hash = encoder.encode("admin123");
		System.out.println("HASH: " + hash);
		SpringApplication.run(SgaoaBackendApplication.class, args);
	}
}