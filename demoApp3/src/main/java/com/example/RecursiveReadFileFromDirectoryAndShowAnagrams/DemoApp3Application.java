package com.example.RecursiveReadFileFromDirectoryAndShowAnagrams;

import com.example.RecursiveReadFileFromDirectoryAndShowAnagrams.service.AnagramService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.demoApp3.Mapper")
@SpringBootApplication
public class DemoApp3Application implements CommandLineRunner {

    @Autowired
    AnagramService anagram;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApp3Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args){
        anagram.write();
        anagram.displayAnagram();
    }
}
