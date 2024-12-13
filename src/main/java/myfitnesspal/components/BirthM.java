package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class BirthM extends SelectOptionM{
    @FindBy(xpath = "//input[@value='%s']")
    ExtendedWebElement sexCheckBox;

    @FindBy(xpath = "//input[@id=\"birthday\"]")
    ExtendedWebElement birthday;
    public BirthM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public void selectSex() {
        sexCheckBox.format("M").click();
    }


    public void selectBirthday(String date) {
        birthday.type(date);
    }

    public String getBirthday() {
        return birthday.getText();
    }

}
