package com.example.job_scraper.controller;

import com.example.job_scraper.service.scraper.VacancyScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraper")
@RequiredArgsConstructor
public class ScraperController {

    private final VacancyScraperService scraperService;

    @PostMapping
    public String run() {
        scraperService.scrape();
        return "Scraping started";
    }
}