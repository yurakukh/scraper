package com.example.job_scraper.controller;

import com.example.job_scraper.dto.VacancyDetailsDto;
import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vacancies")
public class VacancyController {

    private final VacancyService vacancyService;

    @GetMapping
    public Page<VacancyResponseDto> getVacancies(VacancySearchParameters parameters, Pageable pageable) {
        return vacancyService.findAll(parameters, pageable);
    }

    @GetMapping("/{id}")
    public VacancyDetailsDto getVacancyById(@PathVariable Long id) {
        return vacancyService.findById(id);
    }
}
