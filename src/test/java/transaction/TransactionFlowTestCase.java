package transaction;

import org.junit.Test;
import org.junit.runner.JUnitCore;

import model.TransactionFlowTestData;
import util.WalmartHelperMethods;


public class TransactionFlowTestCase {

	WalmartHelperMethods test;
	
	
	public TransactionFlowTestCase(){
		test=new WalmartHelperMethods();
	
	}
	
	
	public static void main(String[] args) {
	    System.out.println("In main method");
	    JUnitCore jCore = new JUnitCore();
	    jCore.run(TransactionFlowTestCase.class);
	}
	
	@Test
	public void BasicTransactionFlowTest() throws InterruptedException{
		TransactionFlowTestData data=new TransactionFlowTestData();
		// Login as previously registered user
		test.WalmartUserLogin();
		
		//Check if the cart is empty. If not empty, empty the cart 
		test.isCartEmpty();
		
		//Search for an Item
		//test.SearchForItem(data.getTestDataFromPoolRandom());
		test.SearchForItem("tosadddddfys");
		
		//select one item from search result
		test.SelectItemViewProductDetails();
		
		// Add the selected item 
		String savedItemID=test.AddItemToCart();
		
		//Check if the correct item is added and is the only item present in the cart
		assert(test.ValidateItemAddedToCart(savedItemID));
		
		
		//Empty the cart
		test.EmptyCart();
		
		//Logout 
		test.WalmartUserLogout();
	}
	
}
