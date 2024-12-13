package android.aptoide;


import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class AppPage extends BasePage {
    @FindBy(id = "appview_install_button")
    ExtendedWebElement installBtn;

    @FindBy(id = "app_name")
    public ExtendedWebElement appNameEl;

    public AppPage(WebDriver driver) {
        super(driver, PAGES.SEARCH);
        setUiLoadedMarker(installBtn);
    }



}
