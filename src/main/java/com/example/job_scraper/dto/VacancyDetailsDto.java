package com.example.job_scraper.dto;

import java.util.Set;

public record VacancyDetailsDto(
        Long id,
        String title,
        String companyName,
        String location,
        String link,
        Set<String> tags
) {
}
