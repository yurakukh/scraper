package com.example.job_scraper.controller;

import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.service.vacancy.VacancyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Jobs", description = "Operations with job vacancies")
@RequiredArgsConstructor
@RestController
@RequestMapping("/jobs")
public class VacancyController {

    private final VacancyService vacancyService;

    @Operation(summary = "Get all vacancies with filters and pagination")
    @GetMapping
    public Page<VacancyResponseDto> getAllVacancies(
            VacancySearchParameters parameters,
            @PageableDefault(size = 20) Pageable pageable) {
        return vacancyService.getAll(parameters, pageable);
    }
}
