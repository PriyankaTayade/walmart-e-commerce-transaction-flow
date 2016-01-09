package selenium.helper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class SeleniumHelper {

	public WebDriver _driver;
	WebDriverWait _wait;
	private final long _PAGE_LOAD_TIMEOUT=60;
	private final long _IMPLICIT_TIMEOUT=10;
	
	public SeleniumHelper(){
		_driver=new FirefoxDriver();
		// Set implicit time out of 10 seconds
		_driver.manage().window().maximize();
		_driver.manage().timeouts().implicitlyWait(_IMPLICIT_TIMEOUT, TimeUnit.SECONDS);
		_driver.manage().timeouts().pageLoadTimeout(_PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		_wait=new WebDriverWait(_driver, 60);

	}
	
	/*
	 * @description find a web element by its Css Class Name
	 * @Parameter elementID
	 * @return  WebElement 
	 */
	public void navigateUsingURL(String url){
		_driver.navigate().to(url);
	}
	
	/*
	 * @description find a web element by its Css Class Name
	 * @Parameter elementID
	 * @return  void 
	 */
	public void _waitForElement() throws InterruptedException{
		
	}
	
	public void waitForPageLoad(){
		
        ExpectedCondition<Boolean> pageload = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
              return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
          };

          WebDriverWait waitForPageLoad = new WebDriverWait(_driver,30);
          try {
        	  waitForPageLoad.until(pageload);
          } catch(Throwable error) {
                  Assert.assertFalse("Timeout waiting for Page Load Request to complete.", false);
          }
	}
	
	public String getCurrentUrl(){
		return _driver.getCurrentUrl();
	}
	
	/*
	 * @description find a web element by its id
	 * @Parameter elementID
	 * @return  WebElement
	 */
	public WebElement findElementByID(String elementID){
		try {
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.id(elementID))));
		}
		catch(Exception e){
			//e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * @description find a web element by its Css Class Name
	 * @Parameter elementID
	 * @return  WebElement
	 */
	public WebElement findElementByClassName(String ClassName){
		try{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.className(ClassName))));
		}
		catch(Exception e){
			//e.printStackTrace();
			return null;
		}
		//return driver.findElement(By.className(ClassName));
	} 
	
	public WebElement findElementByClassSelector(String ClassName){
		try{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.cssSelector(ClassName))));
		}
		catch(Exception e){
			//e.printStackTrace();
			return null;
		}
		//return driver.findElement(By.className(ClassName));
	} 
	public WebElement findElementByXPath(String path){
		try{
			return _wait.until(ExpectedConditions.visibilityOf(_driver.findElement(By.xpath(path))));
		}
		catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * @description find a web element by its Css Class Name
	 * @Parameter elementID
	 * @return  WebElement 
	 */
	public String getAttribute(WebElement element, String attr){
		return element.getAttribute(attr);
	}
	
	
	/*
	 * @description find a web element by its Css Class Name
	 * @Parameter elementID
	 * @return  WebElement 
	 */
	public void addDataToTextField(WebElement element, String Data) throws InterruptedException{
		//int attempt=1;
		//while(attempt<3){
			try{
				element.sendKeys(Data);
				//return;
			}
			catch(Exception e){
				//attempt++;
				e.printStackTrace();
				//_waitForElement();
			}
		//}
		//return;
	}
	
	/*
	 * @description find a web element by its Css Class Name
	 * @Parameter elementID
	 * @return  WebElement 
	 */
	public void clickElement(WebElement element) throws InterruptedException{
		//int attempt=1;
		//while(attempt<3){
			try{
				_wait.until(ExpectedConditions.elementToBeClickable(element)).click();
				//return;
			}
			catch(Exception e){
				//attempt++;
				e.printStackTrace();
				//_waitForElement();
			}
		//}
		//return;
	}

	public boolean getText(WebElement element, String text) {
		// TODO Auto-generated method stub
		try{
			return _wait.until(ExpectedConditions.textToBePresentInElement(element, text));
			//return;
		}
		catch(Exception e){
			//attempt++;
			e.printStackTrace();
			//_waitForElement();
		}
		return false;
	}

	public List<WebElement> findElementsByXPath(String path) {
		// TODO Auto-generated method stub
		return _driver.findElements(By.xpath(path));
	}
	
	public String getCookieNamed(String name){
		try{
			return _driver.manage().getCookieNamed("cart-item-count").getValue();
			//return;
		}
		catch(Exception e){
			//attempt++;
			e.printStackTrace();
			//_waitForElement();
		}
		return "";
	}

	public List<WebElement> findElementsByClassName(String className) {
		// TODO Auto-generated method stub
		
		return _driver.findElements(By.className(className));
	}

}
