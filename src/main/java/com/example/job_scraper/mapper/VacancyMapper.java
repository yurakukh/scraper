package com.example.job_scraper.mapper;

import com.example.job_scraper.dto.VacancyDetailsDto;
import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.model.Vacancy;
import org.springframework.stereotype.Component;

@Component
public class VacancyMapper {
    public VacancyResponseDto toResponseDto(Vacancy vacancy) {
        if (vacancy == null) {
            return null;
        }
        return new VacancyResponseDto(
                vacancy.getId(),
                vacancy.getTitle(),
                vacancy.getCompanyName(),
                vacancy.getLocation(),
                vacancy.getTags()
        );
    }

    public VacancyDetailsDto toDetailsDto(Vacancy vacancy) {
        if (vacancy == null) {
            return null;
        }
        return new VacancyDetailsDto(
                vacancy.getId(),
                vacancy.getTitle(),
                vacancy.getCompanyName(),
                vacancy.getLocation(),
                vacancy.getDescription(),
                vacancy.getLink(),
                vacancy.getTags()
        );
    }
}
