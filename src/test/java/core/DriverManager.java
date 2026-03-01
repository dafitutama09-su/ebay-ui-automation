package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initDriver(String browser) {

        boolean isCI = "true".equalsIgnoreCase(System.getenv("GITHUB_ACTIONS"));

        WebDriver webDriver;

        switch (browser.toLowerCase()) {

            case "chrome" -> webDriver = createChromeDriver(isCI);

            case "firefox" -> webDriver = createFirefoxDriver(isCI);

            default -> throw new IllegalArgumentException(
                    "Browser not supported: " + browser
            );
        }

        driver.set(webDriver);
    }

    // =========================
    // CHROME
    // =========================
    private static WebDriver createChromeDriver(boolean isCI) {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");

        if (isCI) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return new ChromeDriver(options);
    }

    // =========================
    // FIREFOX
    // =========================
    private static WebDriver createFirefoxDriver(boolean isCI) {

        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        if (isCI) {
            options.addArguments("-headless");
        }

        return new FirefoxDriver(options);
    }

    // =========================
    // GET DRIVER
    // =========================
    public static WebDriver getDriver() {
        return driver.get();
    }

    // =========================
    // QUIT DRIVER
    // =========================
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

