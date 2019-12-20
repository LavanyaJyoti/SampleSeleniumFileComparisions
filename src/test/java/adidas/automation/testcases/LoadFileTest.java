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

public class LoadFileTest extends TestBase {
	private BasePage basePage;

	private FileDifferencePage fileDifferencePage;

	ExtentTest test;

	@BeforeClass
	public void setUp() {
		test = ExtentReportTestManager.startTest("LoadFileTest", "Load File Test");
		basePage = new BasePage(getDriver());
		test.log(Status.INFO, "Entering the URL");
		basePage.navigateToUrl(System.getProperty("applicationUrl"));
	}

	@Test(priority = 0, dataProvider = "FileDifference")
	public void loadFileTest(Map<String, String> map) {
		test.log(Status.INFO, "Starting loadFileTest");
		fileDifferencePage = new FileDifferencePage(getDriver());
		fileDifferencePage.uploadFile1(map.get("file1")).uploadFile2(map.get("file2"));
		Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
		fileDifferencePage.clickDifferenceButton();
		Assert.assertNotSame(fileDifferencePage.getTextArea1ROValue(), fileDifferencePage.getTextArea1ROValue(),
				"Text are different");

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
