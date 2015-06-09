package com.testing.mySampleProgram;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;

import com.testing.mySampleProgram.Utils.Constants;
import com.testing.mySampleProgram.Utils.Utility;
import com.testing.mySampleProgram.Utils.Xls_Reader;

public class TestBase {
	public static Properties prop;
	public static WebDriver driver;
	public static DesiredCapabilities cap=null;
	public Logger APPLICATION_LOG = null;//Logger.getLogger("devpinoyLogger");
	
	
	public void initLogs(Class<?> class1){

		FileAppender appender = new FileAppender();
		// configure the appender here, with file location, etc
		appender.setFile(System.getProperty("user.dir")+"//target//reports//"+CustomListener.resultFolderName+"//"+class1.getName()+".log");
		appender.setLayout(new PatternLayout("%d %-5p [%c{1}] %m%n"));
		appender.setAppend(false);
		appender.activateOptions();

		APPLICATION_LOG = Logger.getLogger(class1);
		APPLICATION_LOG.setLevel(Level.DEBUG);
		APPLICATION_LOG.addAppender(appender);
	}
	
	
	public static void init(){
		if(prop == null){
			String path=System.getProperty("user.dir")+"\\src\\test\\resources\\project.properties";
			
			 prop = new Properties();
			try {
				FileInputStream fs = new FileInputStream(path);
				prop.load(fs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void validateRunmodes(String testName,String suiteName,String dataRunmode){
		APPLICATION_LOG.debug("Validating runmode for "+testName+" in suite "+ suiteName);
		init();
		//suite runmode
		boolean suiteRunmode=Utility.isSuiteRunnable(suiteName, new Xls_Reader(prop.getProperty("xlsFileLocation")+"Suite.xlsx"));
		boolean testRunmode=Utility.isTestCaseRunnable(testName, new Xls_Reader(prop.getProperty("xlsFileLocation")+suiteName+".xlsx"));
		boolean dataSetRunmode=false;
		if(dataRunmode.equals(Constants.RUNMODE_YES))
			dataSetRunmode=true;
		
		if(!(suiteRunmode && testRunmode && dataSetRunmode)){
			APPLICATION_LOG.debug("Skipping the test "+testName+" inside the suite "+ suiteName);
			throw new SkipException("Skipping the test "+testName+" inside the suite "+ suiteName);
		}
		
	}

	
	//-----Generic Function
	public void openBrowser(String browserType) throws Exception{
	
	
		if(browserType.equalsIgnoreCase("firefox")){
			cap = DesiredCapabilities.firefox();
			cap.setBrowserName(browserType);
			cap.setPlatform(Platform.ANY);
		}else if(browserType.equalsIgnoreCase("chrome")){
			cap = DesiredCapabilities.chrome();
			cap.setBrowserName("chrome");  // we can write chrome,iexplore
			cap.setPlatform(Platform.ANY);
		}else if(browserType.equals("iexplore")){
			cap = DesiredCapabilities.internetExplorer();
			cap.setBrowserName("iexplore");  // we can write chrome,iexplore
			cap.setPlatform(Platform.WINDOWS);
		}
		
		cap.setCapability("_important", "2");
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	
	public void navigate(String browserType, String URL) throws Exception{
		openBrowser(browserType);
		driver.get(URL);
	}
	
	//click on the element
	public void click(String identifier){
		try{
		if(identifier.endsWith("_xpath"))
			driver.findElement(By.xpath(prop.getProperty(identifier))).click();
		else if(identifier.endsWith("_id"))
			driver.findElement(By.id(prop.getProperty(identifier))).click();
		else if(identifier.endsWith("_name"))
			driver.findElement(By.name(prop.getProperty(identifier))).click();
		}catch(NoSuchElementException e){
			Assert.fail("Element not found - "+identifier);
		}

	}
	
	
	//enter text
	public void enterText(String identifier,String data){
		try{
		if(identifier.endsWith("_xpath"))
			driver.findElement(By.xpath(prop.getProperty(identifier))).sendKeys(data);
		else if(identifier.endsWith("_id"))
			driver.findElement(By.id(prop.getProperty(identifier))).sendKeys(data);
		else if(identifier.endsWith("_name"))
			driver.findElement(By.name(prop.getProperty(identifier))).sendKeys(data);
		}catch(NoSuchElementException e){
			Assert.fail("Element not found - "+identifier);
		}
	}
	
	
	public boolean verifyTitle(String expectedTitle){
		String actualTitle=driver.getTitle();
		if(expectedTitle.equals(actualTitle))
			return true;
		else
			return false;
	}
	
	
	//Verify if Element present
	public boolean isElementPresent(String identifier){
		int size=0;
		if(identifier.endsWith("_xpath"))
			size = driver.findElements(By.xpath(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_id"))
			size = driver.findElements(By.id(prop.getProperty(identifier))).size();
		else if(identifier.endsWith("_name"))
			size = driver.findElements(By.name(prop.getProperty(identifier))).size();
		else // not in prop file
			size=driver.findElements(By.xpath(identifier)).size();
		
		if(size>0)
			return true;
		else
			return false;
	}
	
	//Return the text of runtime object
	public String getText(String identifier){
		String  text="";
		if(identifier.endsWith("_xpath"))
			text = driver.findElement(By.xpath(prop.getProperty(identifier))).getText();
		else if(identifier.endsWith("_id"))
			text = driver.findElement(By.id(prop.getProperty(identifier))).getText();
		else if(identifier.endsWith("_name"))
			text = driver.findElement(By.name(prop.getProperty(identifier))).getText();
		
		return text;
		
	}
	
	public void quit(){
		if(driver!=null){
			driver.quit();
			driver=null;
		}
	}
	
	
	/*****************Application specific functions
	 * @throws Exception *******************/
	
	public void doLogin(String browserType,String URL,String username,String password) throws Exception{
		navigate(browserType,URL);
		click("signIn_xpath");
		enterText("Ebayusername_xpath", username);
		enterText("Ebaypassword_xpath", password);
		click("EbaySignInBttn_xpath");
	}
	
/*	public void doDefaultLogin(String browser){
		doLogin(browser, prop.getProperty("defaultUsername"), prop.getProperty("defaultPassword"));
	}*/
	
	

}
