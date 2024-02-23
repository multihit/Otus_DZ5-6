package components;

import common.AbsCommon;
import data.cities.ICityData;
import data.english.EnglishLevel;
import data.fieldData.InputFieldData;
import data.workGraf.WorkGrafData;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class PersonalDataPage extends AbsCommon {
    String countries;
    String city;
    String english;


    public PersonalDataPage(WebDriver driver) {
        super(driver);
    }

    public void clearFieldsData(InputFieldData... inputFieldData) {
        for (InputFieldData fieldData : inputFieldData) {
            driver.findElement(By.cssSelector(String.format("input[name='%s']", fieldData.getName()))).clear();
        }
    }


    public void addNewDataFields(InputFieldData inputFieldData, String data) {
        driver.findElement(By.cssSelector(String.format("input[name='%s']", inputFieldData.getName())))
                .sendKeys(data);
    }

    public void selectCountry(ICityData cityData) {
        WebElement russiaSelectElement = driver.findElement(By.cssSelector("[data-slave-selector='.js-lk-cv-dependent-slave-city']"));
        russiaSelectElement.click();

        countries = cityData.getCountriesData().getNameCountry();

        WebElement countryListContainer = russiaSelectElement
                .findElement(By.xpath(".//*[contains(@class, 'js-custom-select-options-container')]"));
        waitTools.waitForCondition(ExpectedConditions.not(ExpectedConditions
                .attributeContains(countryListContainer, "class", "hide")));

        driver.findElement(By.cssSelector(String.format("[title='%s']",
                cityData.getCountriesData().getNameCountry()))).click();

        waitTools.waitForCondition(ExpectedConditions
                .attributeContains(countryListContainer, "disabled", "disabled"));
    }

    public void selectCity(ICityData cityData) {
        WebElement moscowSelectElement = driver.findElement(By.xpath("//*[contains(@class, 'js-lk-cv-dependent-slave-city')]"));
        moscowSelectElement.click();

        city = cityData.getName();

        WebElement cityListContainer = moscowSelectElement
                .findElement(By.xpath(".//*[contains(@class, 'js-custom-select-options-container')]"));
        waitTools.waitForCondition(ExpectedConditions.not(ExpectedConditions
                .attributeContains(cityListContainer, "class", "hide")));

        driver.findElement(By.cssSelector(String.format("[title='%s']", cityData.getName()))).click();

        waitTools.waitForCondition(ExpectedConditions.attributeContains(cityListContainer, "class", "hide"));
    }

    public void selectEnglishLevel(EnglishLevel englishLevel) {
        WebElement englishLevelSelectElement = driver.findElement(By
                .xpath("//input[@name='english_level']/ancestor:: div[contains(@class, 'js-lk-cv-custom-select')]"));
        englishLevelSelectElement.click();

        english = englishLevel.getName();

        WebElement levelListContainer = englishLevelSelectElement
                .findElement(By.xpath(".//*[contains(@class, 'js-custom-select-options-container')]"));
        waitTools.waitForCondition(ExpectedConditions.not(ExpectedConditions
                .attributeContains(levelListContainer, "class", "hide")));

        driver.findElement(By.cssSelector(String.format("[title*='%s']", englishLevel.getName()))).click();
    }

    public void selectGender(String option) {
        WebElement selectGenderElement = driver.findElement(By.id("id_gender"));
        selectGenderElement.click();

        Select select = new Select(selectGenderElement);
        select.selectByVisibleText(option);
    }

    public void switchRelocate(boolean isSelected) {
        String relocate = isSelected ? "Да" : "Нет";
        driver.findElement(By.xpath(String.format("//span[@class=\"radio__label\" and text()=\"%s\"]", relocate))).click();
    }

    public void switchWorkFormat(boolean isSelected, WorkGrafData... workGrafs) {
        for (WorkGrafData workGraf : workGrafs) {
            String selector = "input[title='%s']";

            WebElement inputSelect = driver.findElement(By.cssSelector(String.format(selector, workGraf.getName())));


            waitTools.waitForCondition(ExpectedConditions
                    .elementToBeClickable(By.cssSelector(String.format(selector, workGraf.getName()))));

            if (inputSelect.isSelected() != isSelected) {
                inputSelect.click();
            }
        }
    }


    public void selectCommunicationMethod(InputFieldData inputFieldData, String data) {
        WebElement commMethodSelectElement = driver.findElement(By
                .xpath("//span[@class=\"placeholder\" and text()=\"Способ связи\"]"));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(commMethodSelectElement));
        commMethodSelectElement.click();

        WebElement commMethodContainer = driver.findElement(By
                .xpath("//div[@class='lk-cv-block__select-options " +
                        "lk-cv-block__select-options_left js-custom-select-options-container']"));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(commMethodContainer));

        driver.findElement(By.cssSelector(String.format("[title*='%s']", inputFieldData.getName()))).click();

        WebElement inputData = driver.findElement(By.id("id_contact-0-value"));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(inputData));
        inputData.click();
        inputData.sendKeys(data);
    }

    public void clickSavePersonalData() {
        WebElement saveData = driver.findElement(By.cssSelector("button[name='continue']"));
        waitTools.waitForCondition(ExpectedConditions.elementToBeClickable(saveData));
        saveData.click();

    }

    public void assertFieldsDataName(String inputName, String fakerNames) {
        String fakerValue = driver.findElement
                (By.cssSelector(String.format("input[name='%s']", inputName))).getAttribute("value");

        Assertions.assertEquals(fakerValue, fakerNames);
    }

    public void assertFieldsData() {
        Assertions.assertEquals(countries, driver.findElement(By.cssSelector(".js-lk-cv-dependent-master > label:nth-child(1) > div:nth-child(2)")).getText());
        Assertions.assertEquals(city, driver.findElement(By.cssSelector(".js-lk-cv-dependent-slave-city > label:nth-child(1) > div:nth-child(2)")).getText());
        Assertions.assertEquals(english, driver.findElement(By.xpath("//input[@name='english_level']/ancestor:: div[contains(@class, 'js-lk-cv-custom-select')]")).getText());
    }
}