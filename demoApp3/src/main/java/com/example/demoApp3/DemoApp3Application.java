package com.example.demoApp3;
import com.example.demoApp3.Service.AnagramService;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;


@MapperScan("com.example.demoApp3.Mapper")
@SpringBootApplication
@AllArgsConstructor
public class DemoApp3Application implements CommandLineRunner {

    AnagramService anagram;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApp3Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws IOException {
        anagram.openSession();
        anagram.createTableAndFunction();
        anagram.writeAndShow();

    }
}

