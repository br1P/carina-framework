package myfitnesspal.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class PhysicalM extends SelectOptionM{
    public PhysicalM(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @FindBy(xpath = "//input[@id=\"Height (feet)\"]")
    ExtendedWebElement enterHeightFeet;

    @FindBy(xpath = "//input[@id=\"Current weight\"]")
    ExtendedWebElement enterWeight;

    @FindBy(xpath = "//input[@id=\"Goal weight\"]")
    ExtendedWebElement enterIdealWeight;

    public void enterHeight(String heightFeet){
        enterHeightFeet.type(heightFeet);
    }



    public void enterWeight(String weight){
        enterWeight.type(weight);
    }

    public void enterIdealWeight(String idealWeight){
        try {
            int weight = Integer.parseInt(idealWeight);

            weight -= 5;

            enterIdealWeight.type(String.valueOf(weight));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid goalWeight value: " + idealWeight, e);
        }
    }
}
