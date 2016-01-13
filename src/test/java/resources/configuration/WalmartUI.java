package resources.configuration;
/**
 * 
 * @author priyanka
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
	 * @return
	 */
	public String getURL_USER_LOGIN() {
		return _URL_USER_LOGIN;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getURL_USER_CART() {
		return _URL_USER_CART;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getXPATH_ADD_TO_CART() {
		return _XPATH_ADD_TO_CART;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getXPATH_NO_ITEMS_IN_CART() {
		return _XPATH_NO_ITEMS_IN_CART;
	}

	/**
	 * 
	 * @return
	 */
	public String getXPATH_FIRST_ITEM_CART() {
		return _XPATH_FIRST_ITEM_CART;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getXPATH_REMOVE_ITEM_FROM_CART() {
		return _XPATH_REMOVE_ITEM_FROM_CART;
	}

	public String getXPATH_SELECT_ITEM_COLOR_RADIO_BUTTON() {
		return _XPATH_SELECT_ITEM_COLOR_RADIO_BUTTON;
	}

	public String get_XPATH_ITEM_NAME_TO_SAVE_IN_CART() {
		return _XPATH_ITEM_NAME_TO_SAVE_IN_CART;
	}
	
}
