package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;

public class TestBase {
    private String baseAppUrl = System.getProperty("baseUrl") + "/article/faces/welcome.xhtml";
    protected WebDriver driver;
    protected Logger log;

    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true)
    public void setUp(@Optional("remote-chrome") String browser, ITestContext ctx) throws MalformedURLException {
        String testName = ctx.getCurrentXmlTest().getName();
        log = LogManager.getLogger(testName);

        BrowserDriverFactory factory = new BrowserDriverFactory(browser, log);
        driver = factory.createDriver();
        driver.manage().window().setSize(new Dimension(1024, 768));
        driver.get(baseAppUrl);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        log.info("Close driver");
        if (driver != null) {
            // Close browser
            driver.quit();
            driver = null;
        }
    }
}
