INSTALL
1. Запустити базу даних
   docker compose up -d

2. Встановити Playwright
   mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

3. Запустити додаток
   mvn spring-boot:run

4. Перевірити API
   http://localhost:8080/jobs