package com.example.job_scraper.repository;

import com.example.job_scraper.model.Vacancy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Optional<Vacancy> findByLink(String link);
}
