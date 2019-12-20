package adidas.automation.testcases;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.common.util.concurrent.Uninterruptibles;

import adidas.automation.basepage.BasePage;
import adidas.automation.pages.FileDifferencePage;
import adidas.automation.reporting.ExtentReportTestManager;
import adidas.automation.testbase.TestBase;
import adidas.automation.utils.SeleniumUtil;

public class FileNotSupportedTest extends TestBase {
	private BasePage basePage;

	private FileDifferencePage fileDifferencePage;
	
	ExtentTest test;
	
	@BeforeClass
	public void setUp() {

		test=ExtentReportTestManager.startTest("FileNotSupportedTest", "File Not Supported Test");
		basePage = new BasePage(getDriver());
		test.log(Status.INFO, "Entering the URL");
		basePage.navigateToUrl(System.getProperty("applicationUrl"));
	}

	@Test(priority = 0, dataProvider = "FileDifference")
	public void fileNotSupportedTest(Map<String, String> map) {
		test.log(Status.INFO, "Starting fileNotSupportedTest");
		fileDifferencePage = new FileDifferencePage(getDriver());
		fileDifferencePage.uploadFile1(map.get("notSupportedFile"));
		fileDifferencePage.clickDifferenceButton();
		Assert.assertNotSame(fileDifferencePage.getTextArea1ROValue(), "File not supported!", "Message doesn't match");

		Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
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
