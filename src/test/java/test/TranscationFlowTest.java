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

/**
 * This class execute Transaction flow test case and provides various test which can be reused in different test suits
 * @author priyanka tayade
 * version 1.0
 *  
 */
public class TranscationFlowTest {
	
	private SeleniumHelper _tester;
	private WalmartUI _uiConfig;
	private String _savedItemID;	//If of the item to be saved in the cart of later validation
	private String _itemName; 	//Name of the item to be saved in the cart
	
	
	//************************ Before Test*************************************//
	/**
	 * 	Before test will initialize the driver and other required classes.
	 */
	@BeforeTest
	public void beforeTest() {
		
		_tester=new SeleniumHelper();
		_uiConfig=new WalmartUI();
	}
	
	
	//************************ Test *************************************//
	
	
	
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
			_tester.navigateUsingURL(_uiConfig.getURL_USER_LOGIN());	
			
			// wait for the login page to load
			_tester.waitForPageLoad();
			
			//login as using previously registered user credentials.
			_tester.addDataToTextField(_tester.findElementByID("login-username"), email);
			_tester.addDataToTextField(_tester.findElementByID("login-password"), password);
			
			_tester.clickElement(_tester.findElementByClassName("login-sign-in-btn"));
			
			//wait for user to sign in
			_tester.waitForPageLoad();
			
			//verify if user is successfully logged in
			assert(_tester.findElementByClassName("recent-orders-heading")!=null);
			assert(_tester.matchText(_tester.findElementByClassName("recent-orders-heading"), "Welcome to your Walmart account!"));		
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
			if(!_tester.getCookieNamed("cart-item-count").equals("0")){
							
				removeItemFromCart();
			}
			else
				Assert.assertTrue("Cart is Empty!", true);
		}	
		catch(Exception e){
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
			WalmartUserLogout();
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
			_tester.addDataToTextField(_tester.findElementByID("search"),item);
			_tester.clickElement(_tester.findElementByClassName("searchbar-submit"));
			
			//Wait for the search results
			Thread.sleep(5000);
			
			//Very if the product list is displayed using search keyword
			int attempt=0;
			
			while(true)
			{
				if(_tester.findElementByXPath(".//*[@id='tile-container']")==null)
				{
					if(attempt>5)
					{
						Assert.assertFalse("Too many attempts to navigate through category, process terminated !", true);
						break;
					}
					
					System.out.println("For Special cases like toys, search in category");
				
					_tester.clickElement(_tester.findElementByXPath(".//*[@id='js-category-container']/div[2]/div[1]/div[1]/div/div/div/ul/li[1]/a"));				
				
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
			WalmartUserLogout();
		}
	}
	
	
	
	
	
	/**
	 * This methods selects the valid item which is in stock from the list of items displayed form the search result 
	 * to view the item details, 
	 * @param zipcode takes zipcode as parameter to check availability of item in the area
	 */
	@Test(dependsOnMethods="SearchForItem")
	@Parameters("zipcode")
	public void SelectItemViewProductDetails(String zipcode) {
		try
		{	
			int productIndex=0;
			
			while(true)
			{
				List<WebElement> element=_tester.findElementsByClassName("js-product-image");
			
				_tester.navigateUsingURL(_tester.getAttribute(element.get(productIndex),"href"));
				_tester.waitForPageLoad();
				
				//String xpath="html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span";
				
				if(_tester.findElementByXPath(_uiConfig.getXPATH_SELECT_ITEM_COLOR_RADIO_BUTTON())!=null)
				{
					_tester.clickElement(_tester.findElementByXPath(_uiConfig.getXPATH_SELECT_ITEM_COLOR_RADIO_BUTTON()));
				}
			
				Thread.sleep(5000);
			
				if(_tester.findElementByClassName("js-cell-coverage-field")!=null)
				{
					_tester.addDataToTextField(_tester.findElementByClassName("js-cell-coverage-field"),zipcode);
				}
			
				if(_tester.findElementByClassName("js-cell-coverage-check-btn")!=null)
				{
					_tester.clickElement(_tester.findElementByClassName("js-cell-coverage-check-btn"));
				}
			
			
				if(_tester.findElementByXPath(_uiConfig.getXPATH_ADD_TO_CART())!=null)
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
					_tester.navigateBack();				
				}
			}			
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
			WalmartUserLogout();
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
			_tester.waitForPageLoad();
			
			_savedItemID=_tester.getCurrentUrl();
			_savedItemID=_savedItemID.substring(_savedItemID.lastIndexOf("/")+1);
			
			_itemName=_tester.getText(_tester.findElementByXPath(_uiConfig.get_XPATH_ITEM_NAME_TO_SAVE_IN_CART()));
			
			System.out.println("Save Item in the cart with Id: "+_savedItemID);
			
			_tester.clickElement(_tester.findElementByXPath(_uiConfig.getXPATH_ADD_TO_CART()));
			
			Assert.assertTrue("Item Is added to the cart !", true);
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
			// clean up
			removeItemFromCart();
			WalmartUserLogout();
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
			//Check if user is in cart page if not then navigate to cart
			if(!_tester.getCurrentUrl().equalsIgnoreCase(_uiConfig.getURL_USER_CART()));
			{
				_tester.navigateUsingURL(_uiConfig.getURL_USER_CART());
			
				_tester.waitForPageLoad();
				
			}
			
			//Check if there is only one item in the cart
			if(!_tester.matchText(_tester.findElementByXPath(_uiConfig.getXPATH_NO_ITEMS_IN_CART()),"Your cart: 1 item."))
			{
				Assert.assertFalse("More than one item is present in the cart", true);
			}
			else Assert.assertTrue("Only 1 item is present in the cart", true);
			
			
			String itemIDCart=_tester.getAttribute(_tester.findElementByID("CartItemInfo"),"data-us-item-id");
			System.out.println("Item present in the cart with Id: "+itemIDCart);
			
			//Check if the item id matches with the item which was added to the cart
			if(itemIDCart.toLowerCase().trim().equals(_savedItemID.toLowerCase().trim()))
			{
				Assert.assertTrue("Item in the cart match with the item saved", true);
			}
			//If item id only differs by last digit then check if the name matches
			else if(itemIDCart.toLowerCase().trim().substring(0,itemIDCart.trim().length()-1)
					.equals(_savedItemID.toLowerCase().trim().substring(0,_savedItemID.trim().length()-1)))
			{
				System.out.println("Match Item Name, if only the last digit of item id do not match");
				
				//Check if the item name matches
				if(_tester.matchText(_tester.findElementByXPath(".//*[@id='CartItemInfo']/span"), _itemName))
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
			
			// clean up
			removeItemFromCart();
			WalmartUserLogout();
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
	 */
	@Test(dependsOnMethods="EmptyCart")
	public void WalmartUserLogout() {
		try{
			//Navigate to login page		
			_tester.navigateUsingURL("https://www.walmart.com/account/logout"); 
			
			//check if user has successfully logged out 
			if(_tester.findElementByClassName("signout-wrapper")!=null)
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

	
	
	
	
	
	//************************ data providers*************************************//
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
	

	
	
	
	
	//************************ After Test*************************************//
	/**
	 * This method is executed after the test is run, to close the driver or can be used for other tier down process
	 */
	@AfterTest
	public void afterTest() {
		try
		{
			_tester.Close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	//************************ other methods *************************************//
	/**
	 * This function is used to empty the cart
	 */
	public void removeItemFromCart() {

		try
		{
			//IF user is not in shopping cart page to to cart url
			if(!_tester.getCurrentUrl().equalsIgnoreCase(_uiConfig.getURL_USER_CART()))
			{
				_tester.navigateUsingURL(_uiConfig.getURL_USER_CART());
				//wait for the page to load
				_tester.waitForPageLoad();
			}
			
			while(true)
			{
				//get the list of items in cart ( the elements variable contains remove button for each item )
				List<WebElement> removeElements = _tester.findElementsByXPath(_uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());    
				
				//If element list is not empty or null click to remove first element from the cart
				if(removeElements!=null && !removeElements.isEmpty())
					_tester.clickElement(removeElements.get(0));	
				else break; //if no remove element is found, that means cart is empty and break from the loop
				
				if(removeElements.size()<2)
					break;  // if list contains only one or less element which is removed in previous step then break
	
			}
			
			//check if remove cart was successful
			List<WebElement> removeElements = _tester.findElementsByXPath(_uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());
			if(removeElements==null || removeElements.isEmpty())
				Assert.assertTrue("Cart is Emptied Succesfully", true);
			else 
				Assert.assertFalse("User not able to Empty cart", true);
		}
		catch(Exception e)
		{
			Assert.assertFalse("Exception occured !", true);
			e.printStackTrace();
			WalmartUserLogout();
		}
	}
}
