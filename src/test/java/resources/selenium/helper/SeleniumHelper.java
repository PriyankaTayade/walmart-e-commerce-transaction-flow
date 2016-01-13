package resources.selenium.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.host.Element;

import org.openqa.selenium.JavascriptExecutor;

/**
* 
* @author priyanka
*
*/
public class SeleniumHelper {
	
	public WebDriver _driver;
	WebDriverWait _wait;
	private final long _PAGE_LOAD_TIMEOUT=60;
	private final long _IMPLICIT_TIMEOUT=10;
	
	/**
	* Constructor to initialize the driver setting
	*/
	public SeleniumHelper(){
	
		//default driver
		_driver=new Driver().getDriver();
		// Set implicit time out of 10 seconds
		_driver.manage().window().maximize();
		_driver.manage().timeouts().implicitlyWait(_IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		_driver.manage().timeouts().pageLoadTimeout(_PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		_wait=new WebDriverWait(_driver, 60);
		
	}
	
	/**
	* Function to navigate to a url
	* @param url takes url to be navigated as string parameter
	*/
	public void navigateUsingURL(String url){
		_driver.navigate().to(url);
	}
	
	/**
	* 
	*/
	public void waitForPageLoad(){
	
		ExpectedCondition<Boolean> pageload = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
	
		WebDriverWait waitForPageLoad = new WebDriverWait(_driver,30);
		try 
		{
			waitForPageLoad.until(pageload);
		} 
		catch(Throwable error) 
		{
			Assert.assertFalse("Timeout waiting for Page Load Request to complete.", false);
		}
	}
	
	/**
	* 
	* @return
	*/
	public String getCurrentUrl(){
		return _driver.getCurrentUrl();
	}
	
	/**
	* 
	* @param elementID
	* @return
	*/
	public WebElement findElementByID(String elementID){
		try 
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.id(elementID))));
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}
	
	/**
	* 
	* @param ClassName
	* @return
	*/
	public WebElement findElementByClassName(String ClassName){
		try
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.className(ClassName))));
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	} 
	
	/**
	* 
	* @param ClassName
	* @return
	*/
	public WebElement findElementByClassSelector(String ClassName){
		try
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.cssSelector(ClassName))));
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	} 
	
	/**
	* 
	* @param path
	* @return
	*/
	public WebElement findElementByXPath(String path){
		try
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.xpath(path))));
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return null;
		}
	}
	
	/**
	* 
	* @param element
	* @param attr
	* @return
	*/
	public String getAttribute(WebElement element, String attr){
		return element.getAttribute(attr);
	}
	
	
	/**
	* 
	* @param element
	* @param Data
	* @throws InterruptedException
	*/
	public void addDataToTextField(WebElement element, String Data) {
		//int attempt=1;
		//while(attempt<3){
		try
		{
			element.sendKeys(Data);
			//return;
		}
		catch(Exception e)
		{
			//attempt++;
			//e.printStackTrace();
			//_waitForElement();
		}

	}
	
	/**
	* 
	* @param element
	* @throws InterruptedException
	*/
	public void clickElement(WebElement element) {

		try
		{
			_wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
	
	}
	
	/**
	* 
	* @param element
	* @param text
	* @return
	*/
	public boolean matchText(WebElement element, String text) {

		try
		{
			return _wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		}
		catch(Exception e)
		{
			//attempt++;
			//e.printStackTrace();
			//_waitForElement();
			return false;
		}
	}
	
	/**
	* 
	* @param path
	* @return
	*/
	public List<WebElement> findElementsByXPath(String path) {
		return _driver.findElements(By.xpath(path));
	}
	
	/**
	* 
	* @param name
	* @return
	*/
	public String getCookieNamed(String name){
		try
		{
			return _driver.manage().getCookieNamed("cart-item-count").getValue();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return "";
		}	
	}
	
	/**
	* 
	* @param className
	* @return
	*/
	public List<WebElement> findElementsByClassName(String className) {
		return _driver.findElements(By.className(className));
	}
	
	public void Close() {
		_driver.close();
	}

	public String getText(WebElement element) {
		try
		{
			return element.getText();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			return "";
		}
	}

}
