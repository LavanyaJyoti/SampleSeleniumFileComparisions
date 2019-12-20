package adidas.automation.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentReportTestManager {
	private static ExtentReports extent = ExtentReportManager.getInstance();
	private static ThreadLocal<ExtentTest> tTest = new ThreadLocal<>();

	public static synchronized ExtentTest getTest() {
		return tTest.get();
	}

	public static synchronized void endTest() {
		extent.flush();
	}

	public static synchronized ExtentTest startTest(final String testName, final String testDescription) {
		ExtentTest test = extent.createTest(testName, testDescription);
		tTest.set(test);
		return tTest.get();
	}
}
