package com.javaconnectoracle.filemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SpringBootApplication
public class FilemanagerApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(FilemanagerApplication.class, args);
    }

    @Override
    public void addViewControllers(@SuppressWarnings("null") ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login"); // Trỏ đến login.html khi truy cập root "/"
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/templates/"); // Đường dẫn tới thư mục chứa các template
        resolver.setSuffix(".html");
        return resolver;
    }
}

