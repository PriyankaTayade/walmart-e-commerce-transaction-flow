package resources.selenium.helper;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
* 
* @author priyanka
*
*/
public class Driver {

	private WebDriver _driver;
	private final String _CROME_DRIVER_WIN32_PATH="src/test/java/resources/selenium/webdriver/chromedriver_win32/chromedriver.exe";
	//private final String _CROME_DRIVER_LINUX32_PATH="src/test/java/resources/selenium/webdriver/chromedriver_linux32/chromedriver";
	
	/**
	* Constructor to initialize the driver setting as per the host operating system
	*/
	public Driver(){

		if(SystemUtils.IS_OS_WINDOWS)
		{
		
			System.setProperty("webdriver.chrome.driver", _CROME_DRIVER_WIN32_PATH);
			_driver = new ChromeDriver();
		
		}
		else 
			_driver=new FirefoxDriver();
	}
	
	/**
	* Returns Web driver instance for the host os.
	* @return The function is configured to return cromedriver for windows and linux
	*/
	public WebDriver getDriver(){
		return _driver;
	}
}
