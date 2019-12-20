package adidas.automation.pages;

import static adidas.automation.pageconstants.FileDifferencePageConstants.CLEAR1_BUTTON;
import static adidas.automation.pageconstants.FileDifferencePageConstants.CLEAR2_BUTTON;
import static adidas.automation.pageconstants.FileDifferencePageConstants.DIFFERENCE_BUTTON;
import static adidas.automation.pageconstants.FileDifferencePageConstants.DIFFERENCE_TABLE;
import static adidas.automation.pageconstants.FileDifferencePageConstants.FILE1_INPUT;
import static adidas.automation.pageconstants.FileDifferencePageConstants.FILE2_INPUT;
import static adidas.automation.pageconstants.FileDifferencePageConstants.FILE_DISPLAY1_TEXTAREA;
import static adidas.automation.pageconstants.FileDifferencePageConstants.FILE_DISPLAY2_TEXTAREA;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import adidas.automation.utils.SeleniumUtil;

public class FileDifferencePage  {

	protected WebDriver driver;

	public FileDifferencePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		this.driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	}

	@FindBy(id = FILE_DISPLAY1_TEXTAREA)
	private WebElement txtFileDisplay1;

	@FindBy(id = FILE_DISPLAY2_TEXTAREA)
	private WebElement txtFileDisplay2;

	@FindBy(id = DIFFERENCE_BUTTON)
	private WebElement btnDifference;

	@FindBy(xpath = DIFFERENCE_TABLE)
	private WebElement tblDifference;

	@FindBy(id = FILE1_INPUT)
	private WebElement inFile1;

	@FindBy(id = FILE2_INPUT)
	private WebElement inFile2;

	@FindBy(id = CLEAR1_BUTTON)
	private WebElement btnClear1;

	@FindBy(id = CLEAR2_BUTTON)
	private WebElement btnClear2;

	public FileDifferencePage clickDifferenceButton() {

		SeleniumUtil.clickElement(btnDifference);
		return this;

	}

	public FileDifferencePage clickReset1Button() {

		SeleniumUtil.clickElement(btnClear1);
		return this;

	}

	public FileDifferencePage clickReset2Button() {

		SeleniumUtil.clickElement(btnClear2);
		return this;

	}

	public String getTextArea1ROValue() {

		return txtFileDisplay1.getText();

	}
	
	public FileDifferencePage enterDataInTextArea1(final String dataToEnter) {

		SeleniumUtil.enterDataIntoTextBox(txtFileDisplay1, dataToEnter);
		return this;

	}
	
	public FileDifferencePage enterDataInTextArea2(final String dataToEnter) {

		SeleniumUtil.enterDataIntoTextBox(txtFileDisplay2, dataToEnter);
		return this;

	}

	public String getTextArea2ROValue() {

		return txtFileDisplay1.getText();

	}

	public FileDifferencePage uploadFile1(final String fileName) {
		inFile1.sendKeys(fileName);
		return this;
	}

	public FileDifferencePage uploadFile2(final String fileName) {
		inFile2.sendKeys(fileName);
		return this;
	}

	public String getCSSDifferenceValue(final String data) {
		return SeleniumUtil.getCSSProperty(tblDifference.findElements(By.tagName("td")), data);
	}
	
	public String getCellData(final String data) {
		return SeleniumUtil.getData(tblDifference.findElements(By.tagName("td")),data);
	}

}
