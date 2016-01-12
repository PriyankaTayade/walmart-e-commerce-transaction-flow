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
		
			System.out.println("It's a Windows OS");
			
			System.setProperty("webdriver.chrome.driver", _CROME_DRIVER_WIN32_PATH);
			_driver = new ChromeDriver();
		
		}
		else if(SystemUtils.IS_OS_LINUX)
		{
				
			System.out.println("This system has a LINUX OS, Not yet implemented for Linux");
			
			//default driver
			_driver=new FirefoxDriver();
		
		}
		else if(SystemUtils.IS_OS_MAC)
		{
		
			System.out.println("This system has a MAC OS, Not yet implemented for MAC");
			
			//default driver
			_driver=new FirefoxDriver();
		
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
