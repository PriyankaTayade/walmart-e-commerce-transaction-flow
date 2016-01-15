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

import org.openqa.selenium.JavascriptExecutor;

/**
* This class initialize the driver with the help of driver class, 
* and set parameters for the driver .
* SeleniumHelper class provides various selenium driver methods 
* 
* @author priyanka tayade
* @version 1.0
*/
public class SeleniumHelper {
	
	private WebDriver _driver;
	private WebDriverWait _wait;
	private final long _PAGE_LOAD_TIMEOUT=60;
	private final long _IMPLICIT_TIMEOUT=10;
	
	/**
	* Constructor to initialize the driver setting
	*/
	public SeleniumHelper(){
	
		_driver=new Driver().getDriver();
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
		try
		{
			_driver.navigate().to(url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ;
		}
	}
	
	/**
	 * This methods helps to navigate back in the browser
	 */
	public void navigateBack(){
		try
		{
			_driver.navigate().back();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ;
		}
	}
	
	/**
	* This methods waits for the page to load 
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
	* This method returns the current url of the browser
	* @return returns current url
	*/
	public String getCurrentUrl(){
		try
		{
			return _driver.getCurrentUrl();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	* This methods find the element using its id, it waits until the element is visible
	* @param elementID passes element id as a parameter to find the element
	* @return WebElement is return for a given id
	*/
	public WebElement findElementByID(String elementID){
		try 
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.id(elementID))));
		}
		catch(Exception e)
		{
			System.out.println("Expection occured to find element by element id "+elementID);
			//e.printStackTrace();
			return null;
		}
	}
	
	/**
	* This methods find the element using class name, it waits until the element is visible
	* @param ClassName passes elements class name as a parameter to find the element
	* @return WebElement is return for a given class name
	*/
	public WebElement findElementByClassName(String ClassName){
		try
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.className(ClassName))));
		}
		catch(Exception e)
		{
			System.out.println("Expection occured to find element by class name "+ClassName);			
			//e.printStackTrace();
			return null;
		}
	} 
	
	/**
	* This methods find the element using css selector, it waits until the element is visible
	* @param ClassSelector passes elements css selector as a parameter to find the element
	* @return WebElement is return for a given css selector
	*/
	public WebElement findElementByClassSelector(String ClassSelector){
		try
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.cssSelector(ClassSelector))));
		}
		catch(Exception e)
		{
			System.out.println("Expection occured to find element by class selector "+ClassSelector);
			//e.printStackTrace();
			return null;
		}
	} 
	
	/**
	* This methods find the element using xpath, it waits until the element is visible
	* @param xpath passes elements xpath as a parameter to find the element
	* @return WebElement is return for a given xpath
	*/
	public WebElement findElementByXPath(String xpath){
		try
		{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.xpath(xpath))));
		}
		catch(Exception e)
		{
			System.out.println("Expection occured to find element by xpath "+xpath);
			//e.printStackTrace();
			return null;
		}
	}
	
	/**
	* This methods returns a value of the attribute for a element
	* @param element  Passes web element who's attribute value has to be found
	* @param attr Passes element's attribute as a parameter
	* @return value of the attribute as string
	*/
	public String getAttribute(WebElement element, String attr){
		try
		{
			return element.getAttribute(attr);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	* This method adds input user data to the text field
	* @param element passes element as a parameter who's input value is to be set
	* @param Data data to be used as input for element
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
			 e.printStackTrace();
			//_waitForElement();

		}

	}
	
	/**
	* This methods helps to click a web element
	* @param element takes web element as parameter 
	*/
	public void clickElement(WebElement element) {

		try
		{
			_wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	
	/**
	* This method is used to match the text of the web element with a given text
	* @param element get element whose text is to be matched
	* @param text get text to be matched as parameter
	* @return return boolean true if the text matches or else returns false
	*/
	public boolean matchText(WebElement element, String text) {
		int attempt=0;
		while(attempt<5)
		{
			try
			{
				return _wait.until(ExpectedConditions.textToBePresentInElement(element, text));
			}
			catch(Exception e)
			{
				attempt++;
				e.printStackTrace();
				//_waitForElement();
				
				System.out.println("Expection occured to match the text "+text);
				
			}
		}
		return false;
	}
	
	/**
	* This class finds elements by xpaths
	* @param path takes xpath as parameter
	* @return returns list of web elements
	*/
	public List<WebElement> findElementsByXPath(String path) {
		try
		{
			return _driver.findElements(By.xpath(path));
		}
		catch(Exception e){
			System.out.println("Expection occured to find the elements by xpath "+path);
			return null;
		}
	}
	
	/**
	* This class gets the value of the cookie by its name
	* @param name get cookie name as parameter 
	* @return returns value of the cookie as string
	*/
	public String getCookieNamed(String name){
		try
		{
			return _driver.manage().getCookieNamed("cart-item-count").getValue();
		}
		catch(Exception e)
		{
			System.out.println("Expection occured to get cookie named "+name);
			//e.printStackTrace();
			return "";
		}	
	}
	
	
	/**
	* This method helps to find elements by class name
	* @param className ClassName is pass as parameter to find the list of elements
	* @return List of WebElement is returned
	*/
	public List<WebElement> findElementsByClassName(String className) {
		try
		{
			return _driver.findElements(By.className(className));
		}
		catch(Exception e){
			System.out.println("Expection occured to find the elements by class name "+className);
			return null;
		}
	}
	
	public void Close() {
		_driver.close();
	}
	
	
	
	/**
	 * This methods takes a element as returns the text value in it
	 * @param element takes WebElement as a parameter
	 * @return Return text of the element as String
	 */
	public String getText(WebElement element) {
		try
		{
			return element.getText();
		}
		catch(Exception e)
		{
			System.out.println("Expection occured to get text of the element"+element);
			//e.printStackTrace();
			return "";
		}
	}

}
