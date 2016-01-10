package resources.configuration;
public class WalmartUI {

	private final String URL_USER_LOGIN="https://www.walmart.com/account/login";
	private final String URL_USER_CART="https://www.walmart.com/cart/";
	
	private final String XPATH_ADD_TO_CART=".//*[@id='WMItemAddToCartBtn']";
	private final String XPATH_NO_ITEMS_IN_CART=".//*[@id='spa-layout']/div/div/div[1]/div/h3";
	private final String XPATH_FIRST_ITEM_CART=".//*[@id='spa-layout']/div/div/div[1]/div/div[4]/div[2]/div/div/div[2]/div[1]/a";
	private final String XPATH_REMOVE_ITEM_FROM_CART=".//*[@id='CartRemItemBtn']";
	
	public String getURL_USER_LOGIN() {
		return URL_USER_LOGIN;
	}

	public String getURL_USER_CART() {
		return URL_USER_CART;
	}

	public String getXPATH_ADD_TO_CART() {
		return XPATH_ADD_TO_CART;
	}

	public String getXPATH_NO_ITEMS_IN_CART() {
		return XPATH_NO_ITEMS_IN_CART;
	}

	public String getXPATH_FIRST_ITEM_CART() {
		return XPATH_FIRST_ITEM_CART;
	}

	public String getXPATH_REMOVE_ITEM_FROM_CART() {
		return XPATH_REMOVE_ITEM_FROM_CART;
	}
	
}
