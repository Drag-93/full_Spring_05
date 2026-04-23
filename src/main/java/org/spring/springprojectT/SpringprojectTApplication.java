package org.spring.springprojectT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing// BasicTime 클래스
public class SpringprojectTApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringprojectTApplication.class, args);
	}

}
