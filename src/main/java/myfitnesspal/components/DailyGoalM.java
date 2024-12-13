package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class DailyGoalM extends SelectOptionM{

    @FindBy(xpath = "//h1[contains(@class, 'MuiTypography-h1')]")
    ExtendedWebElement congratulationText;

    @FindBy(xpath = "//input[@name=\"enewsletter\"]")
    ExtendedWebElement newsCheck;

    @FindBy(xpath = "//span[text()=\"Explore MyFitnessPal\"]/..")
    ExtendedWebElement exploreBtn;


    public DailyGoalM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }


    public String getCongratulationsText(){
        return congratulationText.getText();
    }

    public void clickNewsCheckBox(){
        newsCheck.click();
    }

    public void clickExploreBtn(){
        exploreBtn.click();
    }
}
