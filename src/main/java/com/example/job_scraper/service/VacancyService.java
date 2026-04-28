package com.example.job_scraper.service;

import com.example.job_scraper.dto.VacancyDetailsDto;
import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.dto.VacancySearchParameters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyService {
    Page<VacancyResponseDto> findAll(VacancySearchParameters parameters, Pageable pageable);

    VacancyDetailsDto findById(Long id);
}
