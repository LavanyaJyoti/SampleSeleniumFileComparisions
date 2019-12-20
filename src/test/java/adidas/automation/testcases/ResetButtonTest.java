package adidas.automation.testcases;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.SystemUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import adidas.automation.basepage.BasePage;
import adidas.automation.pages.FileDifferencePage;
import adidas.automation.reporting.ExtentReportTestManager;
import adidas.automation.testbase.TestBase;
import adidas.automation.utils.SeleniumUtil;

public class ResetButtonTest extends TestBase {
	private BasePage basePage;

	private FileDifferencePage fileDifferencePage;
	
	ExtentTest test;
	
	@BeforeClass
	public void setUp() {
		test = ExtentReportTestManager.startTest("ResetButtonTest", "Reset Button Test");
		basePage = new BasePage(getDriver());
		test.log(Status.INFO, "Entering the URL");
		basePage.navigateToUrl(System.getProperty("applicationUrl"));
	}

	@Test(priority = 0, dataProvider = "FileDifference")
	public void resetButtonTest(Map<String, String> map) {
		test.log(Status.INFO, "Starting resetButtonTest");
		fileDifferencePage = new FileDifferencePage(getDriver());
		fileDifferencePage.uploadFile1(map.get("file1"));
		fileDifferencePage.clickReset1Button();
        Assert.assertNotSame(
        		fileDifferencePage.getTextArea1ROValue(), "",
                "Reset button is not working");
        
        fileDifferencePage.uploadFile2(map.get("file2"));
		fileDifferencePage.clickReset2Button();
        Assert.assertNotSame(
        		fileDifferencePage.getTextArea2ROValue(), "",
                "Reset button is not working");
	}

	@DataProvider(name = "FileDifference")
	public Object[][] getDetails() {
		return SeleniumUtil
				.getDataFromJSON(
						SystemUtils.getUserDir() + File.separator + "src" + File.separator + "test" + File.separator
								+ "resources" + File.separator + "data" + File.separator + "FileDifference.json",
						"file");
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		getDriver().quit();
	}
}
