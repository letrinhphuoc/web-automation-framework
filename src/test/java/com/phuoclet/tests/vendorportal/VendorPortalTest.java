package com.phuoclet.tests.vendorportal;

import com.aventstack.extentreports.Status;
import com.phuoclet.reports.ExtentTestManager;
import com.phuoclet.tests.AbstractTest;
import com.phuoclet.pages.vendorportal.DashboardPage;
import com.phuoclet.pages.vendorportal.LoginPage;
import com.phuoclet.tests.vendorportal.model.VendorPortalTestData;
import com.phuoclet.util.Config;
import com.phuoclet.util.Constants;
import com.phuoclet.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class VendorPortalTest extends AbstractTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private VendorPortalTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setPageObjects(String testDataPath){
        this.loginPage = new LoginPage(driver);
        this.dashboardPage = new DashboardPage(driver);
        this.testData = JsonUtil.getTestData(testDataPath, VendorPortalTestData.class);
    }

    @Test
    public void loginTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Login Test");

        // Step 01: Navigate to Vendor Portal URL
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Navigate to Vendor Portal URL");
        loginPage.goTo(Config.get(Constants.VENDOR_PORTAL_URL));
        Assert.assertTrue(loginPage.isAt());

        // Step 02: Perform Login
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Perform Login");
        loginPage.login(testData.username(), testData.password());
    }

    @Test(dependsOnMethods = "loginTest")
    public void dashboardTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Dashboard Test");

        // Step 01: Verify if on Dashboard Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Verify if on Dashboard Page");
        Assert.assertTrue(dashboardPage.isAt());

        // finance metrics
        // Step 02: Verify Finance Metrics
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Verify Finance Metrics");
        Assert.assertEquals(dashboardPage.getMonthlyEarning(), testData.monthlyEarning());
        Assert.assertEquals(dashboardPage.getAnnualEarning(), testData.annualEarning());
        Assert.assertEquals(dashboardPage.getProfitMargin(), testData.profitMargin());
        Assert.assertEquals(dashboardPage.getAvailableInventory(), testData.availableInventory());

        // order history search
        // Step 03: Search Order History
        ExtentTestManager.getTest().log(Status.INFO, "Step 03: Search Order History");
        dashboardPage.searchOrderHistoryBy(testData.searchKeyword());

        // Step 04: Verify Search Results
        ExtentTestManager.getTest().log(Status.INFO, "Step 04: Verify Search Results");
        Assert.assertEquals(dashboardPage.getSearchResultsCount(), testData.searchResultsCount());
    }

    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Logout Test");

        // Step 01: Logout from Dashboard
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Logout from Dashboard");
        dashboardPage.logout();

        // Step 02: Verify if on Login Page after Logout
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Verify if on Login Page after Logout");
        Assert.assertTrue(loginPage.isAt());
    }
}
