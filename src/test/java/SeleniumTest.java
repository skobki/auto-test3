import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumTest {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SeleniumTest.class);
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
    public void basicAuthTest() {
        // Открыть страницу the-internet.herokuapp.com
        driver.get("https://the-internet.herokuapp.com/basic_auth");
        logger.info("Открыта страница the-internet.herokuapp.com - https://the-internet.herokuapp.com/basic_auth");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Открыть страницу the-internet.herokuapp.com с авторизацией
        driver.get("http://admin:admin@the-internet.herokuapp.com/basic_auth");
        logger.info("Открыта страница the-internet.herokuapp.com - https://the-internet.herokuapp.com/basic_auth");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void fileUploadTest() {
        // Открыть страницу webdriveruniversity.com
        driver.get("https://webdriveruniversity.com/File-Upload/index.html");
        logger.info("Открыта страница webdriveruniversity.com - https://webdriveruniversity.com/File-Upload/index.html");
        // Приложить файл upload.txt
        String fileUploadXpath = "//input[@type=\"file\"]";
        WebElement fileUpload = driver.findElement(By.xpath(fileUploadXpath));
        // !!! Поменять путь на свой
        fileUpload.sendKeys("C:\\Users\\these\\auto-test3\\src\\upload\\upload.txt");
        logger.info("Файл приложен");
        // Отправить файл
        String btnSubmitXpath = "//input[@type=\"submit\"]";
        WebElement btnSubmit = driver.findElement(By.xpath(btnSubmitXpath));
        btnSubmit.click();
        logger.info("Файл отправлен!");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
