package com.example.demoApp3;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.example.demoApp3.Mapper")
@SpringBootApplication
public class DemoApp3Application {

	public static void main(String[] args) {
		SpringApplication.run(DemoApp3Application.class, args);
	}

}

