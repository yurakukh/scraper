package com.example.job_scraper.service.vacancy;

import com.example.job_scraper.dto.VacancyDetailsDto;
import com.example.job_scraper.dto.VacancyResponseDto;
import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.exception.EntityNotFoundException;
import com.example.job_scraper.mapper.VacancyMapper;
import com.example.job_scraper.model.Vacancy;
import com.example.job_scraper.repository.VacancyRepository;
import com.example.job_scraper.specification.VacancySpecification;
import java.util.*;
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

    @Override
    public VacancyDetailsDto getById(Long id) {
        return vacancyRepository.findById(id)
                .map(vacancyMapper::toDetailsDto)
                .orElseThrow(() -> new EntityNotFoundException("Can't find vacancy with id: " + id));
    }

    @Transactional
    @Override
    public void syncVacancies(List<Vacancy> scrapedVacancies) {

        // беремо ВСІ записи (включно з deleted)
        List<Vacancy> existingVacancies = vacancyRepository.findAllIncludingDeleted();

        // мапа link → vacancy (захист від дубля в БД)
        Map<String, Vacancy> existingByLink = existingVacancies.stream()
                .collect(Collectors.toMap(
                        Vacancy::getLink,
                        v -> v,
                        (v1, v2) -> v1 // якщо дубль — беремо перший
                ));

        Set<String> scrapedLinks = new HashSet<>();
        List<Vacancy> toSave = new ArrayList<>();

        for (Vacancy scraped : scrapedVacancies) {

            scrapedLinks.add(scraped.getLink());

            Vacancy existing = existingByLink.get(scraped.getLink());

            if (existing != null) {
                // UPDATE
                update(existing, scraped);
                existing.setDeleted(false); // якщо був soft deleted — відновлюємо
                toSave.add(existing);
            } else {
                // CREATE
                scraped.setDeleted(false);
                toSave.add(scraped);
            }
        }

        // SOFT DELETE тих, яких більше немає
        for (Vacancy existing : existingVacancies) {
            if (!scrapedLinks.contains(existing.getLink())) {
                if (!existing.isDeleted()) {
                    existing.setDeleted(true);
                    toSave.add(existing);
                }
            }
        }

        // 🔥 дедуплікація перед save (критично)
        Map<String, Vacancy> unique = new HashMap<>();
        for (Vacancy v : toSave) {
            unique.put(v.getLink(), v);
        }

        vacancyRepository.saveAll(unique.values());
    }

    private void update(Vacancy target, Vacancy source) {
        target.setTitle(source.getTitle());
        target.setCompanyName(source.getCompanyName());
        target.setLocation(source.getLocation());
        target.setDescription(source.getDescription());
        target.getTags().clear();
        target.getTags().addAll(source.getTags());
    }
}
