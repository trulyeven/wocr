package com.trulyeven.wocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // 스케쥴링 가능
public class WocrApplication {

	public static void main(String[] args) {
		SpringApplication.run(WocrApplication.class, args);
	}

}
