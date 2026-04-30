package com.example.job_scraper.service.scraper;

import com.example.job_scraper.model.Vacancy;
import com.example.job_scraper.service.vacancy.VacancyService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyScraperServiceImpl implements VacancyScraperService {

    private static final String URL = "https://jobs.techstars.com/jobs";

    private final VacancyParser vacancyParser;
    private final PlaywrightService playwrightService;
    private final VacancyService vacancyService;

    @Async
    @Scheduled(fixedDelay = 3600000)
    public void scheduledScrape() {
        System.out.println("Scraping started: " + LocalDateTime.now());

        scrape();

        System.out.println("Scraping finished: " + LocalDateTime.now());
    }

    @Override
    public void scrape() {
        String html = playwrightService.getPageContent(URL);
        Document document = Jsoup.parse(html);
        List<Vacancy> vacancies = vacancyParser.parse(document);
        enrichWithDescription(vacancies);

        vacancyService.syncVacancies(vacancies);
    }

    private void enrichWithDescription(List<Vacancy> vacancies) {
        vacancies.stream()
                .limit(20) // Limit to first 20 vacancies to avoid too many requests
                .forEach(v -> {
                    try {
                        String html = playwrightService.getJobDescription(v.getLink());
                        Document doc = Jsoup.parse(html);
                        String description = vacancyParser.parseDescription(doc);
                        v.setDescription(description);
                    } catch (Exception e) {
                        System.out.println("Failed to fetch description for vacancy: " + v.getLink());
                    }
                });
    }
}
