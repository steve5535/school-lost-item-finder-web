package com.study.schoollostitemfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SchoolLostItemFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolLostItemFinderApplication.class, args);
    }

}
