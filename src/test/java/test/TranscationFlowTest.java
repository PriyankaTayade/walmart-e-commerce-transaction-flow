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
	String itemName;
	
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
	 */
	@Test(dataProvider = "login")
	public void WalmartUserLogin(String email, String password) {
		try{	
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
			assert(tester.matchText(tester.findElementByClassName("recent-orders-heading"), "Welcome to your Walmart account!"));		
		}	
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This methods checks if the cart is empty, if it is not empty then it will delete all the items from the cart
	 * 
	 */
	@Test(dependsOnMethods="WalmartUserLogin")
	public void EmptyCartIfNotEmpty() {
		try
		{
			if(!tester.getCookieNamed("cart-item-count").equals("0")){
							
				removeItemFromCart();
			}
			else
				Assert.assertTrue("Cart is Empty!", true);
		}	
		catch(Exception e){
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
	}
	
	/**
	 * This methods searches for an item with a keyword using the walmart search bar
	 * 
	 * @param item item to be search 
	 */
	@Test(dependsOnMethods="EmptyCartIfNotEmpty")
	@Parameters("item")
	public void SearchForItem(String item){
	
		try
		{
			//Search for an Item from the test data pool
			tester.addDataToTextField(tester.findElementByID("search"),item);
			tester.clickElement(tester.findElementByClassName("searchbar-submit"));
			
			//Wait for the search results
			Thread.sleep(5000);
			
			//Very if the product list is displayed using search keyword
			int attempt=0;
			
			while(true)
			{
				if(tester.findElementByXPath(".//*[@id='tile-container']")==null)
				{
					if(attempt>5)
					{
						Assert.assertFalse("Too many attempts to navigate through category, process terminated !", true);
						break;
					}
					
					System.out.println("For Special cases like toys, search in category");
				
					tester.clickElement(tester.findElementByXPath(".//*[@id='js-category-container']/div[2]/div[1]/div[1]/div/div/div/ul/li[1]/a"));				
				
					attempt++;
				}
				else 
					break;
			}
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
	}
	
	/**
	 * This methods selects the valid item which is in stock from the list of items displayed form the search result 
	 * to view the item details, 
	 * 
	 */
	@Test(dependsOnMethods="SearchForItem")
	@Parameters("zipcode")
	public void SelectItemViewProductDetails(String zipcode) throws InterruptedException{
		try
		{	
			int productIndex=0;
			
			while(true)
			{
				List<WebElement> element=tester.findElementsByClassName("js-product-image");
			
				tester.navigateUsingURL(tester.getAttribute(element.get(productIndex),"href"));
				tester.waitForPageLoad();
				
				//String xpath="html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span";
				
				if(tester.findElementByXPath(uiConfig.getXPATH_SELECT_ITEM_COLOR_RADIO_BUTTON())!=null)
				{
					tester.clickElement(tester.findElementByXPath(uiConfig.getXPATH_SELECT_ITEM_COLOR_RADIO_BUTTON()));
				}
			
				Thread.sleep(5000);
			
				if(tester.findElementByClassName("js-cell-coverage-field")!=null)
				{
					tester.addDataToTextField(tester.findElementByClassName("js-cell-coverage-field"),zipcode);
				}
			
				if(tester.findElementByClassName("js-cell-coverage-check-btn")!=null)
				{
					tester.clickElement(tester.findElementByClassName("js-cell-coverage-check-btn"));
				}
			
			
				if(tester.findElementByXPath(uiConfig.getXPATH_ADD_TO_CART())!=null)
				{
					Assert.assertTrue("Valid item is selected successfully !", true);
					break;
				} 
				else 
				{
					if(productIndex>5)
					{
						Assert.assertFalse("Did not find item avaible or in stock, too many attempts, process terminated !", true);
						break;
					}
					productIndex++;
					tester._driver.navigate().back();				
				}
			}			
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
	}
	
	/**
	 * This Methods adds an item to the shopping cart
	 */
	@Test(dependsOnMethods="SelectItemViewProductDetails")
	public void AddItemToCart() {
		//Add Item to the cart
		try
		{
			tester.waitForPageLoad();
			
			savedItemID=tester.getCurrentUrl();
			savedItemID=savedItemID.substring(savedItemID.lastIndexOf("/")+1);
			
			itemName=tester.getText(tester.findElementByXPath(uiConfig.get_XPATH_ITEM_NAME_TO_SAVE_IN_CART()));
			
			System.out.println("Save Item in the cart with Id: "+savedItemID);
			
			tester.clickElement(tester.findElementByXPath(uiConfig.getXPATH_ADD_TO_CART()));
			
			Assert.assertTrue("Item Is added to the cart !", true);
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
	
	}
	
	/**
	 * This methods validated if the same item is present in the cart as previously saved to the cart, 
	 * and no other item is present apart from the valid item
	 */
	@Test(dependsOnMethods="AddItemToCart")
	public void ValidateItemAddedToCart(){
	
		try
		{
			if(!tester.getCurrentUrl().equalsIgnoreCase(uiConfig.getURL_USER_CART()));
			{
				tester.navigateUsingURL(uiConfig.getURL_USER_CART());
			
				tester.waitForPageLoad();
			}
			
			if(!tester.matchText(tester.findElementByXPath(uiConfig.getXPATH_NO_ITEMS_IN_CART()),"Your cart: 1 item."))
			{
				Assert.assertFalse("More than one item is present in the cart", true);
			}
			else Assert.assertTrue("Only 1 item is present in the cart", true);
			
			String itemIDCart=tester.getAttribute(tester.findElementByID("CartItemInfo"),"data-us-item-id");
			System.out.println("Item present in the cart with Id: "+itemIDCart);
			
			if(itemIDCart.toLowerCase().trim().equals(savedItemID.toLowerCase().trim()))
			{
				Assert.assertTrue("Item in the cart match with the item saved", true);
			}
			else if(itemIDCart.toLowerCase().trim().substring(0,itemIDCart.trim().length()-1)
					.equals(savedItemID.toLowerCase().trim().substring(0,savedItemID.trim().length()-1)))
			{
				System.out.println("Match Item Name, if only the last digit of item id do not match");
				if(tester.matchText(tester.findElementByXPath(".//*[@id='CartItemInfo']/span"), itemName))
				Assert.assertTrue("Item in the cart match with the item saved", true);
			}
			else 
			{	
				Assert.assertFalse("Item in the cart did not matched with the item saved", true);
			}
		}	
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
	}
	
	/**
	 * This methods empty the user shopping cart
	 */
	@Test(dependsOnMethods="ValidateItemAddedToCart")
	public void EmptyCart() {
	
		removeItemFromCart();
	}
	
	/**
	 * This class helps user to log out from the current session
	 * @throws InterruptedException If interrupted during wait this class throws an Interrupted Exception
	 */
	@Test(dependsOnMethods="EmptyCart")
	public void WalmartUserLogout() throws InterruptedException {
		try{
			//Navigate to login page		
			tester.navigateUsingURL("https://www.walmart.com/account/logout"); 
			if(tester.findElementByClassName("signout-wrapper")!=null)
				Assert.assertTrue("User Logout successfully", true);
			else 
				Assert.assertFalse("User did not Logout successfully", true);
		}	
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
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
		try
		{
			tester.Close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void removeItemFromCart() {

		try
		{
			//IF user is not in shopping cart page to to cart url
			if(!tester.getCurrentUrl().equalsIgnoreCase(uiConfig.getURL_USER_CART()))
			{
				tester.navigateUsingURL(uiConfig.getURL_USER_CART());
				//wait for the page to load
				tester.waitForPageLoad();
			}
			
			while(true)
			{
				//get the list of items in cart ( the elements variable contains remove button for each item )
				List<WebElement> removeElements = tester.findElementsByXPath(uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());    
				
				//If element list is not empty or null click to remove first element from the cart
				if(removeElements!=null && !removeElements.isEmpty())
					tester.clickElement(removeElements.get(0));	
				else break; //if no remove element is found, that means cart is empty and break from the loop
				
				if(removeElements.size()<2)
					break;  // if list contains only one or less element which is removed in previous step then break
	
			}
			
			List<WebElement> removeElements = tester.findElementsByXPath(uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());
			if(removeElements==null || removeElements.isEmpty())
				Assert.assertTrue("Cart is Emptied Succesfully", true);
			else 
				Assert.assertFalse("User not able to Empty cart", true);
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
		}
	}
}
