package android.aptoide;

import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = AppsPage.class)
public class AppsPage extends BasePage {
    public AppsPage(WebDriver driver) {
        super(driver, PAGES.APPS);

        ExtendedWebElement denyButton = findExtendedWebElement(By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_deny_button\"]"));
        if (denyButton.isPresent()) {
            System.out.println("Permission dialog present, dismissing...");
            denyButton.click();
        } else {
            System.out.println("No permission dialog detected.");
        }

        setUiLoadedMarker(findExtendedWebElement(
                By.xpath("//android.view.ViewGroup[@resource-id=\"cm.aptoide.pt:id/fragment_apps_swipe_container\"]")
        ));
    }

    @FindBy(id = "apps_header_title")
    public List<ExtendedWebElement> headerList;

    @FindBy(xpath = "//android.widget.TextView[@resource-id=\"cm.aptoide.pt:id/apps_installed_app_name\" and @text=\"Aptoide\"]/../..")
    public ExtendedWebElement aptoideAppTxt;
}
