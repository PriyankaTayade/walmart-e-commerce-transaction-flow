package resources.model;

import java.util.Random;

public class TransactionFlowTestData {

		
	private final String[] testData={"tv", "socks", "dvd", "toys", "iPhone"};
	
	//private String savedItemID="";
	public String getTestDataFromPoolRandom(){
		
	    Random rand = new Random();
	    int min=0;
	    int max=testData.length-1;
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return testData[randomNum];
	    
	}

	
}
