package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;


public class TopMenu extends BasePage {
    private By topMenu = By.id("header_form:j_idt9");

    public TopMenu(WebDriver driver, Logger log) {
        super(driver, log);
    }


    @Step("Hover over menu item: {0}")
     TopMenu hoverOverMenuItem(String menuName){
        By topMenuItem = By.xpath("//*[@id='header_form:j_idt9']//*[contains(text(), '" + menuName + "')]");
        waitForVisibilityOf(topMenuItem, 3);
        hoverOver(topMenuItem);
        return this;
    }

    @Step("Hover and click menu item: {0}")
     TopMenu hoverAndClickMenuItem(String menuName){
        By topMenuItem = By.xpath("//*[@id='header_form:j_idt9']//*[contains(text(), '" + menuName + "')]");
        waitForVisibilityOf(topMenuItem, 3);
        hoverOverAndClick(topMenuItem);
        return this;
    }
}
