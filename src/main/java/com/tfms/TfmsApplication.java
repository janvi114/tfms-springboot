package com.tfms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class TfmsApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TfmsApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TfmsApplication.class, args);
        System.out.println("==============================================");
        System.out.println("TFMS Application Started Successfully!");
        System.out.println("==============================================");
    }
}