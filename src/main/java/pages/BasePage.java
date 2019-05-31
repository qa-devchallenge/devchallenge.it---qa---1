package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;

public class BasePage {
    protected WebDriver driver;
    protected Logger log;

    public BasePage(WebDriver driver, Logger log) {
        this.driver = driver;
        this.log = log;
    }

    /**
     * Open page with given URL
     */
    protected void openUrl(String url) {
        driver.get(url);
    }

    /**
     * Find element using given locator
     */
    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Find all elements using given locator
     */
    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Click on element with given locator when its visible
     */
    @Step("Click on ({0})")
    protected void click(By locator) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofSeconds(15))
                .pollingEvery(ofMillis(500))
                .ignoring(ElementNotVisibleException.class).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            driver.findElement(locator).click();
        } catch (TimeoutException e) {
            log.error("Can not click on element '" + locator + "'");
        }
    }

    /**
     * Hover over element with given locator when its visible
     */
    @Step("Hover on {0}")
    protected void hoverOver(By locator) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofSeconds(15))
                .pollingEvery(ofMillis(500))
                .ignoring(ElementNotVisibleException.class).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            new Actions(driver).moveToElement(driver.findElement(locator)).perform();
        } catch (TimeoutException e) {
            log.error("Can not hover on element '" + locator + "'");
        }
    }

    /**
     * Hover over and click on element with given locator when its visible
     */
    @Step("Hover and click on {0}")
    protected void hoverOverAndClick(By locator) {
        Wait wait = new FluentWait(driver)
                .withTimeout(ofSeconds(15))
                .pollingEvery(ofMillis(500))
                .ignoring(ElementNotVisibleException.class).ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            new Actions(driver).moveToElement(driver.findElement(locator)).click().perform();
        } catch (TimeoutException e) {
            log.error("Can not hover on element '" + locator + "'");
        }
    }

    /**
     * Type given text into element with given locator
     */
    protected void type(String text, By locator) {
        waitForVisibilityOf(locator, 5);
        find(locator).sendKeys(text);
    }

    /**
     * Wait for specific ExpectedCondition for the given amount of time in seconds
     */
    private void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(condition);
    }

    /**
     * Wait for given number of seconds for element with given locator to be visible
     * on the page
     */
    protected void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
            }
            attempts++;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        return isElementDisplayed(locator, 2);
    }

    protected boolean isElementDisplayed(By locator, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException ex) {
            log.warn("Element '" + locator + "' is not displayed after searching for the element for " + seconds + " seconds");
            return false;
        }
    }

    protected boolean isNestedElementsDisplayed(By parent, By child) {
        return isNestedElementsDisplayed(parent, child, 2);
    }

    protected boolean isNestedElementsDisplayed(By parent, By child, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        try {
            wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(parent, child));
            return true;
        } catch (TimeoutException ex) {
            log.warn("Elements '" + parent + "' and '" + child + "' is not displayed after searching for the element for "
                    + seconds + " seconds");
            return false;
        }
    }

    @Step("Execute Script")
    public Object executeJS(String script) {
        log.info("Executing JS script: " + script);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script);
    }

    protected boolean areElementsPresent(By locator, int seconds, int number) {
        boolean areElementsPresent = false;

        for (int time = 0; time < seconds; time++) {
            if (driver.findElements(locator).size() == number) {
                areElementsPresent = true;
                break;
            }
        }
        if (!areElementsPresent) {
            log.error("Elements are not present: " + number + " in locator: " + locator);
        }

        return areElementsPresent;
    }

    protected String getAttribute(By locator, String attribute) {
        String attributeValue = "Attribute is not present";

        try {
            attributeValue = new WebDriverWait(driver, 2)
                    .until(ExpectedConditions.presenceOfElementLocated(locator))
                    .getAttribute(attribute);
        } catch (WebDriverException e) {
            log.error("Attribute " + attribute + " is not presented in locator: " + locator);
        }

        return attributeValue;
    }
}
