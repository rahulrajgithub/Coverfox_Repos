package CoverFoxTest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import CoverFoxBase.BaseClass;
import CoverFoxPom.CoverFoxAddressDetailsPage;
import CoverFoxPom.CoverFoxHealthPlanPage;
import CoverFoxPom.CoverFoxHomePage;
import CoverFoxPom.CoverFoxMemberDetailsPage;
import CoverFoxPom.CoverFoxResultsPage;
import CoverFoxUtility.UtilityTest;

public class TestClass extends BaseClass {

	CoverFoxHomePage homePage;
	CoverFoxHealthPlanPage healthPage;
	CoverFoxAddressDetailsPage addressPage;
	CoverFoxMemberDetailsPage memberDetails;
	CoverFoxResultsPage resultPage;
	String filePath;
	String destForScreenshot;

	public static Logger logger;

	@BeforeTest
	public void openBrowser()

	{
		logger = Logger.getLogger("4_CoverFox"); // logger4j
		PropertyConfigurator.configure("Log4j.properties");// configure loggerfile4j
		logger.info("launchBrowser");


		launchBrowser();
		homePage = new CoverFoxHomePage(driver);
		healthPage = new CoverFoxHealthPlanPage(driver);
		addressPage = new CoverFoxAddressDetailsPage(driver);
		memberDetails = new CoverFoxMemberDetailsPage(driver);
		resultPage = new CoverFoxResultsPage(driver);
		filePath = System.getProperty("user.dir") + "\\COVERFOX.xlsx";
		destForScreenshot = System.getProperty("user.dir") + "\\ScreenShotForMaven\\";

	}

	@BeforeClass
	public void preConditions() throws IOException, FileNotFoundException, InterruptedException

	{
		// Home-Page
		Thread.sleep(3000);
		homePage = new CoverFoxHomePage(driver);
		homePage.clickOnGetStarted();
		Reporter.log("Click on get started", true);
		logger.info("Click On Get Started");

		// Health-Plan Page
		Thread.sleep(4000);
		healthPage = new CoverFoxHealthPlanPage(driver);
		healthPage.clickOnNextBtn();
		Reporter.log("Click on next button", true);
		logger.info("Click on next button");

		// Member-details Page
		Thread.sleep(2000);
		memberDetails = new CoverFoxMemberDetailsPage(driver);
//		memberDetails.selectAgeDropDown(Utility.readDataFromPeroperties("age"));
		memberDetails.selectAgeDropDown(UtilityTest.getExcelData(filePath, "Sheet1", 1, 0));
		logger.warn("Enter age Between 18-90 years");
		logger.info("Handeling age Drop-Down");
		Reporter.log("Select age", true);
		memberDetails.clickOnNextBtn();
		Reporter.log("Click on next button", true);
		logger.info("Click on next button");
		Thread.sleep(2000);

		// Address-Details Page
		addressPage = new CoverFoxAddressDetailsPage(driver);
//		addressPage.enterPinCode(Utility.readDataFromPeroperties("pinCode"));
		addressPage.enterPinCode(UtilityTest.getExcelData(filePath, "Sheet1", 1, 1));
		logger.warn("Please Enter Valid Pincode");
		logger.info("Entering Pincode");
		Reporter.log("enter pincode", true);
//		addressPage.enterMobileNumber(Utility.readDataFromPeroperties("mobNo"));
		addressPage.enterMobileNumber(UtilityTest.getExcelData(filePath, "Sheet1", 1, 2));
		logger.warn("Please Enter Valid MobileNo");
		logger.info("Entering MobileNo");
		Reporter.log("enter mobileNo", true);
		addressPage.clickOnContinueBtn();
		Reporter.log("Click on Continue Btn", true);
		logger.info("Click on next button");
		Thread.sleep(2000);
	}

	@Test
	public void validateBanners() throws IOException

	{
		// Result-Page
		resultPage = new CoverFoxResultsPage(driver);
		logger.info("Validating Banners");
		Reporter.log("Validating the no of banners on result page", true);
		Assert.assertEquals(resultPage.getTotalBanners(), resultPage.getTextOnHomePage(),
				"No of Banners and text is not  matching");
		Reporter.log("Taking Screenshot", true);
		UtilityTest.getScreenShotForMaven(driver, destForScreenshot, "validateBanners");

	}

	@Test
	public void validatePresenceOfSortDropdown() throws IOException {
		// result-page
		resultPage = new CoverFoxResultsPage(driver);
		logger.info("Validating the Presence of Sort Button");
		Reporter.log("Validating the presence of sort dropdown on result page", true);
		Assert.assertTrue(resultPage.sortPlanDropdownIsDisplayed(),
				"Sort Plan Dropdown is not displayed , Tc is failed");
		Reporter.log("Taking Screenshot", true);
		UtilityTest.getScreenShotForMaven(driver, destForScreenshot, "PresenceOfSortDropDown");
	}

	@AfterClass
	public void closeTheBrowser() throws InterruptedException

	{
		logger.info("Close Browser");
		closeBrowser();
	}

}