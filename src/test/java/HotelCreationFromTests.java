import entity.Hotel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HotelListPage;
import pages.RegisterHotelPage;
import ru.yandex.qatools.allure.annotations.Stories;
import util.TestBase;
import util.TestListener;

import static entity.Hotel.createDummyHotel;
import static org.testng.Assert.assertTrue;

@Listeners({TestListener.class})
public class HotelCreationFromTests extends TestBase {
    private RegisterHotelPage newHotelPage;

    @BeforeMethod(alwaysRun = true)
    public void initData() {
        newHotelPage = new RegisterHotelPage(driver, log);
    }

    @Test
    @Stories("Verify that user can open New Hotel page:")
    public void verifyThatUserCanOpen_newHotelPage() {
        newHotelPage.openPage();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        newHotelPage.checkAllFormInputsAreDisplayed()
                .checkRequiredFieldsMarkedByAsterisk();
        newHotelPage.submitForm();
        newHotelPage.checkRequiredFieldsErrorMessagesAreDisplayed();
    }

    @Test
    @Stories("Verify that user can edit Name field:")
    public void verifyThatUserCanEdit_NameField() {
        newHotelPage.openPageUrl();
        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        assertTrue(newHotelPage.isElementEditable(newHotelPage.getNameInput()), "Name input is editable");

        Hotel hotel = createDummyHotel();
        HotelListPage hotelListPage = newHotelPage.createNewHotel(hotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }


    @Test
    @Stories("Verify that user can edit Global Rating field")
    public void verifyThatUserCanEdit_GlobalRatingField() {
        newHotelPage.openPageUrl();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel fourStarHotel = createDummyHotel().withRating(4);
        HotelListPage hotelListPage = newHotelPage.createNewHotel(fourStarHotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }

    @Test
    @Stories("Verify that user can add Date of Construction of new hotel")
    public void verifyThatUserCanEdit_DateOfConstructionField() {
        newHotelPage.openPageUrl();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel hotel = createDummyHotel().withDateofConstruction("12/31/14");
        HotelListPage hotelListPage = newHotelPage.createNewHotel(hotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }

    @Test
    @Stories("Verify that user can add Country of new hotel")
    public void verifyThatUserCanAdd_Country() {
        newHotelPage.openPageUrl();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel ukrainianHotel = createDummyHotel().withCountry(Hotel.Country.UKRAINE);
        HotelListPage hotelListPage = newHotelPage.createNewHotel(ukrainianHotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }

    @Test
    @Stories("Verify that user can add City of new hotel")
    public void verifyThatUserCanAdd_City() {
        newHotelPage.openPageUrl();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel kyivHotel = createDummyHotel().withCity(Hotel.City.KYIV);
        HotelListPage hotelListPage = newHotelPage.createNewHotel(kyivHotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }

    @Test
    @Stories("Verify that user can add Short Description of new hotel")
    public void verifyThatUserCanAdd_ShortDescription() {
        newHotelPage.openPageUrl();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel hotel = createDummyHotel().withShortDescription("Short AUTOTEST Description");
        HotelListPage hotelListPage = newHotelPage.createNewHotel(hotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }

    @Test
    @Stories("Verify that user can add Description of new hotel")
    public void verifyThatUserCanAdd_Description() {
        newHotelPage.openPageUrl();

        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel hotel = createDummyHotel().withDescription("AUTOTEST Description");
        HotelListPage hotelListPage = newHotelPage.createNewHotel(hotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }

    @Test
    @Stories("Verify that user can add Notes of new hotel")
    public void verifyThatUserCanAdd_Notes() {
        newHotelPage.openPageUrl();
        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel hotelWithNotes = createDummyHotel().withNotes("AUTOTEST Notes");
        HotelListPage hotelListPage = newHotelPage.createNewHotel(hotelWithNotes);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");


        newHotelPage.openPageUrl();
        assertTrue(newHotelPage.isOpened(), "Register Hotel page is displayed");
        Hotel emptyNotesHotel = createDummyHotel().withNotes("");
        hotelListPage = newHotelPage.createNewHotel(emptyNotesHotel);
        assertTrue(hotelListPage.isOpened(), "Hotels List page is displayed");
    }
}
