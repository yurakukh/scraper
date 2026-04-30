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
        scrape();
    }

    @Override
    public void scrape() {
        String html = playwrightService.getPageContent(URL);
        Document document = Jsoup.parse(html);
        List<Vacancy> vacancies = vacancyParser.parse(document);
        vacancyService.syncVacancies(vacancies);
    }

}
