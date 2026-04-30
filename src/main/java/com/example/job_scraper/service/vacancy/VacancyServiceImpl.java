package com.example.job_scraper.service.vacancy;

import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.mapper.VacancyMapper;
import com.example.job_scraper.model.Vacancy;
import com.example.job_scraper.repository.VacancyRepository;
import com.example.job_scraper.specification.VacancySpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;

    @Override
    public Page<VacancyResponseDto> getAll(VacancySearchParameters parameters, Pageable pageable) {
        return vacancyRepository.findAll(VacancySpecification.build(parameters), pageable)
                .map(vacancyMapper::toResponseDto);
    }

    @Transactional
    @Override
    public void syncVacancies(List<Vacancy> scrapedVacancies) {

        Map<String, Vacancy> existingByLink = loadExistingVacancies();
        Set<String> scrapedLinks = extractLinks(scrapedVacancies);
        Map<String, Vacancy> result = new HashMap<>();

        for (Vacancy scraped : scrapedVacancies) {
            Vacancy merged = merge(scraped, existingByLink.get(scraped.getLink()));
            result.put(merged.getLink(), merged);
        }

        markDeleted(existingByLink, scrapedLinks, result);
        vacancyRepository.saveAll(result.values());
    }

    private void update(Vacancy target, Vacancy source) {
        target.setTitle(source.getTitle());
        target.setCompanyName(source.getCompanyName());
        target.setLocation(source.getLocation());
        target.getTags().clear();
        target.getTags().addAll(source.getTags());
    }

    private Map<String, Vacancy> loadExistingVacancies() {
        return vacancyRepository.findAllIncludingDeleted().stream()
                .collect(Collectors.toMap(
                        Vacancy::getLink,
                        v -> v,
                        (v1, v2) -> v1
                ));
    }

    private Set<String> extractLinks(List<Vacancy> vacancies) {
        return vacancies.stream()
                .map(Vacancy::getLink)
                .collect(Collectors.toSet());
    }

    private Vacancy merge(Vacancy scraped, Vacancy existing) {
        if (existing == null) {
            scraped.setDeleted(false);
            return scraped;
        }

        update(existing, scraped);
        existing.setDeleted(false);
        return existing;
    }

    private void markDeleted(
            Map<String, Vacancy> existingByLink,
            Set<String> scrapedLinks,
            Map<String, Vacancy> result
    ) {
        for (Vacancy existing : existingByLink.values()) {
            if (!scrapedLinks.contains(existing.getLink()) && !existing.isDeleted()) {
                existing.setDeleted(true);
                result.put(existing.getLink(), existing);
            }
        }
    }
}
