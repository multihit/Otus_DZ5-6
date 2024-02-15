package personalAreaPageTest;

import com.github.javafaker.Faker;
import components.Header;
import components.PersonalDataPage;
import components.popups.AuthPopups;
import data.cities.ICityData;
import data.cities.RussiaCityData;
import data.english.EnglishLevel;
import data.fieldData.InputFieldData;
import data.workGraf.WorkGrafData;
import factory.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class PersonalAreaTest {

    private Logger logger = (Logger) LogManager.getLogger(PersonalAreaTest.class);

    private WebDriver driver;
    protected Faker faker = new Faker();

    @BeforeEach
    public void init() {
        this.driver = new DriverFactory().create();
        logger.info("Start driver");
    }

    @AfterEach
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Stop driver");
    }

    @Test
    public void savePersonalData() {
        new MainPage(driver).open("/");

        logger.info("Waiting marker tel");
        Header header = new Header(driver);
        header.waitMarkerTelNumber();
        header.waitSignInBtnIsPresent();
        header.waitSignInBtnToBeClicable();

        logger.info("Check status auth popup");
        AuthPopups authPopups = new AuthPopups(driver);
        authPopups.popupShouldNotBeVisible();

        logger.info("Start of test logic. Login in LK");
        header.clickSignInButton();
        authPopups.popupShouldBeVisible();

        authPopups.enterDataEmail();
        authPopups.enterDataPassword();
        authPopups.clickSignInBtnPopups();

        header.checkLogoUser();
        logger.info("Login in LK successful. Switch personal data page");
        header.clickPersonalArea();

        PersonalDataPage personalData = new PersonalDataPage(driver);

        personalData.clearFieldsData(InputFieldData.FNAME);
        personalData.clearFieldsData(InputFieldData.FNAMELATIN);
        personalData.clearFieldsData(InputFieldData.LNAME);
        personalData.clearFieldsData(InputFieldData.LNAMELATIN);
        personalData.clearFieldsData(InputFieldData.BLOGNAME);
        personalData.clearFieldsData(InputFieldData.DATEOFBRTH);
        logger.info("Clear fields successful");

        personalData.addNewDataFields(InputFieldData.FNAME, "Junior");
        personalData.addNewDataFields(InputFieldData.FNAMELATIN, faker.name().name());
        personalData.addNewDataFields(InputFieldData.LNAME, faker.name().name());
        personalData.addNewDataFields(InputFieldData.LNAMELATIN, faker.name().name());
        personalData.addNewDataFields(InputFieldData.BLOGNAME, faker.name().name());
        personalData.addNewDataFields(InputFieldData.DATEOFBRTH,
                faker.date().birthday().toInstant().atZone(ZoneId.
                        systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        logger.info("Add names and date birthday successful");

        ICityData[] cityData = RussiaCityData.values();
        ICityData city = faker.options().nextElement(cityData);

        personalData.selectCountry(city);
        personalData.selectCity(city);
        logger.info("Add country and city birthday successful");

        personalData.selectEnglishLevel(EnglishLevel.BEGINNER);
        logger.info("Add english level successful");

        personalData.switchRelocate(true);
        logger.info("Relocate switch successful");

        personalData.switchWorkFormat(true, WorkGrafData.REMOTELY);
              logger.info("Add work format successful");

        personalData.selectCommunicationMethod(InputFieldData.TELEGRAM, faker.name().name());
        logger.info("Add first communication method successful");

//           personalData.clickAddCommunicationMethod();
//          personalData.selectCommunicationMethod(InputFieldData.HABR, faker.name().name());


        personalData.selectGender("Мужской");
        logger.info("Add gender successful");

        personalData.addNewDataFields(InputFieldData.COMPANY, faker.company().name());
        personalData.addNewDataFields(InputFieldData.POSITION, faker.company().profession());
        logger.info("Add work information successful");

        personalData.clickSavePersonalData();
    }

    @Test
    public void checkFieldsDataIsEmpty() {
        new MainPage(driver).open("/");

        logger.info("Waiting marker tel");
        Header header = new Header(driver);
        header.waitMarkerTelNumber();
        header.waitSignInBtnIsPresent();
        header.waitSignInBtnToBeClicable();

        logger.info("Check status auth popup");
        AuthPopups authPopups = new AuthPopups(driver);
        authPopups.popupShouldNotBeVisible();

        logger.info("Start of test logic. Login in LK");
        header.clickSignInButton();
        authPopups.popupShouldBeVisible();

        authPopups.enterDataEmail();
        authPopups.enterDataPassword();
        authPopups.clickSignInBtnPopups();

        header.checkLogoUser();
        logger.info("Login in LK successful. Switch personal data page");
        header.clickPersonalArea();

        PersonalDataPage personalData = new PersonalDataPage(driver);
        personalData.assertFieldsData(InputFieldData.FNAME);
        personalData.assertFieldsData(InputFieldData.FNAMELATIN);
        personalData.assertFieldsData(InputFieldData.LNAME);
        personalData.assertFieldsData(InputFieldData.LNAMELATIN);
        personalData.assertFieldsData(InputFieldData.BLOGNAME);
        personalData.assertFieldsData(InputFieldData.DATEOFBRTH);

        personalData.checkFieldsDataIsNotEmpty();

    }
}
