package core;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.util.Properties;

public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    protected static Properties config;
    protected static Dotenv dotenv;

    protected static String username;
    protected static String password;

    @BeforeSuite(alwaysRun = true)
    public void loadConfig() {

        // =========================
        // LOAD ENV CONFIG (staging/prod)
        // =========================
        String env = System.getProperty("env");
        env = (env == null || env.isEmpty()) ? "staging" : env;

        config = ConfigReader.loadProperties(env);
        log.info("Loaded environment: {}", env);

        // =========================
        // LOAD .env (LOCAL ONLY)
        // =========================
        dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        // =========================
        // PRIORITY:
        // 1. GitHub Secrets (CI)
        // 2. .env (local)
        // =========================
        username = System.getenv("LOGIN_USERNAME") != null
                ? System.getenv("LOGIN_USERNAME")
                : dotenv.get("Login_Username");

        password = System.getenv("LOGIN_PASSWORD") != null
                ? System.getenv("LOGIN_PASSWORD")
                : dotenv.get("Login_Password");

        log.info("Credential loaded (safe)");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {

        DriverManager.initDriver(browser);

        DriverManager.getDriver().manage().window().maximize();

        DriverManager.getDriver().get(config.getProperty("baseUrl"));

        log.info("Open browser: {}", browser);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }
}

