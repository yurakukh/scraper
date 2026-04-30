package com.example.job_scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class JobScraperApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobScraperApplication.class, args);
    }
}
