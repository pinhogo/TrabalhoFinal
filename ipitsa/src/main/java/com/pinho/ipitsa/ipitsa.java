package com.pinho.ipitsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pinho")
public class ipitsa {

	public static void main(String[] args) {
		SpringApplication.run(ipitsa.class, args);
	}

}
