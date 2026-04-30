package com.example.job_scraper.specification;

import com.example.job_scraper.dto.VacancySearchParameters;
import com.example.job_scraper.model.Vacancy;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.data.jpa.domain.Specification;

public class VacancySpecification {

    public static Specification<Vacancy> build(VacancySearchParameters parameters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (parameters.companies() != null && !parameters.companies().isEmpty()) {
                List<String> companiesLower = parameters.companies().stream()
                        .map(s -> s.toLowerCase(Locale.ROOT))
                        .toList();

                predicates.add(
                        criteriaBuilder.lower(root.get("companyName")).in(companiesLower)
                );
            }

            if (parameters.locations() != null && !parameters.locations().isEmpty()) {
                List<String> locationsLower = parameters.locations().stream()
                        .map(s -> s.toLowerCase(Locale.ROOT))
                        .toList();

                predicates.add(
                        criteriaBuilder.lower(root.get("location")).in(locationsLower)
                );
            }

            if (parameters.tags() != null && !parameters.tags().isEmpty()) {

                Join<Vacancy, String> tagsJoin = root.join("tags");

                List<String> tagsLower = parameters.tags().stream()
                        .map(s -> s.toLowerCase(Locale.ROOT))
                        .toList();

                predicates.add(
                        criteriaBuilder.lower(tagsJoin).in(tagsLower)
                );

                query.distinct(true);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
