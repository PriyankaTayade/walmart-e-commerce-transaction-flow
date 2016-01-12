package test;

import org.testng.annotations.Test;

import resources.configuration.WalmartUI;
import resources.selenium.helper.SeleniumHelper;


import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;

public class TranscationFlowTest {
	
	SeleniumHelper tester;
	WalmartUI uiConfig;
	String savedItemID;
	
	/**
	 * 	Before test will initialize the driver and other required classes.
	 */
	@BeforeTest
	public void beforeTest() {
		
		tester=new SeleniumHelper();
		uiConfig=new WalmartUI();
	}
	
	/**
	 * This methods is used for returning user login to walmart login page
	 *  
	 * @param email provides user_name i.e email for user login
	 * @param password provides user password for user login
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dataProvider = "login")
	public void WalmartUserLogin(String email, String password) throws InterruptedException {
		
		//Navigate to walmart login page
		tester.navigateUsingURL(uiConfig.getURL_USER_LOGIN());	
		
		// wait for the login page to load
		tester.waitForPageLoad();
		
		//login as using previously registered user credentials.
		tester.addDataToTextField(tester.findElementByID("login-username"), email);
		tester.addDataToTextField(tester.findElementByID("login-password"), password);
		
		tester.clickElement(tester.findElementByClassName("login-sign-in-btn"));
		
		//wait for user to sign in
		tester.waitForPageLoad();
		
		//verify if user is successfully logged in
		assert(tester.findElementByClassName("recent-orders-heading")!=null);
		assert(tester.getText(tester.findElementByClassName("recent-orders-heading"), "Welcome to your Walmart account!"));		  
	}
	
	/**
	 * This methods checks if the cart is empty, if it is not empty then it will delete all the items from the cart
	 * 
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="WalmartUserLogin")
	public void EmptyCartIfNotEmpty() throws InterruptedException{
		
		if(!tester.getCookieNamed("cart-item-count").equals("0")){
						
			tester.navigateUsingURL(uiConfig.getURL_USER_CART());
			
			tester.waitForPageLoad();
			
			//Remove the elements till the cart is empty
			while(true){
				
				List<WebElement> elements = tester.findElementsByXPath(uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());    
				
				//Check of not item is present in the cart, if yes then continue or else break
				if(elements==null || elements.isEmpty())
					break;
				
				//Remove each item from the cart
				tester.clickElement(elements.get(0));			
			}
		}
	}
	
	/**
	 * This methods searches for an item with a keyword using the walmart search bar
	 * 
	 * @param item item to be search 
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="EmptyCartIfNotEmpty")
	@Parameters("item")
	public void SearchForItem(String item) throws InterruptedException{
	
		//Search for an Item from the test data pool
		tester.addDataToTextField(tester.findElementByID("search"),item);
		tester.clickElement(tester.findElementByClassName("searchbar-submit"));
		
		//Wait for the search results
		Thread.sleep(5000);
	}
	
	/**
	 * This methods selects the valid item which is in stock from the list of items displayed form the search result 
	 * to view the item details, 
	 * 
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="SearchForItem")
	public void SelectItemViewProductDetails() throws InterruptedException{
		//select a product
		while(true)
		{
			if(tester.findElementByXPath(".//*[@id='tile-container']")==null)
			{
				System.out.println("For Special cases like toys");
			
				tester.clickElement(tester.findElementByXPath(".//*[@id='js-category-container']/div[2]/div[1]/div[1]/div/div/div/ul/li[1]/a"));				
			}
			else break;
		}
		
		int index=0;
		
		while(true)
		{
			List<WebElement> element=tester.findElementsByClassName("js-product-image");
		
			tester.navigateUsingURL(tester.getAttribute(element.get(index),"href"));
			tester.waitForPageLoad();
			
			String xpath="html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span";
			
			if(tester.findElementByXPath("html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span")!=null){
				tester.clickElement(tester.findElementByXPath(xpath));
			}
		
			Thread.sleep(5000);
		
			if(tester.findElementByClassName("js-cell-coverage-field")!=null){
				tester.addDataToTextField(tester.findElementByClassName("js-cell-coverage-field"),"95050");
			}
		
			if(tester.findElementByClassName("js-cell-coverage-check-btn")!=null){
				tester.clickElement(tester.findElementByClassName("js-cell-coverage-check-btn"));
			}
		
		
			if(tester.findElementByXPath(uiConfig.getXPATH_ADD_TO_CART())!=null){
				break;
			} else {
				tester._driver.navigate().back();
				index++;
			}
		}
		//Wait for the page to redirect	
	}
	
	/**
	 * This Methods adds an item to the shopping cart
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="SelectItemViewProductDetails")
	public void AddItemToCart() throws InterruptedException{
		//Add Item to the cart
		tester.waitForPageLoad();
		savedItemID=tester.getCurrentUrl();
		savedItemID=savedItemID.substring(savedItemID.lastIndexOf("/")+1);
		//System.out.println(savedItemID);
		
		tester.clickElement(tester.findElementByXPath(uiConfig.getXPATH_ADD_TO_CART()));
	
	}
	
	/**
	 * This methods validated if the same item is present in the cart as previously saved to the cart, 
	 * and no other item is present apart from the valid item
	 */
	@Test(dependsOnMethods="AddItemToCart")
	public void ValidateItemAddedToCart(){
	
		if(tester.getCurrentUrl().equalsIgnoreCase(uiConfig.getURL_USER_CART()));
		tester.navigateUsingURL(uiConfig.getURL_USER_CART());
		
		tester.waitForPageLoad();
		
		if(!tester.getText(tester.findElementByXPath(uiConfig.getXPATH_NO_ITEMS_IN_CART()),"Your cart: 1 item."))
		{
			System.out.println("one than one element");
			assert(false);
		}
		
		String itemIDCart=tester.getAttribute(tester.findElementByID("CartItemInfo"),"data-us-item-id");
		System.out.println(itemIDCart);
		
		if(itemIDCart.toLowerCase().trim().equals(savedItemID.toLowerCase().trim()))
		{
			assert(true);
		}
		else 
		{	
			System.out.println("Id did not match");
			assert(false);
		}
	}
	
	/**
	 * This methods empty the user shopping cart
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="ValidateItemAddedToCart")
	public void EmptyCart() throws InterruptedException{
	
		if(tester.getCurrentUrl().equalsIgnoreCase(uiConfig.getURL_USER_CART()));
			tester.navigateUsingURL(uiConfig.getURL_USER_CART());
		
		tester.waitForPageLoad();
		
		while(true)
		{
			List<WebElement> elements = tester.findElementsByXPath(uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());    
			
			if(elements==null || elements.isEmpty())
				break;
			tester.clickElement(elements.get(0));		
		}
	}
	
	/**
	 * This class helps user to log out from the current session
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="EmptyCart")
	public void WalmartUserLogout() throws InterruptedException {
		//Navigate to login page		
		tester.navigateUsingURL("https://www.walmart.com/account/logout"); 
		if(tester.findElementByClassName("signout-wrapper")!=null)
			assert(true);
		else 
			assert(false);
	}
	
	/**
	 * This method provides data for User Login
	 * @return return Object array with user name and passwords as values for walmart user sign in
	 */
	@DataProvider(name="login")
		public Object[][] Login() {
			return new Object[][] {
				new Object [] { "priyanka.tayadeb@gmail.com" ,
								"abcd123"
			}
		};
	}
	
	/**
	 * This method is executed after the test is run, to close the driver or can be used for other tier down process
	 */
	@AfterTest
	public void afterTest() {
		tester.Close();
	}
}
