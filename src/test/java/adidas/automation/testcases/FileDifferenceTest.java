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

public class FileDifferenceTest extends TestBase {

	
	private BasePage basePage;

	private FileDifferencePage fileDifferencePage;

	private String firstText;

	private String secondText;
	
	ExtentTest test;

	@BeforeClass
	public void setUp() {
		
		test=ExtentReportTestManager.startTest("FileDifferenceTest", "File Difference Test");
		basePage = new BasePage(getDriver());
		test.log(Status.INFO, "Entering the URL");
		basePage.navigateToUrl(System.getProperty("applicationUrl"));
		
	}

	@Test(priority = 0, dataProvider = "FileDifference")
	public void compareTexts(Map<String, String> map) {
		test.log(Status.INFO, "Starting compareTexts");
		
		fileDifferencePage = new FileDifferencePage(getDriver());

		fileDifferencePage.enterDataInTextArea1(map.get("data1")).enterDataInTextArea2(map.get("data2"))
				.clickDifferenceButton();

		firstText = fileDifferencePage.getCellData(map.get("data1"));

		secondText = fileDifferencePage.getCellData(map.get("data2"));

		Assert.assertNotSame(firstText, secondText, "Text are different");

		Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);

	}

	@Test(priority = 1, dependsOnMethods = { "compareTexts" })
	public void comparingCSS() {
		test.log(Status.INFO, "Starting comparingCSS");
		String firstTextCss = fileDifferencePage.getCSSDifferenceValue(firstText);
		String secondTextCss = fileDifferencePage.getCSSDifferenceValue(secondText);
		Assert.assertEquals(firstTextCss, secondTextCss, "Properties didn't match");

		Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

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
