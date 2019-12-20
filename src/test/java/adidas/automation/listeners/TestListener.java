package adidas.automation.listeners;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import adidas.automation.reporting.ExtentReportTestManager;
import adidas.automation.testbase.TestBase;

public class TestListener extends TestBase implements ITestListener {
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		System.out.println("I am in onStart method " + iTestContext.getName());

	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		System.out.println("I am in onFinish method " + iTestContext.getName());

		ExtentReportTestManager.endTest();

	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
		ExtentReportTestManager.getTest().pass(MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");

		
		Object testClass = iTestResult.getInstance();
		WebDriver webDriver = ((TestBase)testClass).getDriver();

		
		String base64Screenshot = "data:image/png;base64,"
				+ ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);

		// ExtentReports log and screenshot operations for failed tests.
		ExtentReportTestManager.getTest().addScreenCaptureFromBase64String(base64Screenshot);
		
		ExtentReportTestManager.getTest().fail(MarkupHelper.createLabel("Test failed", ExtentColor.RED));
	
		ExtentReportTestManager.getTest().fail(iTestResult.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
		// ExtentReports log operation for skipped tests.
		ExtentReportTestManager.getTest().skip( "Test Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
}
