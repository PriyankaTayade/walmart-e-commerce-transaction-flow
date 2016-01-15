# walmart-e-commerce-transaction-flow

Author:      Priyanka Tayade

Email:       priyanka.tayadeb@gmail.com

Created On:  1/8/2016

Version: 1.0

MAINTENANCE
------------------------------------------------------------------------
Modified On: --

REQUIREMENT TO RUN THE PROJECT
------------------------------------------------------------------------
1. Java 8
2. Maven 3.3
3. Selenium webdriver 2.4
4. TestNG 6.9


NOTE: If you do not java installed

 Here is the link to download 
 
 jdk : http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
 
 jre: http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
 
 Also here is the link to how to setup java home
 
 https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/

Relative Path of chrome driver to project directory for windows 32 bit: 
src\test\java\resources\selenium\webdriver\chromedriver_win32

Tested on windows 8 

Tested on browsers: firefox and crome

IDE: eclipse mars

PURPOSE:
-------------------------------------------------------------------------
Automate an end-to-end user e-commerce transaction flow using any open source tool for www.walmart.com with an existing customer on Chrome browser.

SCENARIO TO AUTOMATE:
-------------------------------------------------------------------------
1. Login using existing account

2. Check if the cart is not empty, if not then removes all items from the cart
	
3. Perform a search on home page from a pool of key words given below

4. Identify an item from the result set that you can add to cart

5. Add the item to cart

6. Validate that item added is present in the cart and is the only item in the cart

7. Empty the cart after validation

8. User is logged out



Test Case:  Login using existing account
-----------------------------------------------------------------------------
Test cases covered.

1.  Checks if user is able to login using valid credentials.
2.  After successful login is valid login success message displayed.

Test cases not covered.

1. If invalid login credentials are provided, user is not able to login.
2. If invalid login credentials are provided, is correct error message displayed.

Test Case:  Logout from account
-----------------------------------------------------------------------------
Test cases covered.

1. Checks if user after logout.

Test cases not covered.

1. After successful logout is valid logout success message displayed.
2. User session is no longer present.

Test Case: Perform a search on home page from a pool of key words given below
-----------------------------------------------------------------------------
Test cases covered.

1. Test suite cover 5 test cases with each test searching for these terms: tv, socks, dvd, toys, iPhone and respectively
2. If items are not displayed and instead category or department choices are displayed, example for special cases like toys

Test cases not covered.

1. For invalid search, no results are displayed

Test Case: Identify an item from the result set that you can add to cart
----------------------------------------------------------------------------
Test cases covered.

1. User is able to select an item from the list of item displayed.

Implementation Details.

1. For special cases like toys if the list of items is not displayed.
2. First category from left menu is selected iteratively till the list of item is displayed.
3. This implementation can go in an infinite loop, therefore maximum number of time this iterative selection is done is set to 5 before terminating the case. 

Test Case: Add the item to cart
----------------------------------------------------------------------------
Test cases covered.

1. If the item is out of stock, select another item and keep on repeating till a item is found which is in stock, assuming that probability of finding continuous item out of stock is minimum.
2. If a color is to be selected for an item to be added to the cart, selected the color and adds the item to the cart provided the item is in stock.
3. If it is mandatory to check of the item is available at particular location before adding to cart, put the zip code and check availability.


Implementation details.

1. Item id is saved before adding to cart.
2. Item name is also saved before adding to the cart

Test cases not covered.

1. Item id if not present, we cannot validate the test case 


Test Case: Validate the cart 
-----------------------------------------------------------------------------
Test cases covered
1.Only one single item is present in cart
2. Check if item id of the item in cart match with the item id of previously added item
3. For some items like "iphone" the item id changes to parent item id while in cart. where parent item id has only one last digit different. In this case also check if the item name matches to verify the item.

Test cases not covered
1. Does not handle condition when no item id is present.

TEST DATA:
-------------------------------------------------------------------------

 Account details: created own account with following credentials
	User name: priyanka.tayadeb@gmail.com
	Password:  abcd123

 Search terms: tv, socks, dvd, toys, iPhone
	

INSTALLATION GUIDE FOR WINDOWS
--------------------------------------------------------------------------

STEP 1. DOWNLOAD THE SOURCE CODE

Method 1
1. Clone the project to your local Git repository

Method 2
1. Download the compressed project file from github
2. Extract the file in local project folder
 
STEP 2: CREATE MAVEN PROJECT 

1. Open eclipse
2. Click on "File" in the top menu bar > Click on "Import"
3. Go to "Maven", and expand > under Maven click on "Existing Maven Project" > Click "Next"
4. Click on "Browse" > Select the download source code directory as "Root Directory". 
5. It will detect the pom.xml > Click "Finish".
6. This will load the project in project explorer. > Expand the project


STEP 3: RUN THE TEST AS TESTNG SUITE

1. Right Click on TranscationFlowTest.xml
2. Go to Run > Click on TestNG Suite.
4. Monitor the Suite and check for number of test case passed, failed and skipped.
5. Ideally all the test case should pass.


LIMITATION:
-----------------------------------------------------------------------------
1. Due to time constrained did not test and implement on all browsers and systems due to lack of resources such as OS.
3. Did not cover all the test scenarios due to time constraint.

TRADE OFF
-----------------------------------------------------------------------------
1. wait conditions are very helpful but not in case of add to cart where some elements such as zip code or color check box might not be present for all search item in the cart making add to cart function slow. this can be optimized.

TECHNICAL CHOICES
-----------------------------------------------------------------------------
1. Selenium was easy to learn quickly in 2 days, lots of material available online
2. Selenium was a good choice for the assignment requirement since I had to only automate web application UI

 

