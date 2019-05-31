package pages;

import entity.Hotel;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Getter
public class RegisterHotelPage extends BasePage {
    private String pageUrl = System.getProperty("baseUrl") + "/article/faces/hotel.xhtml";
    private TopMenu topMenu;

    private By registerHotelForm = By.id("add_hotel");
    private By nameInput = By.name("add_hotel:name");
    private By ratingStarsCollection = By.xpath(".//*[@id='add_hotel:rate']//div[@class='ui-rating-star']");
    private By dateOfConstructionInput = By.name("add_hotel:dateOfConstruction_input");
    private By countryDropdown = By.id("add_hotel:country");
    private By cityDropdown = By.id("add_hotel:city");
    private By shortDescriptionInput = By.name("add_hotel:short_description");
    private By descriptionInput = By.name("add_hotel:description");
    private By notesInput = By.name("add_hotel:notes");
    private By btnSave = By.name("add_hotel:j_idt62");


    public RegisterHotelPage(WebDriver driver, Logger log) {
        super(driver, log);
        this.topMenu = new TopMenu(driver, log);
    }

    @Step
    public void openPageUrl() {
        log.info("Opening page URL: " + pageUrl);
        openUrl(pageUrl);
        log.info("Register Hotel Page opened!");
    }

    @Step
    public void openPage() {
        log.info("Opening page: " + pageUrl);
        topMenu.hoverOverMenuItem("Article")
                .hoverOverMenuItem("New")
                .hoverAndClickMenuItem("Hotel");
        log.info("Register Hotel Page opened!");
    }

    @Step
    public boolean isOpened() {
        return isElementDisplayed(registerHotelForm);
    }

    @Step("Type hotel name: {0}")
    public RegisterHotelPage typeName(String name) {
        log.info("Typing hotel name: " + name);
        waitForVisibilityOf(nameInput);
        type(name, nameInput);
        String nameValue = getAttribute(nameInput, "value").toLowerCase();
        assertEquals(nameValue, name.toLowerCase(), "Name");
        return this;
    }

    @Step("Rate hotel with {0} stars")
    public RegisterHotelPage rateHotel(int rating) {
        log.info("Typing hotel rating: " + rating);
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating should be from 1 to 5 stars");
        }
        List<WebElement> stars = findAll(ratingStarsCollection);
        stars.get(rating - 1).click();
        List<WebElement> activeStar = findAll(By.xpath("//*[@id='add_hotel:rate']/*[contains(@class, 'ui-rating-star-on')]"));
        assertEquals(activeStar.size(), rating, "Actual rating");
        return this;
    }

    @Step("Typing Date Of Construction: {0}")
    public RegisterHotelPage typeDateOfConstruction(final String dateOfConstuction) {
        log.info("Typing dateOfConstuction: " + dateOfConstuction);
        String dateofConstructionJquerySelector = "#add_hotel\\\\:dateOfConstruction_input";
        waitForVisibilityOf(dateOfConstructionInput);
        executeJS(String.format("$('%s').datepicker('setDate', '%s')", dateofConstructionJquerySelector, dateOfConstuction));
        String dateValue = getAttribute(dateOfConstructionInput, "value").toLowerCase();
        assertEquals(dateValue, dateOfConstuction, "Date Of Construction");
        return this;
    }

    @Step("Select country: {0}")
    public RegisterHotelPage selectCountry(final Hotel.Country country) {
        log.info("Selecting country: " + country.getName());
        By valueLocator = By.xpath("//*[@data-label='" + country.getName() + "']");
        assertTrue(isElementDisplayed(countryDropdown), "No country select present");
        click(countryDropdown);
        assertTrue(isElementDisplayed(valueLocator), "Value is displayed");
        click(valueLocator);
        By selectedValue = By.xpath("//*[@id='add_hotel:country_label' and contains(text(), '" + country.getName() + "')]");
        assertTrue(isElementDisplayed(selectedValue), "Selected country: " + country.getName());
        return this;
    }

    @Step("Select city: {0}")
    public RegisterHotelPage selectCity(final Hotel.City city) {
        log.info("Selecting city: " + city.getName());
        By valueLocator = By.xpath("//*[@data-label='" + city.getName() + "']");
        assertTrue(isElementDisplayed(cityDropdown), "No city select present");
        click(cityDropdown);
        assertTrue(isElementDisplayed(valueLocator), "Value is displayed");
        click(valueLocator);
        By selectedValue = By.xpath("//*[@id='add_hotel:city_label' and contains(text(), '" + city.getName() + "')]");
        assertTrue(isElementDisplayed(selectedValue), "Selected city: " + city.getName());
        return this;
    }

    @Step("Type short description: {0}")
    public RegisterHotelPage typeShortDescription(String shortDescription) {
        log.info("Typing short description: " + shortDescription);
        waitForVisibilityOf(shortDescriptionInput);
        type(shortDescription, shortDescriptionInput);
        String shortDescriptionValue = getAttribute(shortDescriptionInput, "value").toLowerCase();
        assertEquals(shortDescriptionValue, shortDescription.toLowerCase(), "Short Description");
        return this;
    }

    @Step("Type description: {0}")
    public RegisterHotelPage typeDesription(String description) {
        log.info("Typing description: " + description);
        waitForVisibilityOf(descriptionInput);
        type(description, descriptionInput);
        String descriptionInputValue = getAttribute(descriptionInput, "value").toLowerCase();
        assertEquals(descriptionInputValue, description.toLowerCase(), "Description");
        return this;
    }

    @Step("Type notes: {0}")
    public RegisterHotelPage typeNotes(String notes) {
        log.info("Typing notes: " + notes);
        waitForVisibilityOf(notesInput);
        type(notes, notesInput);
        String notesValue = getAttribute(notesInput, "value").toLowerCase();
        assertEquals(notesValue, notes.toLowerCase(), "Notes");
        return this;
    }

    @Step("Submit form")
    public HotelListPage submitForm() {
        log.info("Submiting form...");
        waitForVisibilityOf(btnSave);
        click(btnSave);
        return new HotelListPage(driver, log);
    }

    @Step
    public RegisterHotelPage checkAllFormInputsAreDisplayed() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, nameInput),
                "Name input should be displayed");
        softAssert.assertTrue(areElementsPresent(ratingStarsCollection, 2, 5),
                "Rating input should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, dateOfConstructionInput),
                "Date of construction input should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, countryDropdown),
                "Country dropdown should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, cityDropdown),
                "City dropdown should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, shortDescriptionInput),
                "Short Description input should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, descriptionInput),
                "Description input should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, notesInput),
                "Notes input should be displayed");
        softAssert.assertTrue(isNestedElementsDisplayed(registerHotelForm, btnSave),
                "Save btn should be displayed");
        softAssert.assertAll();
        return this;
    }

    @Step("Check required fields marked by asterisk")
    public RegisterHotelPage checkRequiredFieldsMarkedByAsterisk() {
        By nameAsterisk = By.xpath("//*[@for='add_hotel:name']/*[contains(text(), '*')]");
        By dateAsterisk = By.xpath("//*[@for='add_hotel:dateOfConstruction_input']/*[contains(text(), '*')]");
        By countryAsterisk = By.xpath("//*[@for='add_hotel:country_input']/*[contains(text(), '*')]");
        By cityAsterisk = By.xpath("//*[@for='add_hotel:city_input']/*[contains(text(), '*')]");
        By shortDescriptionAsterisk = By.xpath("//*[@for='add_hotel:short_description']/*[contains(text(), '*')]");
        By descriptionAsterisk = By.xpath("//*[@for='add_hotel:description']/*[contains(text(), '*')]");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(isElementDisplayed(nameAsterisk), "Name asterisk is displayed");
        softAssert.assertTrue(isElementDisplayed(dateAsterisk), "Date asterisk is displayed");
        softAssert.assertTrue(isElementDisplayed(countryAsterisk), "Country asterisk is displayed");
        softAssert.assertTrue(isElementDisplayed(cityAsterisk), "City asterisk is displayed");
        softAssert.assertTrue(isElementDisplayed(shortDescriptionAsterisk), "Short description asterisk is displayed");
        softAssert.assertTrue(isElementDisplayed(descriptionAsterisk), "Description asterisk is displayed");
        softAssert.assertAll();
        return this;
    }

    @Step("Check field have validation error")
    public RegisterHotelPage checkRequiredFieldsErrorMessagesAreDisplayed() {
        By locator = By.xpath("//*[contains(@name, 'add_hotel')]/following::*[contains(@class, 'ui-message-error-detail')" +
                "and contains(text(), 'Validation Error: Value is required.')]");
        List<WebElement> errorMessages = findAll(locator);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(errorMessages.size(), 6, "Validation error messages displayed");
        errorMessages.forEach(e -> softAssert.assertTrue(e.isDisplayed()));
        softAssert.assertAll();
        return this;
    }

    @Step("Is element editable: {0}")
    public boolean isElementEditable(By locator) {
        WebElement element = find(locator);
        String readonly = element.getAttribute("readonly");
        return readonly == null;
    }

    public HotelListPage createNewHotel(final Hotel hotel) {
        typeName(hotel.getName());
        rateHotel(hotel.getRating());
        typeDateOfConstruction(hotel.getDateOfConstruction());
        selectCountry(hotel.getCountry());
        selectCity(hotel.getCity());
        typeShortDescription(hotel.getShortDescription());
        typeDesription(hotel.getDescription());
        typeNotes(hotel.getNotes());
        return submitForm();
    }


}
