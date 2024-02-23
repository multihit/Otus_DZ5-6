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
    String fakerName;
    String fakerlName;
    String fakerlastName;
    String fakerlastNameLatin;

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

        fakerName = String.valueOf(faker.name().firstName());
        fakerlName = String.valueOf(faker.name().firstName());
        fakerlastName = String.valueOf(faker.name().lastName());
        fakerlastNameLatin = String.valueOf(faker.name().lastName());

        personalData.addNewDataFields(InputFieldData.FNAME, fakerName);
        personalData.addNewDataFields(InputFieldData.FNAMELATIN, fakerlName);
        personalData.addNewDataFields(InputFieldData.LNAME, fakerlastName);
        personalData.addNewDataFields(InputFieldData.LNAMELATIN, fakerlastNameLatin);
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

        personalData.selectCommunicationMethod(InputFieldData.TELEGRAM, faker.name().lastName());
        logger.info("Add first communication method successful");

        personalData.selectGender("Мужской");
        logger.info("Add gender successful");

        personalData.addNewDataFields(InputFieldData.COMPANY, faker.company().name());
        personalData.addNewDataFields(InputFieldData.POSITION, faker.company().profession());
        logger.info("Add work information successful");

        personalData.clickSavePersonalData();


        authPopups.popupShouldNotBeVisible();
        new MainPage(driver).open("/");
        logger.info("Waiting marker tel");
        logger.info("Check status auth popup");
        authPopups.popupShouldNotBeVisible();
        logger.info("Start of test logic. Login in LK");
        header.checkLogoUser();
        logger.info("Login in LK successful. Switch personal data page");
        header.clickPersonalArea();

        personalData.assertFieldsDataName("fname", fakerName);
        personalData.assertFieldsDataName("fname_latin", fakerlName);
        personalData.assertFieldsDataName("lname", fakerlastName);
        personalData.assertFieldsDataName("lname_latin", fakerlastNameLatin);
        personalData.assertFieldsData();
        logger.info("comparison of personal data successfully");
    }

}


