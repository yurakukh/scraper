package com.example.job_scraper;

import com.example.job_scraper.service.scraper.VacancyScraperService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JobScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobScraperApplication.class, args);
	}
}
