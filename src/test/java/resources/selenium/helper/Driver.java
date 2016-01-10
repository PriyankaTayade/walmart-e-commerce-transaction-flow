package resources.selenium.helper;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {

	private WebDriver _driver;
	private final String _CROME_DRIVER_WIN32_PATH="src/test/java/resources/selenium/webdriver/chromedriver_win32/chromedriver.exe";
	private final String _CROME_DRIVER_LINUX32_PATH="src/test/java/resources/selenium/webdriver/chromedriver_linux32/chromedriver";
	
	public Driver(){
	
					
	    if(SystemUtils.IS_OS_WINDOWS){
	    	
	        System.out.println("It's a Windows OS");
	        
	        System.setProperty("webdriver.chrome.driver", _CROME_DRIVER_WIN32_PATH);
			_driver = new ChromeDriver();
	    }
	    if(SystemUtils.IS_OS_LINUX){
	    	
	        System.out.println("It's a Linux OS");
	        
	        System.setProperty("webdriver.chrome.driver", _CROME_DRIVER_LINUX32_PATH);
			_driver = new ChromeDriver();
	    }
	    if(SystemUtils.IS_OS_MAC){
	    	
	        System.out.println("This system has a MAC OS, Not yet implemented for MAC");
	        
	      //default driver
			_driver=new FirefoxDriver();
	    }
	}
	
	public WebDriver getDriver(){
		return _driver;
	}
}
