package PaymentE2E;

import static org.testng.Assert.fail;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class HarveyNormanPayment {


	private WebDriver driver;
	private String baseUrl;
	//private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();	
	private Actions action;



	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\2944\\Java\\Browser\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "https://www.harveynorman.com.au";
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test
	public void testPaymentProcess() throws Exception {
		driver.get(baseUrl);
		//explicitWaitElement(By.id("search")).clear();

		//Search iphone
		explicitWaitElement(By.id("search")).sendKeys("iphone");
		explicitWaitElement(By.xpath("//form[@id='search_form']/button")).click();
		explicitWaitElement(By.xpath("//div[@id='category-grid']/div[1]/div[2]/div/div/div[3]/a")).click();
		/*WebElement addToCartBtn = explicitWaitElement(By.id("btn-add-to-cart"));
		addToCartBtn.click();*/
		explicitWaitElement(By.id("btn-add-to-cart")).click();
		
		/*//Code to get the status code of the add to cart button click.
		WebElement atc = driver.findElement(By.id("product_addtocart_form"));
		String atcurl = atc.getAttribute("action");
		String addToCartURL = "http://www.harveynorman.com.au/ajaxwishlist/cart/add/product/264469/isAjax/1/?product=264469&related_product=&options%5B999999900%5D=";
		int statusCode = RestAssured.get(atcurl).statusCode();
		System.out.println("Status code for add to cart ajax call " + statusCode );*/
		
		
		WebElement addToCartMsg = explicitWaitElement(By.id("btn_checkout"));

		if(addToCartMsg.isDisplayed())
		{
			String msg = addToCartMsg.getText();
			if(msg.equalsIgnoreCase("View Cart"))
			{
				System.out.println("Wow...Added Iphone in the Cart....!!");
				driver.findElement(By.xpath("//a[@id='shopAllMd']")).sendKeys("");
			}
			else
				System.out.println("NO luck...");
		}
		else
		{
			Thread.sleep(5000);
		}

		//Go back to the Shop all product link and Search Trampolines
		//Code to handle Mouse hover.
		action = new Actions(driver);
		action.moveToElement(explicitWaitElement(By.xpath("//a[@id='shopAllMd']"))).click().perform();
		action.moveToElement(driver.findElement(By.xpath("//*[@id='navMdList']/ul/li[22]/a"))).perform();
		action.moveToElement(explicitWaitElement(By.linkText("Trampolines"))).perform();
		action.click().perform();

		//Add Trampoline in a cart.
		explicitWaitElement(By.linkText("Plum 8ft Space Zone V3 Trampoline")).click();
		explicitWaitElement(By.id("btn-add-to-cart")).click();
		WebElement TopCartBtn = explicitWaitElement(By.id("top-cart"));
		Thread.sleep(2000);
		TopCartBtn.sendKeys("");
		int itemCount = Integer.parseInt(TopCartBtn.getText());
		if(itemCount>0)
		{	
			//Click on the green basket icon
			System.out.println("Wow...Added Trampolines in the Cart....!!");

		}
		else
			System.out.println("Cart is empty");

		explicitWaitElement(By.id("top-cart")).click();
		Thread.sleep(2000);
		WebElement continueBtn = driver.findElement(By.xpath("//div[@id='topCartContent']/div/div[2]/div[2]/div/a"));

		if(continueBtn.isDisplayed()== true)
		{
			continueBtn.click();
			System.out.println("Continue button clicked...");

		}

		else
		{
			fluentWaitElement(By.xpath("//div[@id='topCartContent']/div/div[2]/div[2]/div/a"), 60, driver).click();
			System.out.println("Continue button clicked after fluent wait...");
		}

		//Thread.sleep(3000);



		//Change the Postcode

		explicitWaitElement(By.id("s_method_delivery_delivery")).click();
		WebElement pincodeAutoPopulateTB = driver.findElement(By.name("my-location-input"));
		explicitWaitElement(By.name("my-location-input")).sendKeys("Sydney");
		Thread.sleep(1000);		
		pincodeAutoPopulateTB.sendKeys(Keys.ARROW_DOWN);
		pincodeAutoPopulateTB.sendKeys(Keys.ENTER);

		Thread.sleep(5000);
		//By checkoutBtn = By.id("btn_checkout");
		By checkoutBtn = By.xpath("//a[@id='btn_checkout']");
		WebElement CheckBtn = explicitWaitElement(checkoutBtn);
		if (CheckBtn.isDisplayed() == true && CheckBtn.isEnabled()== true)
		{
			CheckBtn.click();
			System.out.println("Found Checkout button and clicked");
		}
		else if(CheckBtn.isDisplayed() != true && CheckBtn.isEnabled()!= true)
		{
			Thread.sleep(3000);
			CheckBtn.click();
			System.out.println("Found Checkout button after Sleep and clicked");
		}
		else
			System.out.println("Did not Find Checkout button...Failed");

		//Fill the payment form
		explicitWaitElement(By.id("billing:firstname")).clear();
		explicitWaitElement(By.id("billing:firstname")).sendKeys("Test");
		explicitWaitElement(By.id("billing:lastname")).clear();
		explicitWaitElement(By.id("billing:lastname")).sendKeys("Test");
		explicitWaitElement(By.id("billing:telephone")).clear();


		//Enter Card details
		explicitWaitElement(By.id("billing:telephone")).sendKeys("0414141414");
		//explicitWaitElement(By.id("billing:email")).clear();
		explicitWaitElement(By.id("billing:email")).sendKeys("test@test.com");
		//explicitWaitElement(By.id("billing:street1")).clear();
		explicitWaitElement(By.id("billing:street1")).sendKeys("112 Alexander Street");
		//explicitWaitElement(By.id("id_comments")).clear();
		explicitWaitElement(By.id("id_comments")).sendKeys("Testing");

		explicitWaitElement(By.id("id_accept_terms")).sendKeys(Keys.SPACE);

		Select visaCardDropDown  = new Select(explicitWaitElement(By.id("checkout-payment-method-switcher")));
		visaCardDropDown.selectByVisibleText("Credit Card");


		System.out.println("Moving to CC Payment !!!!");	

		Thread.sleep(5000);
		//Switch to parent frame using index.
		WebDriver parentFrameObj = fluentWaitFrame(2, 100, driver);

		System.out.println("Switched to Parent");
		//Switch to ChildFrame using Parent Frame Object
		WebElement childFrameCCnum = explicitWaitElement(By.className("gw-proxy-cardNumber"));

		if (childFrameCCnum.isDisplayed()==true){

			WebDriver childFrameObjCCNum = parentFrameObj.switchTo().frame(childFrameCCnum);

			//Switch to the CVVnumber Frame using the child frame A object.
			System.out.println("Switched to Child Frame for  CC Num textbox.");

			//Enter the Credit Card number in the textbox using the CC number Frame object. 
			childFrameObjCCNum.findElement(By.xpath("/html/body/input")).sendKeys("4111111111111111");

			//Switch back to Parent Frame.
			WebDriver backToParent = childFrameObjCCNum.switchTo().parentFrame();

			//Enter other details.
			backToParent.findElement(By.id("tnspci_cc_owner")).sendKeys("AUDREY KEAT");

			//Select the values from the Date dropDwon web element.
			new Select(backToParent.findElement(By.id("tnspci_expiry_month"))).selectByVisibleText("06 - June");
			new Select(backToParent.findElement(By.id("tnspci_expiry_year"))).selectByVisibleText("2017");
			new Select(backToParent.findElement(By.id("tnspci_expiry_year"))).selectByVisibleText("2018");

			//Switch to the CVVnumber Textbox Frame.
			
			WebElement childFrame = explicitWaitElement(By.cssSelector(".gw-proxy-securityCode"));
			if (childFrame.isDisplayed()==true){

				WebDriver childFrameObjCVV = parentFrameObj.switchTo().frame(childFrame);
				childFrameObjCVV.findElement(By.xpath("/html/body/input")).sendKeys("183");
				System.out.println("Coming out of child cvv frame.!!1");

				//Switch back to Parent Frame.
				backToParent = childFrameObjCVV.switchTo().parentFrame();
				backToParent.findElement(By.id("tnspci_onestepcheckout-place-order_btn")).click();
			}
			else 
				System.out.println("Didn't find the child frame...");

		}

		else 
			System.out.println("Didn't find the Parent frame...");

		
		String expectedFailuarMsg = "Your transaction was declined by issuer. Contact us if you need help";
		WebElement statusAlert = explicitWaitElement(By.xpath("//*[@id='content']/div[2]"));
		String actualFailuarMsg = statusAlert.getText();
		Assert.assertEquals(actualFailuarMsg, expectedFailuarMsg, "Satus message is not matching..");
				
		
		
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	//Method for explicit wait - to resolve the Syncronization issue.
	public WebElement explicitWaitElement(final By locator) {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		WebElement webelmnt = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return  webelmnt;
	};

	//Method for explicit wait - to resolve the Syncronization issue.
	public WebElement explicitWaitElement(final By locator, long timeout, WebDriver dr) {

		WebDriverWait wait = new WebDriverWait(dr, timeout);

		WebElement webelmnt = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		return  webelmnt;
	};

	public WebElement fluentWaitElement(final By locator, long timeout, WebDriver dr) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(dr)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class);


		//WebDriverWait wait = new WebDriverWait(dr, timeout);

		WebElement webelmnt = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

		return  webelmnt;
	};

	public WebDriver fluentWaitFrame(int locator, long timeout, WebDriver dr) {

		Wait<WebDriver> wait = new FluentWait<WebDriver>(dr)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class);


		//WebDriverWait wait = new WebDriverWait(dr, timeout);

		WebDriver driver = wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));

		return  driver;
	};

	public static String addToCartHTTPRqRs(URL url) throws Exception{
		String response = "";
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		try
		{
			connection.connect();
			response = connection.getResponseMessage();	
			int i = connection.getResponseCode();
			connection.disconnect();
			System.out.println("Response code from the Server after Add to cart action:" + i);
			return response;
		}
		catch(Exception exp)
		{
			return exp.getMessage();
		}  				
	}
	

	

}
