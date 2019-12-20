package adidas.automation.reporting;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.SystemUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	

	private ExtentReportManager() {

	}

	private static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null)
			createReportInstance();
		return extent;
	}

	private static ExtentReports createReportInstance() {
		DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String fileName = sdf.format(date).replaceAll("/", "_").replaceAll(":", "_").replaceAll(" ", "_");



		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
				SystemUtils.getUserDir() + File.separator + "target" + File.separator + "extent-reports"
						+ File.separator + fileName+".html");

		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("Automation Analysis");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("Adidas Automation");

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("User Home", SystemUtils.getUserHome().toString());
		extent.setSystemInfo("UserName", "Jeeshan");

		return extent;

	}
}
