package core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.util.Properties;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);
    protected static Properties config;

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {
        config = ConfigReader.loadProperties("staging");
        log.info("Configuration loaded");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {

        DriverManager.initDriver(browser);
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(config.getProperty("baseUrl"));

        log.info("Browser opened: {}", browser);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
        log.info("Browser closed");
    }
}

