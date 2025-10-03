package com.testing;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SwagLabsTest {
    WebDriver driver;
    static ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void startReport() {
        extent = ExtentManager.getInstance();
    }

    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);
        driver = new RemoteWebDriver(new URL("http://localhost:4444/"), caps);
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void testValidLoginAndAddToCart() {
        test = extent.createTest("Valid Login & Add to Cart Test");
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.className("shopping_cart_link")).click();

        WebElement cartItem = driver.findElement(By.className("inventory_item_name"));
        Assert.assertEquals(cartItem.getText(), "Sauce Labs Backpack");

        test.log(Status.PASS, "Valid login and cart verification passed ✅");
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        test = extent.createTest("Invalid Login Test");
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
        driver.findElement(By.id("password")).sendKeys("wrong_password");
        driver.findElement(By.id("login-button")).click();

        WebElement errorMsg = driver.findElement(By.cssSelector("h3[data-test='error']"));
        Assert.assertTrue(errorMsg.getText().contains("Epic sadface"),
                "Expected error message not displayed!");

        test.log(Status.PASS, "Invalid login displayed error correctly ❌");
    }

    @AfterMethod
public void captureScreenshot(ITestResult result) {
    String testName = result.getMethod().getMethodName();

    if (result.getStatus() == ITestResult.FAILURE) {
        test.log(Status.FAIL, "FAILED: " + result.getThrowable());
        takeScreenshot(testName); // only for FAIL
    } else if (result.getStatus() == ITestResult.SUCCESS) {
        test.log(Status.PASS, "PASSED ✅");
    } else {
        test.log(Status.SKIP, "SKIPPED");
    }
}


    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterSuite
    public void endReport() {
        extent.flush();
    }

    private void takeScreenshot(String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/" + name + ".png");
            FileUtils.copyFile(src, dest);
            test.addScreenCaptureFromPath("screenshots/" + name + ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
