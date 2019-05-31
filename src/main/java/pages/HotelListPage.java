package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

import static org.testng.Assert.assertTrue;

public class HotelListPage extends BasePage {
    private String pageUrl = System.getProperty("baseUrl") + "/article/faces/hotelList.xhtml";
    private By listForm = By.name("j_idt40");

    public HotelListPage(WebDriver driver, Logger log) {
        super(driver, log);
    }

    @Step
    public void openPageUrl() {
        log.info("Opening page: " + pageUrl);
        openUrl(pageUrl);
        assertTrue(isElementDisplayed(listForm), "Hotel List page is opened");
        log.info("Hotels List Page opened!");
    }

    @Step
    public boolean isOpened() {
        return isElementDisplayed(listForm);
    }
}
