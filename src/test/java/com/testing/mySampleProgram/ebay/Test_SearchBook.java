package com.testing.mySampleProgram.ebay;

import java.util.Hashtable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.testing.mySampleProgram.TestBase;
import com.testing.mySampleProgram.Utils.Constants;
import com.testing.mySampleProgram.Utils.ErrorUtil;
import com.testing.mySampleProgram.Utils.TestDataProvider;

public class Test_SearchBook extends TestBase{
	
	
	@BeforeClass
	public void initLogs(){
		initLogs(this.getClass());	
		//init();
		//openBrowser(prop.getProperty("browserType"));
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteEbayDataProvider")
	public void test_SearchBook(Hashtable<String,String> table){
		
		APPLICATION_LOG.debug("Executing Login scenarion for Ebay - Search Books");
		//doLogin(prop.getProperty("browserType"), table.get("URL"), table.get("Username"), table.get("Password"));
		driver.get("http://ebay.com");
		validateRunmodes("Test_SearchBook", Constants.FIRST_SUITE, table.get("Runmode"));
		try
		{
			//isElementPresent(prop.getProperty("Categories_id"));
			WebElement category = driver.findElement(By.id(prop.getProperty("Categories_id")));
			Select s = new Select(category);
			s.selectByVisibleText(table.get("Category"));
			//s.selectByIndex(4);
			click("EbaySearchBttn_xpath");
			//driver.findElement(By.xpath(".//*[@id='gh-btn']")).click();
			WebElement bookType = driver.findElement(By.xpath(prop.getProperty("BookType_xpath_part1")+table.get("BookType")+prop.getProperty("BookType_xpath_part2")));
			Actions act = new Actions(driver);
			act.moveToElement(bookType).build().perform();
			WebElement SubbookType = driver.findElement(By.xpath(prop.getProperty("SubBookType_xpath_part1")+table.get("SubBookType")+prop.getProperty("SubBookType_xpath_part2")));
			
			act.moveToElement(SubbookType).build().perform();
			act.click(SubbookType).build().perform();
			
		}catch(Throwable t)
		{
			System.out.println(t.getMessage());
			ErrorUtil.addVerificationFailure(t);
		}
		
	}
}
