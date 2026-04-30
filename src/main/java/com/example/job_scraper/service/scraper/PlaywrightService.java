package com.example.job_scraper.service.scraper;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class PlaywrightService {
    private static final String JOB_ITEM_SELECTOR = "div[data-testid='job-list-item']";
    private static final String LOAD_MORE_BUTTON_SELECTOR = "button[data-testid='load-more']";
    public static final int MAX_VACANCIES = 200;

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;

    @PostConstruct
    public void init() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .setLocale("en-US")
        );
    }

    public String getPageContent(String url) {
        Page page = createPage();
        navigate(page,url, WaitUntilState.DOMCONTENTLOADED);
        page.waitForSelector(JOB_ITEM_SELECTOR);
        loadAllVacancies(page);
        String content = page.content();
        page.close();
        return content;
    }

    private Page createPage() {
        return context.newPage();
    }

    private void navigate(Page page, String url, WaitUntilState state) {
        page.navigate(url, new Page.NavigateOptions().setWaitUntil(state));
    }

    private void loadAllVacancies(Page page) {

        int previousCount = 0;


        Locator button = page.locator(LOAD_MORE_BUTTON_SELECTOR);
        if (button.count() > 0) {
            button.first().click();
            page.waitForTimeout(3000);
        }

        while (true) {
            int currentCount = page.locator(JOB_ITEM_SELECTOR).count();

            if (currentCount >= MAX_VACANCIES) {
                break;
            }
            if (currentCount == previousCount) {
                break;
            }

            previousCount = currentCount;

            page.mouse().wheel(0, 3000);
            page.waitForTimeout(1000);
        }
    }

    @PreDestroy
    public void close() {
        context.close();
        browser.close();
        playwright.close();
    }
}
