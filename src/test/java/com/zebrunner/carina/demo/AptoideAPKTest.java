package com.zebrunner.carina.demo;

import android.aptoide.*;
import android.aptoide.StartPage;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import android.aptoide.PAGES;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.utils.mobile.IMobileUtils;
import org.testng.asserts.SoftAssert;

import java.time.Duration;


public class AptoideAPKTest implements IAbstractTest, IMobileUtils {

    WebDriver webDriver = getDriver();
    SoftAssert softAssert = new SoftAssert();
    private void waitForPageToLoad(BasePage page, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(timeoutSeconds));
        wait.until(driver -> page.isPageOpened(Duration.ofSeconds(timeoutSeconds)));
    }
    @Test
    public void checkBottomNavBar() {


        StartPage startPage = new StartPage(webDriver);

        startPage.assertPageOpened(6);

        HomePage homePage = startPage.skipStart();
        waitForPageToLoad(homePage, 6);
        softAssert.assertTrue(homePage.isPageOpened(6), "home never open.");


        EditorialPage editorialPage = homePage.navigateTo(PAGES.EDITORIAL);
        waitForPageToLoad(editorialPage, 6);
        softAssert.assertTrue(editorialPage.isPageOpened(5), "EDITORIAL page was not opened.");

        SearchPage searchPage = editorialPage.navigateTo(PAGES.SEARCH);
        softAssert.assertTrue(searchPage.isPageOpened(5), "SEARCH page was not opened.");

        StoresPage storesPage = searchPage.navigateTo(PAGES.STORES);
        softAssert.assertTrue(storesPage.isPageOpened(5), "STORES page was not opened.");

//        AppsPage appsPage = storesPage.navigateTo(PAGES.APPS); //apps section is not charging at all?
//        waitForPageToLoad(appsPage,6);
//        softAssert.assertTrue(appsPage.isPageOpened(5), "APPS page was not opened.");

        softAssert.assertAll();
    }



    @Test
    public void searchForApp() {
        String query = "League";


        StartPage startPage = new StartPage(webDriver);
        startPage.assertPageOpened(5);

        HomePage homePage = startPage.skipStart();
        homePage.assertPageOpened(5);

        SearchPage searchPage = homePage.navigateTo(PAGES.SEARCH);
        searchPage.assertPageOpened(5);

        ExtendedWebElement selectedAppEl = searchPage.searchForApp(query);
        Assert.assertNotNull(selectedAppEl, String.format("Anything match with the query: '%s'", query));

        String appName = SearchPage.getAppName(selectedAppEl);
        softAssert.assertTrue(appName.toLowerCase().contains(query.toLowerCase()), String.format("'%s' is not present in the app name: '%s'", query, appName));

        AppPage appPage = searchPage.openResult(selectedAppEl);
        Assert.assertTrue(appPage.isPageOpened(6), "App page is not opening...");

        Assert.assertEquals(appPage.appNameEl.getText(), appName, "App names did not match.");

        softAssert.assertAll("Search has failed:");
    }

    @Test
    public void checkHomePage() {
        StartPage startPage = new StartPage(webDriver);
        startPage.assertPageOpened(5);

        HomePage homePage = startPage.skipStart();
        homePage.assertPageOpened(5);

        softAssert.assertTrue(homePage.actionbarIcon.isVisible(Duration.ofMillis(2500)), "Action bar icon did not appeared...");
        softAssert.assertTrue(homePage.coinIcon.isVisible(Duration.ofMillis(2500)), "Coin Icon did not appeared...");
        softAssert.assertTrue(homePage.gamesBtn.isVisible(Duration.ofMillis(2500)), "Games button did not appeared...");
        softAssert.assertTrue(homePage.appsBtn.isVisible(Duration.ofMillis(2500)), "Apps button did not appeared...");
        softAssert.assertTrue(homePage.featuredAppList.isVisible(Duration.ofMillis(2500)), "Featured apps did not appeared...");
        softAssert.assertTrue(homePage.gotwSign.isVisible(Duration.ofMillis(2500)), "Game of the Week sign did not appeared...");

        softAssert.assertAll();
    }


    @Test
    public void checkStorePage() {


        StartPage startPage = new StartPage(webDriver);
        startPage.assertPageOpened(6);

        HomePage homePage = startPage.skipStart();
        homePage.assertPageOpened(6);

        StoresPage storesPage = homePage.navigateTo(PAGES.STORES);
        storesPage.assertPageOpened(6);


        softAssert.assertTrue(storesPage.moreSign.isVisible(Duration.ofMillis(2500)), "More sign did not appeared...");
        softAssert.assertTrue(storesPage.loginBtn.isVisible(Duration.ofMillis(2500)), "Login button did not appeared...");

        for (ExtendedWebElement header : storesPage.headerList) {
            softAssert.assertTrue(header.isVisible(Duration.ofMillis(2500)), "Header did not appeared...");
        }

        for (ExtendedWebElement recommendedStore : storesPage.recommendedStores) {
            softAssert.assertTrue(recommendedStore.isVisible(Duration.ofMillis(2500)), "Store did not appeared...");
        }

        softAssert.assertAll();
    }

    @Test
    public void checkAppsPage() {


        StartPage welcomePage = new StartPage(webDriver);
        welcomePage.assertPageOpened(6);
        HomePage homePage = welcomePage.skipStart();
        homePage.assertPageOpened(6);

        AppsPage appsPage = homePage.navigateTo(PAGES.APPS);
        appsPage.assertPageOpened(6);

        for (ExtendedWebElement header : appsPage.headerList) {
            softAssert.assertTrue(header.isVisible(Duration.ofMillis(2500)), "Header was not visible.");
        }

        softAssert.assertTrue(appsPage.aptoideAppTxt.isPresent(), "Aptoide app is not appearing.");

    }


}
