package android.aptoide;


import com.zebrunner.carina.utils.android.IAndroidUtils;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = SearchPage.class)
public class SearchPage extends BasePage implements IAndroidUtils {
    public SearchPage(WebDriver driver) {
        super(driver, PAGES.SEARCH);
        this.hideKeyboard();

        setUiLoadedMarker(trendingList);
    }

    @FindBy(id = "trending_list")
    ExtendedWebElement trendingList;

    @FindBy(id = "search_src_text")
    ExtendedWebElement searchLabel;

    @FindBy(id = "fragment_search_result_all_stores_app_list")
    ExtendedWebElement appResultList;

    public List<ExtendedWebElement> searchFor(String query, int limit) {
        this.searchLabel.click();
        this.searchLabel.type(query);
        pressKeyboardKey(AndroidKey.ENTER);

        waitUntil(ExpectedConditions.presenceOfElementLocated(appResultList.getBy()), Duration.ofSeconds(5));
        List<ExtendedWebElement> resultList = this.appResultList.findExtendedWebElements(
                By.xpath(".//android.view.ViewGroup")
        );

        if (limit > resultList.size())
            limit = resultList.size();

        return resultList.subList(0, limit);
    }

    public ExtendedWebElement searchForApp(String query) {
        this.searchLabel.click();
        this.searchLabel.type(query);
        pressKeyboardKey(AndroidKey.ENTER);

        waitUntil(ExpectedConditions.presenceOfElementLocated(appResultList.getBy()), Duration.ofSeconds(5));
        List<ExtendedWebElement> resultList = this.appResultList.findExtendedWebElements(
                By.xpath(".//android.view.ViewGroup")
        );

        return resultList.isEmpty() ? null : resultList.get(0);
    }

    public static String getAppName(ExtendedWebElement appResult) {
        return appResult.findExtendedWebElement(
                By.xpath("//android.widget.TextView[@resource-id=\"cm.aptoide.pt:id/app_name\"]")
        ).getText();
    }

    public AppPage openResult(ExtendedWebElement appResult) {
        appResult.click();

        return new AppPage(getDriver());
    }
}
