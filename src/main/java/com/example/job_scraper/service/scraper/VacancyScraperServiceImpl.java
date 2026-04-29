package com.example.job_scraper.service.scraper;

import com.example.job_scraper.model.Vacancy;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyScraperServiceImpl implements VacancyScraperService {

    private static final String URL = "https://jobs.techstars.com/jobs";

    private final VacancyParser vacancyParser;
    private final PlaywrightService playwrightService;

    @PostConstruct
    public void test() {
        scrape();
    }

    @Override
    public void scrape() {
        String html = playwrightService.getPageContent(URL);
        Document document = Jsoup.parse(html);
        List<Vacancy> vacancies = vacancyParser.parse(document);
        enrichWithDescription(vacancies);

        //for testing
        vacancies.forEach(v ->
                System.out.println(
                        "-----------------------------\n"
                        + "Title: " + v.getTitle() + "\n"
                        + "Company: " + v.getCompanyName() + "\n"
                        + "Link: " + v.getLink() + "\n"
                        + "Location: " + v.getLocation() + "\n"
                        + "Tags: " +  v.getTags() + "\n"
                        + "Description: " + v.getDescription() + "\n"
                        + "-----------------------------\n"
                )
        );

    }

    private void enrichWithDescription(List<Vacancy> vacancies) {
        vacancies.stream()
                .limit(3)
                .forEach(v -> {
                    try {
                        String html = playwrightService.getJobDescription(v.getLink());
                        Document doc = Jsoup.parse(html);
                        String description = vacancyParser.parseDescription(doc);
                        v.setDescription(description);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to fetch description for vacancy: " + v.getLink(), e);
                    }
                });
    }
}
