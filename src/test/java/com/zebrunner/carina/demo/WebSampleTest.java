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
import myfitnesspal.objects.MFPUser;
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
import web.gui.BBCPage;
import web.gui.InventoryPage;
import web.gui.LoginPage;
import web.gui.MercadoLibrePage;
import web.objects.User;

/**
 * This sample shows how create Web test.
 *
 * @author qpsdemo
 */
public class WebSampleTest implements IAbstractTest {


    @Test
    @MethodOwner(owner = "bpruzsiani")
    @TestPriority(Priority.P1)
    @TestLabel(name = "feature", value = {"web", "regression"})
    public void testLogin() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs", "Page did not open correctly");

        User user = new User("standard_user", "secret_sauce");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(user.getUsername(), user.getPassword());

        String currentUrl = getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("inventory.html"), "Login failed");

        InventoryPage inventoryPage = new InventoryPage(getDriver());
        inventoryPage.logout();


        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Logout failed, login button not found");
    }

    @Test
    @MethodOwner(owner = "bpruzsiani")
    @TestPriority(Priority.P2)
    @TestLabel(name = "feature", value = {"web", "regression"})
    public void testLoginWithInvalidData() {
        getDriver().get("https://www.saucedemo.com/");
        Assert.assertEquals(getDriver().getTitle(), "Swag Labs", "Page did not open correctly");

        User invalidUser = new User("invalid_user", "wrong_password");

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(invalidUser.getUsername(), invalidUser.getPassword());

        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Did not show error message");
        Assert.assertTrue(loginPage.getErrorMessageText().contains("Username and password do not match"), "Error message is not correct");
    }


    @Test
    @MethodOwner(owner = "bpruzsiani")
    @TestPriority(Priority.P3)
    @TestLabel(name = "feature", value = {"web", "regression"})
    public void testPageScroll() {
        WebDriver driver = getDriver();
        BBCPage bbcPage = new BBCPage(driver);

        bbcPage.openPage();

        Assert.assertTrue(bbcPage.isTitleCorrect(), "Page did not load successfully");

        bbcPage.scrollPage(0, 500);
        WebElement loadedContent = bbcPage.waitForContentToLoad();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(bbcPage.isContentDisplayed(loadedContent), "Content did not load correctly after scrolling");

        bbcPage.scrollPage(0, 1000);
        WebElement moreContent = bbcPage.waitForContentToLoad();
        softAssert.assertTrue(bbcPage.isContentDisplayed(moreContent), "Content did not load correctly after further scrolling");

        softAssert.assertAll();
    }

    @Test(description = "Validating search input field redirection in Mercado Libre")
    public void searchInputTest() {
        MercadoLibrePage mercadoLibrePage = new MercadoLibrePage(getDriver());

        mercadoLibrePage.openPage("https://listado.mercadolibre.com.ar/");

        mercadoLibrePage.acceptCookies();

        mercadoLibrePage.searchFor("laptops");

        Assert.assertTrue(mercadoLibrePage.isResultsHeaderDisplayed(), "Results header is not displayed.");
        String resultsText = mercadoLibrePage.getResultsHeaderText();
        Assert.assertTrue(resultsText.toLowerCase().contains("resultados"), "Result header text does not contain 'resultados'.");

        List<WebElement> productTitles = mercadoLibrePage.getProductTitles();
        SoftAssert softAssert = new SoftAssert();
        for (WebElement productTitle : productTitles) {
            softAssert.assertTrue(productTitle.getText().toLowerCase().contains("laptop"), "Product title does not contain 'laptop': " + productTitle.getText());
        }

        softAssert.assertAll();
    }

    @Test(description = "Validate categories navigation and selection in Mercado Libre")
    public void testCategoriesAndSelectMultiple() {
        MercadoLibrePage mercadoLibrePage = new MercadoLibrePage(getDriver());

        mercadoLibrePage.openPage("https://www.mercadolibre.com.ar/");

        mercadoLibrePage.clickCategories();

        String[] categories = {"Inmuebles", "Supermercado", "Herramientas"};

        SoftAssert softAssert = new SoftAssert();

        for (String category : categories) {
            mercadoLibrePage.clickCategory(category);

            String currentUrl = mercadoLibrePage.getCurrentUrl();
            softAssert.assertTrue(currentUrl.toLowerCase().contains(category.toLowerCase()), "Did not redirect correctly to the category: " + category);

            mercadoLibrePage.navigateBack();
            mercadoLibrePage.clickCategories();
        }

        softAssert.assertAll();
    }

    @Test
    public void dailySearch() {

        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            driver.get("https://www.bing.com");


            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_s")));
            signInButton.click();

            WebElement personalAccountSignInButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.b_toggle.b_imi[data-a='false'][data-p='W']")));
            personalAccountSignInButton.click();


            WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("loginfmt")));
            emailField.sendKeys("");

            WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            nextButton.click();

            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.name("passwd")));
            passwordField.sendKeys("");

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
            loginButton.click();
            WebElement noButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("declineButton")));
            noButton.click();

            String[] searchKeywords = {"Carina Framework", "Automated Testing", "Selenium WebDriver", "Weather in my city", "President of the USA", "Movies at the cinema", "Football matches today", "Top 5 basketball players of all time"};

            for (String keyword : searchKeywords) {
                WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
                searchBox.clear();
                searchBox.sendKeys(keyword);
                searchBox.submit();

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".b_algo")));

                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("window.scrollBy(0, 500)", "");
                Thread.sleep(5000);

                driver.get("https://www.bing.com");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    //WebDriver driver; // Asumiendo que tienes el WebDriver inicializado correctamente
   // MFPUser user = new MFPUser("John Doe", "01/01/1990", 180, 75, "johndoe@example.com", "password123");

//    @Test
//    public void SignIn() {
//      //  driver.get("https://www.myfitnesspal.com/es/account/login");
//
//        waitForElementVisible(By.xpath("//input[@name='name' and @placeholder='Nombre']"));
//
//
//        WebElement nameField = driver.findElement(By.xpath("//input[@name='name' and @placeholder='Nombre']"));
//        nameField.sendKeys(user.getName());
//
//        // Hacemos clic en el botón "Siguiente"
//        clickNextButton();
//
//        // Hacemos clic en el botón "Perder peso"
//        WebElement loseWeightButton = driver.findElement(By.xpath("//button[@value='lose_weight' and .//p[text()='Perder peso']]"));
//        loseWeightButton.click();
//
//        // Hacemos clic en el botón "Siguiente"
//        clickNextButton();
//
//        // Continuamos con el flujo de "Siguiente"
//        for (int i = 0; i < 5; i++) {
//            clickNextButton();
//        }
//
//        // Seleccionamos el sexo masculino
//        WebElement maleRadioButton = driver.findElement(By.xpath("//input[@name='sex' and @value='M']"));
//        maleRadioButton.click();
//
//        // Ingresamos la fecha de nacimiento
//        WebElement birthDateField = driver.findElement(By.id("cumpleaños"));
//        birthDateField.sendKeys(user.getBirthDate());
//
//        // Hacemos clic en el botón "Siguiente"
//        clickNextButton();
//
//        // Ingresamos la altura del usuario
//        WebElement heightFeetField = driver.findElement(By.id("Altura (pies)"));
//        heightFeetField.sendKeys(String.valueOf(user.getHeightFeet()));
//
//        // Ingresamos el peso actual del usuario
//        WebElement weightField = driver.findElement(By.id("Peso actual"));
//        weightField.sendKeys(String.valueOf(user.getWeight()));
//
//        // Calculamos el peso deseado y lo ingresamos
//        WebElement desiredWeightField = driver.findElement(By.id("Peso deseado"));
//        desiredWeightField.sendKeys(String.valueOf(user.getWeight() + 5));
//
//        // Hacemos clic en el botón "Siguiente" para completar el registro
//        clickNextButton();
//    }
//
//    // Función para esperar que un elemento esté visible
//    private void waitForElementVisible(By locator) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//
//    // Función para hacer clic en el botón "Siguiente"
//    private void clickNextButton() {
//        WebElement nextButton = driver.findElement(By.xpath("//button[@class='MuiButtonBase-root MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeMedium MuiButton-containedSizeMedium MuiButton-fullWidth css-ufjate']"));
//        nextButton.click();
//    }


}
