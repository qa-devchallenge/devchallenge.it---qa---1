package util;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;


public class BrowserDriverFactory {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private String hubUrl = System.getProperty("hubUrl");
    private String browser;
    private Logger log;

    public BrowserDriverFactory(String browser, Logger log) {
        this.browser = browser.toLowerCase();
        this.log = log;
    }

    public WebDriver createDriver() throws MalformedURLException {
        // Create driver
        log.info("Create driver: " + browser);

        switch (browser) {
            case "chrome":
                //Windows start up
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
                //Linux startup
         //       System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                driver.set(new ChromeDriver());
                break;

            case "chromeheadless":
                //Windows start up
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
                //Linux startup
         //       System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless");
                driver.set(new ChromeDriver(chromeOptions));
                break;

            case "remote-chrome":
                driver.set(getRemoteChromeDriver());
                break;
        }

        return driver.get();
    }

    private WebDriver getRemoteChromeDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--allow-insecure-localhost");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return new RemoteWebDriver(
                URI.create(hubUrl + "/wd/hub").toURL(),
                capabilities);
    }
}