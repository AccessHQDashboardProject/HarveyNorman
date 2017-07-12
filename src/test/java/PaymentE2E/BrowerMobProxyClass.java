package PaymentE2E;

import java.io.File;
import java.io.IOException;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BrowerMobProxyClass {

	//System.out.println("Testing the Browser mob proxy services and creating HAR file for performance testing...");

	String browserPath = "C:\\Users\\2944\\Java\\Browser\\chromedriver_win32\\chromedriver.exe";
	String harFileName = "C:/Users/2944/workspace_luna/HarveyNorman/HARFile/HarveyNormanHAR.har";
	String baseURL = "https://www.harveynorman.com.au";
	WebDriver driver;
	
	BrowserMobProxy proxyBMP;


	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		
		//start the BMP proxy
		proxyBMP = new BrowserMobProxyServer();
		proxyBMP.start(0);
		
		//get the selenium proxy object from "org.openqa.selenium.Proxy" package
		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxyBMP);		
		
		//configure it as a desired capability
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.PROXY, seleniumProxy);
		
		//Set chrome driver system property.
		System.setProperty("webdriver.chrome.driver", browserPath);
		driver = new ChromeDriver(cap);
		
		//enable more detailed HAR capture, if desired(see captureType for the complete list.)
		proxyBMP.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
		
		//create a new HAR with the label "HarveyNorman.com".
		proxyBMP.newHar("HarveyNorman.com");
		
		//launch the application
		driver.get(baseURL);
		
	}

	@Test
	public void testHTTPRequestResponse(){
		
		System.out.println("Harvey Norman Home page..");
	    WebElement searchBox = driver.findElement(By.id("search"));
		explicitWaitWebElement(searchBox).sendKeys("iphone");
		
		driver.findElement(By.xpath("//form[@id='search_form']/button")).click();
		driver.findElement(By.xpath("//div[@id='category-grid']/div[1]/div[2]/div/div/div[3]/a")).click();
		driver.findElement(By.id("btn-add-to-cart")).click();
		System.out.println("Iphone added to cart");
	}
	
	@AfterClass
	public void tearDown(){
		//get the HAR data
		Har harObj = proxyBMP.getHar();
		
		//write HAR Data in a file
		File harFile = new File(harFileName);
		try{
			harObj.writeTo(harFile);
		}catch(IOException ioex){
			System.out.println(ioex.toString());
			System.out.println("Could not find a file' " + harFileName +"'");
		}
		
		if(driver!=null)
		{
			proxyBMP.stop();
			driver.quit();
		}
		
	}

	//Method for explicit wait - to resolve the Syncronization issue.
			public WebElement explicitWaitWebElement(WebElement webelement) {

				WebDriverWait wait = new WebDriverWait(driver, 50);
				WebElement webelmnt = wait.until(ExpectedConditions.visibilityOf(webelement));
				return  webelmnt;
			};
}
