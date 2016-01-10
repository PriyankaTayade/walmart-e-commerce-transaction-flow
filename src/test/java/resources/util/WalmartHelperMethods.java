package resources.util;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import resources.configuration.User;
import resources.configuration.WalmartUI;
import resources.model.TransactionFlowTestData;
import resources.selenium.helper.SeleniumHelper;

public class WalmartHelperMethods {
	
	SeleniumHelper tester;
	TransactionFlowTestData data;
	WalmartUI uiConfig;
	User userConfig;
	
	public WalmartHelperMethods(){
		tester=new SeleniumHelper();
		data=new TransactionFlowTestData();
		uiConfig=new WalmartUI();
		userConfig=new User();
	}

	public void WalmartUserLogin() throws InterruptedException {
		//Navigate to login page
		tester.navigateUsingURL(uiConfig.getURL_USER_LOGIN());
				
		  // Wait for the page to load
				
				
		  //login as previously registered user.
		tester.addDataToTextField(tester.findElementByID("login-username"), userConfig.getUsername());
		tester.addDataToTextField(tester.findElementByID("login-password"), userConfig.getPassowrd());
		tester.clickElement(tester.findElementByClassName("login-sign-in-btn"));
		   Thread.sleep(5000);
	}
	
	public void WalmartUserLogout() throws InterruptedException {
		//Navigate to login page
		
		   Thread.sleep(5000);
	}
	
	public void isCartEmpty() throws InterruptedException{
		if(!tester.getCookieNamed("cart-item-count").equals("0")){
			 System.out.println("Cart Empty");
		}
		else {
			System.out.println("Cart Not Empty");
			EmptyCart();
		}
		
	}
	
	public void SearchForItem(String searchString) throws InterruptedException{
		
		//Search for an Item from the test data pool
		tester.addDataToTextField(tester.findElementByID("search"),searchString);
		tester.clickElement(tester.findElementByClassName("searchbar-submit"));
		
		//Wait for the search results
		Thread.sleep(5000);
	}
	
	
	public void SelectItemViewProductDetails() throws InterruptedException{
		//select a product
		while(true){
			if(tester.findElementByXPath(".//*[@id='tile-container']")==null)
			{
				System.out.println("For Special cases like toys");
				
				tester.clickElement(tester.findElementByXPath(".//*[@id='js-category-container']/div[2]/div[1]/div[1]/div/div/div/ul/li[1]/a"));				
			}
			else break;
		}
		int index=0;
		
		while(true){
			List<WebElement> element=tester.findElementsByClassName("js-product-image");
		
			tester.navigateUsingURL(tester.getAttribute(element.get(index),"href"));
			Thread.sleep(5000);
			String xpath="html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span";
			if(tester.findElementByXPath("html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span")!=null){
				tester.clickElement(tester.findElementByXPath(xpath));
				System.out.println("clicked");
			}
			Thread.sleep(5000);
			if(tester.findElementByClassName("js-cell-coverage-field")!=null){
				tester.addDataToTextField(tester.findElementByClassName("js-cell-coverage-field"),"95050");
				System.out.println("zip added");
			}
			if(tester.findElementByClassName("js-cell-coverage-check-btn")!=null){
				tester.clickElement(tester.findElementByClassName("js-cell-coverage-check-btn"));
				System.out.println("clicked");
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
	
	
	public String AddItemToCart() throws InterruptedException{
		//Add Item to the cart
		tester.waitForPageLoad();
		String savedItemID=tester.getCurrentUrl();
		savedItemID=savedItemID.substring(savedItemID.lastIndexOf("/")+1);
		//System.out.println(savedItemID);

		tester.clickElement(tester.findElementByXPath(uiConfig.getXPATH_ADD_TO_CART()));
								
		return savedItemID;
	}
	
	
	public boolean ValidateItemAddedToCart(String savedItemID){
		
		if(tester.getCurrentUrl().equalsIgnoreCase(uiConfig.getURL_USER_CART()));
			tester.navigateUsingURL(uiConfig.getURL_USER_CART());
			
		tester.waitForPageLoad();
		
		if(!tester.getText(tester.findElementByXPath(uiConfig.getXPATH_NO_ITEMS_IN_CART()),"Your cart: 1 item.")){
			System.out.println("one than one element");
				return false;
		}
		
		String itemIDCart=tester.getAttribute(tester.findElementByXPath(uiConfig.getXPATH_FIRST_ITEM_CART()),"data-us-item-id");
		System.out.println(itemIDCart);
		if(itemIDCart.toLowerCase().trim().equals(savedItemID.toLowerCase().trim()))
			return true;
		
		System.out.println("Id did not match");
		return false;
	}
	
	
	public void EmptyCart() throws InterruptedException{
		
		if(tester.getCurrentUrl().equalsIgnoreCase(uiConfig.getURL_USER_CART()));
		tester.navigateUsingURL(uiConfig.getURL_USER_CART());
		
		tester.waitForPageLoad();
		
		while(true){
			List<WebElement> elements = tester.findElementsByXPath(uiConfig.getXPATH_REMOVE_ITEM_FROM_CART());    

				if(elements==null || elements.isEmpty())
					break;
				tester.clickElement(elements.get(0));
            
       }
	}


}
