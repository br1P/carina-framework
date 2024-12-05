/*******************************************************************************
 * Copyright 2020-2023 Zebrunner Inc (https://www.zebrunner.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.zebrunner.carina.demo;

import java.time.Duration;
import java.util.List;

import com.zebrunner.carina.demo.gui.components.ModelItem;
import com.zebrunner.carina.demo.gui.components.NewsItem;
import com.zebrunner.carina.demo.gui.components.compare.ModelSpecs;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.zebrunner.carina.demo.gui.pages.common.AllBrandsPageBase;
import com.zebrunner.carina.demo.gui.pages.common.BrandModelsPageBase;
import com.zebrunner.carina.demo.gui.pages.common.CompareModelsPageBase;
import com.zebrunner.carina.demo.gui.pages.common.HomePageBase;
import com.zebrunner.carina.demo.gui.pages.common.ModelInfoPageBase;
import com.zebrunner.carina.demo.gui.pages.common.NewsPageBase;
import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.agent.core.annotation.TestLabel;
import com.zebrunner.carina.core.registrar.ownership.MethodOwner;
import com.zebrunner.carina.core.registrar.tag.Priority;
import com.zebrunner.carina.core.registrar.tag.TestPriority;

/**
 * This sample shows how create Web test.
 *
 * @author qpsdemo
 */
public class WebSampleTest implements IAbstractTest {

    @Test
    @MethodOwner(owner = "qpsdemo")
    @TestPriority(Priority.P3)
    @TestLabel(name = "feature", value = { "web", "regression" })
    public void testModelSpecs() {
        // Open GSM Arena home page and verify page is opened
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        // Select phone brand
        BrandModelsPageBase productsPage = homePage.selectBrand("Samsung");
        // Select phone model
        ModelInfoPageBase productInfoPage = productsPage.selectModel("Galaxy A04");
        // Verify phone specifications
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(productInfoPage.readDisplay(), "6.5\"", "Invalid display info!");
        softAssert.assertEquals(productInfoPage.readCamera(), "50MP", "Invalid camera info!");
        softAssert.assertEquals(productInfoPage.readRam(), "3-8GB RAM", "Invalid ram info!");
        softAssert.assertEquals(productInfoPage.readBattery(), "5000mAh", "Invalid battery info!");
        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "qpsdemo")
    @TestPriority(Priority.P1)
    @TestLabel(name = "feature", value = { "web", "acceptance" })
    public void testCompareModels() {
        // Open GSM Arena home page and verify page is opened
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
        // Open model compare page
        CompareModelsPageBase comparePage = homePage.openComparePage();
        // Compare 2 (for mobile testing) or 3 (for desktop testing) models
        List<ModelSpecs> specs = comparePage.compareModels("Samsung Galaxy J3", "Samsung Galaxy S23 Ultra", "Samsung Galaxy J7 Pro");
        // Verify model announced dates
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(specs.get(0).readSpec(ModelSpecs.SpecType.ANNOUNCED), "2016, March 31. Released 2016, May 06");
        softAssert.assertEquals(specs.get(0).readSpec(ModelSpecs.SpecType.TECHNOLOGY), "GSM / HSPA / LTE");
        softAssert.assertEquals(specs.get(1).readSpec(ModelSpecs.SpecType.ANNOUNCED), "2023, February 29");
        softAssert.assertEquals(specs.get(1).readSpec(ModelSpecs.SpecType.TECHNOLOGY), "GSM / CDMA / HSPA / EVDO / LTE / 5G");
        // for desktop could be compared 3 devices, when for mobile only 2
        if (specs.size() > 2) {
            softAssert.assertEquals(specs.get(2).readSpec(ModelSpecs.SpecType.ANNOUNCED), "2017, June");
            softAssert.assertEquals(specs.get(2).readSpec(ModelSpecs.SpecType.TECHNOLOGY), "GSM / HSPA / LTE");
        }

        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "qpsdemo")
    @TestLabel(name = "feature", value = { "web", "acceptance" })
    public void testNewsSearch() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");

        NewsPageBase newsPage = homePage.getFooterMenu().openNewsPage();
        Assert.assertTrue(newsPage.isPageOpened(), "News page is not opened!");

        final String searchQ = "iphone";
        List<NewsItem> news = newsPage.searchNews(searchQ);
        Assert.assertFalse(CollectionUtils.isEmpty(news), "News not found!");
        SoftAssert softAssert = new SoftAssert();
        for (NewsItem n : news) {
            System.out.println(n.readTitle());
            softAssert.assertTrue(StringUtils.containsIgnoreCase(n.readTitle(), searchQ),
                    "Invalid search results for " + n.readTitle());
        }
        softAssert.assertAll();
    }

    @Test()
    @MethodOwner(owner = "qpsdemo")
    @TestPriority(Priority.P3)
    @TestLabel(name = "feature", value = { "web", "regression" })
    public void testBrandGroup() {
        HomePageBase homePage = initPage(getDriver(), HomePageBase.class);
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");

        AllBrandsPageBase allBrandsPage = homePage.openAllBrandsPage();
        Assert.assertTrue(allBrandsPage.isPageOpened(), "All mobile phone brands page is not opened!");

        final String brandName = "Lava";
        BrandModelsPageBase brandModelsPage = allBrandsPage.selectBrand(brandName);
        List<ModelItem> models = brandModelsPage.getModels();
        SoftAssert softAssert = new SoftAssert();
        for (ModelItem modelItem : models) {
            softAssert.assertFalse(modelItem.readModel().contains(brandName),
                    "Model " + modelItem.readModel() + " should not include brand " + brandName + " in title");
        }

        softAssert.assertAll();
    }

    @Test
    @MethodOwner(owner = "bpruzsiani")
    @TestPriority(Priority.P1)
    @TestLabel(name = "feature", value = { "web", "regression" })
    public void testLogin() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs", "Page did not open correctly");


        WebElement usernameField = getDriver().findElement(By.id("user-name"));
        WebElement passwordField = getDriver().findElement(By.id("password"));
        WebElement loginButton = getDriver().findElement(By.id("login-button"));


        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click(); //login


        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "login failed");


        WebElement menuButton = getDriver().findElement(By.id("react-burger-menu-btn"));
        menuButton.click();
        WebElement logoutLink = getDriver().findElement(By.id("logout_sidebar_link"));
        logoutLink.click(); //we log out


        Assert.assertTrue(getDriver().findElement(By.id("login-button")).isDisplayed(), "login failed, didnt find button");
    }

    @Test
    @MethodOwner(owner = "bpruzsiani")
    @TestPriority(Priority.P2)
    @TestLabel(name = "feature", value = { "web", "regression" })
    public void testLoginWithInvalidData() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs", "page did not open correctly");

        WebElement usernameField = getDriver().findElement(By.id("user-name"));
        WebElement passwordField = getDriver().findElement(By.id("password"));
        WebElement loginButton = getDriver().findElement(By.id("login-button"));


        usernameField.sendKeys("invalid_user");
        passwordField.sendKeys("wrong_password");
        loginButton.click();


        WebElement errorMessage = getDriver().findElement(By.cssSelector(".error-message-container"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Did not show error message");
        Assert.assertTrue(errorMessage.getText().contains("Username and password do not match"),
                "error message is not correct");
    }

    @Test
    @MethodOwner(owner = "bpruzsiani")
    @TestPriority(Priority.P3)
    @TestLabel(name = "feature", value = { "web", "regression" })
    public void testPageScroll() {
        WebDriver driver = getDriver();
        driver.get("https://www.mercadolibre.com.ar/");


        Assert.assertTrue(driver.getTitle().contains("Mercado Libre"), "Page did not load succesfully");


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)", "");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loadedContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("article")));


        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(loadedContent.isDisplayed(), "Content did not load correctly after scrolling");


        js.executeScript("window.scrollBy(0, 1000)", "");


        WebElement moreContent = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("article")));
        softAssert.assertTrue(moreContent.isDisplayed(), "Content did not load correctly after scrolling");

        softAssert.assertAll();
    }

    @Test(description = "Validating search input field redirection in Mercado Libre")
    public void searchInputTest() {
        WebDriver driver = getDriver();


        driver.get("https://listado.mercadolibre.com.ar/");

        try {
            WebElement cookiesButton = driver.findElement(By.id("accept-cookies"));
            if (cookiesButton.isDisplayed()) {
                cookiesButton.click();
            }
        } catch (NoSuchElementException e) {
            System.out.println("No cookies acceptance button found.");
        }


        WebElement searchField = driver.findElement(By.name("as_word"));
        searchField.sendKeys("laptops");
        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'nav-search-btn')]"));
        searchButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui-search-search-result']/span[contains(@class, 'ui-search-search-result__quantity-results')]")));

        String resultsText = driver.findElement(By.xpath("//div[@class='ui-search-search-result']/span[contains(@class, 'ui-search-search-result__quantity-results')]")).getText();
        Assert.assertTrue(resultsText.contains("resultados"), "Result headers not found.");

        List<WebElement> productTitles = driver.findElements(By.xpath("//span[@class='ui-search-item-title']"));
        SoftAssert softAssert = new SoftAssert();
        for (WebElement productTitle : productTitles) {
            softAssert.assertTrue(productTitle.getText().toLowerCase().contains("laptop"),
                    "product is not 'laptop': " + productTitle.getText());
        }
        softAssert.assertAll();
    }

    @Test
    public void testCategoriesAndSelectMultiple() {

        WebDriver driver = getDriver();
        driver.get("https://www.mercadolibre.com.ar/");


        By categoriesLink = By.xpath("//a[@class='nav-menu-categories-link' and text()='Categorías']");


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement categoriesButton = wait.until(ExpectedConditions.elementToBeClickable(categoriesLink));

        categoriesButton.click();


        String[] categories = {"Inmuebles", "Supermercado", "Herramientas"};

        for (String category : categories) {

            By categoryLink = By.xpath("//a[contains(text(), '" + category + "')]");


            WebElement categoryButton = wait.until(ExpectedConditions.elementToBeClickable(categoryLink));


            categoryButton.click();


            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.toLowerCase().contains(category.toLowerCase()), //maybe softassert here ?
                    "Did not redirect correctly to the category: " + category);


            driver.navigate().back();
            categoriesButton = wait.until(ExpectedConditions.elementToBeClickable(categoriesLink));
            categoriesButton.click();
        }
    }

    @Test
    public void dailySearch() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Paso 1: Navega a Bing e inicia sesión
            driver.get("https://www.bing.com");

            // Localiza y hace clic en el botón "Iniciar sesión"
            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_s")));
            signInButton.click();

            // Selecciona "Iniciar sesión con una cuenta personal"
            WebElement personalAccountSignInButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a.b_toggle.b_imi[data-a='false'][data-p='W']")
            ));
            personalAccountSignInButton.click();

            // Ingresa correo electrónico
            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("loginfmt")));
            emailField.sendKeys("brunopruzsiani@hotmail.com"); // Reemplaza con tu correo

            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            nextButton.click();

            // Ingresa contraseña
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("passwd")));
            passwordField.sendKeys("budincapo"); // Reemplaza con tu contraseña

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            loginButton.click();

            // Selecciona "No" en "Mantener sesión iniciada"
            WebElement noButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("declineButton")));
            noButton.click();

            // Paso 2: Realiza las búsquedas después de iniciar sesión
            String[] searchKeywords = {
                    "Carina Framework", "Automated Testing", "Selenium WebDriver",
                    "Weather in my city", "President of the USA",
                    "Movies at the cinema", "Football matches today",
                    "Top 5 basketball players of all time"
            };

            for (String keyword : searchKeywords) {
                WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
                searchBox.clear();
                searchBox.sendKeys(keyword);
                searchBox.submit();

                // Espera resultados
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".b_algo")));

                // Pausa antes de la próxima búsqueda
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 500)", "");
                Thread.sleep(5000);

                // Vuelve a la página principal
                driver.get("https://www.bing.com");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }



}
