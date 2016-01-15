package resources.selenium.helper;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
* This class helps setup selenium driver for a given operating system and works with chrome driver
* 
* @author priyanka tayade
* @version 1.0
*/
public class Driver {

	private WebDriver _driver;
	private final String _CHROME_DRIVER_WIN32_PATH="src/test/java/resources/selenium/webdriver/chromedriver_win32/chromedriver.exe";
	//private final String _CHROME_DRIVER_LINUX32_PATH="src/test/java/resources/selenium/webdriver/chromedriver_linux32/chromedriver";
	
	/**
	* Constructor to initialize the driver setting as per the host operating system
	*/
	//TODO setup driver for other operating system such as linux
	//TODO Extend to work with all the browsers only works with chrome.
	public Driver(){

		if(SystemUtils.IS_OS_WINDOWS)
		{
		
			System.setProperty("webdriver.chrome.driver", _CHROME_DRIVER_WIN32_PATH);
			_driver = new ChromeDriver();
		
		}
		else 
			_driver=new FirefoxDriver();
	}
	
	/**
	* Returns Web driver instance for the host os.
	* @return The function is configured to return driver
	*/
	public WebDriver getDriver(){
		return _driver;
	}
}
