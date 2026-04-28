package com.example.job_scraper.service;

import com.example.job_scraper.dto.VacancyDetailsDto;
import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.mapper.VacancyMapper;
import com.example.job_scraper.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    @Override
    public Page<VacancyResponseDto> findAll(VacancySearchParameters parameters, Pageable pageable) {
        return vacancyRepository.findAll(pageable)
                .map(vacancyMapper::toResponseDto);
    }

    @Override
    public VacancyDetailsDto findById(Long id) {
        return vacancyRepository.findById(id)
                .map(vacancyMapper::toDetailsDto)
                .orElseThrow(() -> new RuntimeException("Can't find vacancy with id: " + id));
    }
}
