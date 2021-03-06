import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LogsTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(LogsTest.class);
    String env = System.getProperty("browser", "edge");
    String st = System.getProperty("option", "NORMAL");

    @BeforeEach
    public void setUp() {
        logger.info("Браузер = " + env);
        logger.info("Стратегия загрузки страницы = " + st);
        driver = WebDriverFactory.getDriver(env.toLowerCase(), st.toUpperCase());
        logger.info("Драйвер запущен");
    }

    @AfterEach
    public void setDown() {
        if(driver != null) {
            driver.quit();
            logger.info("Драйвер остановлен");
        }
    }

    @Test
    public void availableLogTypesTest() {
        // Получение доступных типов логов
        Set<String> availableLogTypes = driver.manage().logs().getAvailableLogTypes();
        for (String s : availableLogTypes)
            logger.info("Log Type:  " + s);
    }

    @Test
    public void logsByTypeTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");
        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");
        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");
        // Получить логи
        Logs logs = driver.manage().logs();
        LogEntries logsEntries = logs.get(LogType.BROWSER);
        List<LogEntry> logsEntriesList = logsEntries.getAll();
        for (LogEntry logsEntry : logsEntriesList) {
            logger.info(Date.from(Instant.ofEpochSecond(logsEntry.getTimestamp())) + " " +
                    logsEntry.getLevel() + " " + logsEntry.getMessage());
        }
    }

    @Test
    public void logsByTypeAndLevelTest() {
        // Открыть страницу https://www.dns-shop.ru/
        driver.get("https://www.dns-shop.ru/");
        logger.info("Открыта страница dns-shop.ru - https://www.dns-shop.ru/");
        // Нажать на ссылку "Да"
        By linkYesXPath = By.xpath("//a[text()=\"Да\"]");
        WebElement linkYes = driver.findElement(linkYesXPath);
        linkYes.click();
        logger.info("Нажата ссылка \"Да\"");
        // Нажать на ссылку "Бытовая техника"
        By linkBTXPath = By.xpath("//div/a[text()=\"Бытовая техника\"]");
        WebElement linkBT = driver.findElement(linkBTXPath);
        linkBT.click();
        logger.info("Нажата ссылка \"Бытовая техника\"");
        // Получить логи
        Logs logs = driver.manage().logs();
        LogEntries logsEntries = logs.get(LogType.BROWSER);
        List<LogEntry> logsEntriesList = logsEntries.getAll().stream()
                .filter(a -> a.getLevel() == Level.SEVERE)
                .collect(Collectors.toList());
        for (LogEntry logsEntry : logsEntriesList) {
            logger.info(Date.from(Instant.ofEpochMilli(logsEntry.getTimestamp())) + " " +
                    logsEntry.getLevel() + " " + logsEntry.getMessage());
        }
    }
}
