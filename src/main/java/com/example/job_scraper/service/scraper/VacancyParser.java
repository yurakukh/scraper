package com.example.job_scraper.service.scraper;

import com.example.job_scraper.model.Vacancy;
import java.util.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class VacancyParser {
    private static final String URL = "https://jobs.techstars.com";
    private static final String JOB_ITEM_SELECTOR = "div[data-testid='job-list-item']";
    private static final String LINK_SELECTOR = "a[data-testid='read-more']";
    private static final String H4_SELECTOR = "h4";
    private static final String COMPANY_NAME_SELECTOR = "a[data-testid='link']";
    private static final String LOCATION_SELECTOR = "meta[itemprop='addressLocality']";
    private static final String TAGS_SELECTOR = "div[data-testid='tag'] div";

    public List<Vacancy> parse(Document doc) {
        Elements vacancies = doc.select(JOB_ITEM_SELECTOR);

        List<Vacancy> result = new ArrayList<>();

        for (Element vacancy : vacancies) {

            Element linkEl = vacancy.selectFirst(LINK_SELECTOR);
            if (linkEl == null) continue;

            Element titleEl = vacancy.selectFirst(H4_SELECTOR);
            String title = titleEl != null
                    ? titleEl.text()
                    : "";

            Element companyEl = vacancy.selectFirst(COMPANY_NAME_SELECTOR);
            String company = companyEl != null
                    ? companyEl.text()
                    : "";

            Element locationEl = vacancy.selectFirst(LOCATION_SELECTOR);
            String location = locationEl != null
                    ? locationEl.attr("content")
                    : "";

            String link = linkEl.attr("href");
            String fullLink = link.startsWith("http")
                    ? link
                    : URL + link;


            Set<String> tags = new HashSet<>(vacancy.select(TAGS_SELECTOR).eachText());

            Vacancy v = new Vacancy();
            v.setTitle(title);
            v.setCompanyName(company);
            v.setLink(fullLink);
            v.setLocation(location);
            v.setTags(tags);
            result.add(v);
        }
        return result;
    }

    public String parseDescription(Document doc) {
        Element el = doc.selectFirst("div[data-testid='careerPage']");
        return el != null ? el.text().substring(0, 999) : "";
    }
}
