package com.example.job_scraper.repository;

import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.model.Vacancy;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Optional<Vacancy> findByLink(String link);

    @EntityGraph(attributePaths = "tags")
    Page<Vacancy> findAll(Specification<Vacancy> specification, Pageable pageable);

    @EntityGraph(attributePaths = "tags")
    Optional<Vacancy> findById(Long id);
}
