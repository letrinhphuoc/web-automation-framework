package com.phuoclet.tests.flightreservation;

import com.aventstack.extentreports.Status;
import com.phuoclet.pages.flightreservation.*;
import com.phuoclet.reports.ExtentTestManager;
import com.phuoclet.tests.AbstractTest;
import com.phuoclet.tests.flightreservation.model.FlightReservationTestData;
import com.phuoclet.util.Config;
import com.phuoclet.util.Constants;
import com.phuoclet.util.JsonUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class FlightReservationTest extends AbstractTest {

    private FlightReservationTestData testData;

    @BeforeTest
    @Parameters("testDataPath")
    public void setParameters(String testDataPath){
        this.testData = JsonUtil.getTestData(testDataPath, FlightReservationTestData.class);
    }

    @Test
    public void userRegistrationTest(Method method){
        ExtentTestManager.startTest(method.getName(), "User Registration Test");

        RegistrationPage registrationPage = new RegistrationPage(driver);

        // Step 01: Navigate to 'Flight Reservation' URL
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Navigate to 'Flight Reservation' URL");
        registrationPage.goTo(Config.get(Constants.FLIGHT_RESERVATION_URL));
        Assert.assertTrue(registrationPage.isAt());

        // Step 02: Enter user details
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Enter user details");
        registrationPage.enterUserDetails(testData.firstName(), testData.lastName());

        // Step 03: Enter user credentials
        ExtentTestManager.getTest().log(Status.INFO, "Step 03: Enter user credentials");
        registrationPage.enterUserCredentials(testData.email(), testData.password());

        // Step 04: Enter address details
        ExtentTestManager.getTest().log(Status.INFO, "Step 04: Enter address details");
        registrationPage.enterAddress(testData.street(), testData.city(), testData.zip());

        // Step 05: Click on 'Register' button
        ExtentTestManager.getTest().log(Status.INFO, "Step 05: Click on 'Register' button");
        registrationPage.register();

        // Log test as passed
        ExtentTestManager.getTest().log(Status.PASS, "User Registration Test passed successfully");
    }

    @Test(dependsOnMethods = "userRegistrationTest")
    public void registrationConfirmationTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Registration Confirmation Test");

        // Step 01: Initialize RegistrationConfirmationPage
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Initialize RegistrationConfirmationPage");
        RegistrationConfirmationPage registrationConfirmationPage = new RegistrationConfirmationPage(driver);

        // Step 02: Verify if on Registration Confirmation Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Verify if on Registration Confirmation Page");
        Assert.assertTrue(registrationConfirmationPage.isAt());

        // Step 03: Verify First Name on Confirmation Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 03: Verify First Name on Confirmation Page");
        Assert.assertEquals(registrationConfirmationPage.getFirstName(), testData.firstName());
        registrationConfirmationPage.goToFlightsSearch();
    }

    @Test(dependsOnMethods = "registrationConfirmationTest")
    public void flightsSearchTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Flights Search Test");

        // Step 01: Initialize FlightsSearchPage
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Initialize FlightsSearchPage");
        FlightsSearchPage flightsSearchPage = new FlightsSearchPage(driver);

        // Step 02: Verify if on Flights Search Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Verify if on Flights Search Page");
        Assert.assertTrue(flightsSearchPage.isAt());

        // Step 03: Select Passengers
        ExtentTestManager.getTest().log(Status.INFO, "Step 03: Select Passengers");
        flightsSearchPage.selectPassengers(testData.passengersCount());

        // Step 04: Click on Search Flights button
        ExtentTestManager.getTest().log(Status.INFO, "Step 04: Click on Search Flights button");
        flightsSearchPage.searchFlights();
    }

    @Test(dependsOnMethods = "flightsSearchTest")
    public void flightsSelectionTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Flights Selection Test");

        // Step 01: Initialize FlightsSelectionPage
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Initialize FlightsSelectionPage");
        FlightsSelectionPage flightsSelectionPage = new FlightsSelectionPage(driver);

        // Step 02: Verify if on Flights Selection Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Verify if on Flights Selection Page");
        Assert.assertTrue(flightsSelectionPage.isAt());

        // Step 03: Select Flights
        ExtentTestManager.getTest().log(Status.INFO, "Step 03: Select Flights");
        flightsSelectionPage.selectFlights();

        // Step 04: Confirm Flights
        ExtentTestManager.getTest().log(Status.INFO, "Step 04: Confirm Flights");
        flightsSelectionPage.confirmFlights();
    }

    @Test(dependsOnMethods = "flightsSelectionTest")
    public void flightReservationConfirmationTest(Method method){
        ExtentTestManager.startTest(method.getName(), "Flight Reservation Confirmation Test");

        // Step 01: Initialize FlightConfirmationPage
        ExtentTestManager.getTest().log(Status.INFO, "Step 01: Initialize FlightConfirmationPage");
        FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);

        // Step 02: Verify if on Flight Reservation Confirmation Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 02: Verify if on Flight Reservation Confirmation Page");
        Assert.assertTrue(flightConfirmationPage.isAt());

        // Step 03: Verify the Price on Confirmation Page
        ExtentTestManager.getTest().log(Status.INFO, "Step 03: Verify the Price on Confirmation Page");
        Assert.assertEquals(flightConfirmationPage.getPrice(), testData.expectedPrice());
    }

}