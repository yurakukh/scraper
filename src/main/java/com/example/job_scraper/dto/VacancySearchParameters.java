package com.example.job_scraper.dto;

import java.util.List;

public record VacancySearchParameters(
        List<String> companies,
        List<String> locations,
        List<String> tags
) {
}
