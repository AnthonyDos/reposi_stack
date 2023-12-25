package com.pastrycertified.pastrycertified;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@EnableScheduling
@Configuration
@EnableWebMvc
@PropertySource("classpath:config.properties")
@SpringBootApplication
public class PastryCertifiedApplication {

    public static void main(String[] args) {
        SpringApplication.run(PastryCertifiedApplication.class, args);
    }

}

