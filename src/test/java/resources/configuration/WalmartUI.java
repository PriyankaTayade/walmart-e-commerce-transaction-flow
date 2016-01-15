package resources.configuration;
/**
 * This clas is to keep track of relative xpath with might change with css design
 * @author priyanka
 * version 1.0
 *
 */
public class WalmartUI {

	private final String _URL_USER_LOGIN="https://www.walmart.com/account/login";
	
	private final String _URL_USER_CART="https://www.walmart.com/cart/";
	
	private final String _XPATH_ADD_TO_CART=".//*[@id='WMItemAddToCartBtn']";
	
	private final String _XPATH_NO_ITEMS_IN_CART=".//*[@id='spa-layout']/div/div/div[1]/div/h3";
	
	private final String _XPATH_FIRST_ITEM_CART=".//*[@id='spa-layout']/div/div/div[1]/div/div[4]/div[2]/div/div/div[2]/div[1]/a";
	
	private final String _XPATH_REMOVE_ITEM_FROM_CART=".//*[@id='CartRemItemBtn']";
	
	private final String _XPATH_SELECT_ITEM_COLOR_RADIO_BUTTON="html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[5]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/div/div/span[1]/label/span";

	private final String _XPATH_ITEM_NAME_TO_SAVE_IN_CART="html/body/div[2]/section/section[4]/div/div[2]/div[1]/div[4]/div/h1/span";
	
	/**
	 * 
	 * @return url for user login
	 */
	public String getURL_USER_LOGIN() {
		return _URL_USER_LOGIN;
	}
	
	/**
	 * 
	 * @return url for user cart
	 */
	public String getURL_USER_CART() {
		return _URL_USER_CART;
	}
	
	/**
	 * 
	 * @return xpath to add to cart button
	 */
	public String getXPATH_ADD_TO_CART() {
		return _XPATH_ADD_TO_CART;
	}
	
	/**
	 * 
	 * @return xpath to number of items in cart
	 */
	public String getXPATH_NO_ITEMS_IN_CART() {
		return _XPATH_NO_ITEMS_IN_CART;
	}

	/**
	 * 
	 * @return xpath to first item in cart
	 */
	public String getXPATH_FIRST_ITEM_CART() {
		return _XPATH_FIRST_ITEM_CART;
	}
	
	/**
	 * 
	 * @return xpath to remove item from cart
	 */
	public String getXPATH_REMOVE_ITEM_FROM_CART() {
		return _XPATH_REMOVE_ITEM_FROM_CART;
	}

	/**
	 * 
	 * @return xpath to select item color radio button in save to cart page
	 */
	public String getXPATH_SELECT_ITEM_COLOR_RADIO_BUTTON() {
		return _XPATH_SELECT_ITEM_COLOR_RADIO_BUTTON;
	}
	
	/**
	 * 
	 * @return xpath to item name in save to cart page
	 */
	public String get_XPATH_ITEM_NAME_TO_SAVE_IN_CART() {
		return _XPATH_ITEM_NAME_TO_SAVE_IN_CART;
	}
	
}
