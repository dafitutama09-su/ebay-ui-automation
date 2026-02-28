package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {

        boolean isCI = "true".equals(System.getenv("GITHUB_ACTIONS"));

        WebDriver webDriver;

        switch (browser.toLowerCase()) {

            // ================= CHROME =================
            case "chrome" -> {

                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();

                options.addArguments("--disable-notifications");
                options.addArguments("--start-maximized");

                if (isCI) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                    options.addArguments("--window-size=1920,1080");
                }

                webDriver = new ChromeDriver(options);
            }

            // ================= FIREFOX =================
            case "firefox" -> {

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions options = new FirefoxOptions();

                if (isCI) {
                    options.addArguments("-headless");
                }

                webDriver = new FirefoxDriver(options);
            }

            default -> throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

