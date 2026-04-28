package com.example.job_scraper.mapper;

import com.example.job_scraper.config.MapperConfig;
import com.example.job_scraper.dto.VacancyDetailsDto;
import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.model.Vacancy;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface VacancyMapper {
    VacancyResponseDto toResponseDto(Vacancy vacancy);
    VacancyDetailsDto toDetailsDto(Vacancy vacancy);
}
