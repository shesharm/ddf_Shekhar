package com.testing.mySampleProgram.GoIbibo;

import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testing.mySampleProgram.TestBase;
import com.testing.mySampleProgram.Utils.Constants;
import com.testing.mySampleProgram.Utils.TestDataProvider;

public class TestGoIbibo extends TestBase{

	@BeforeTest
	public void initLogs(){
		initLogs(this.getClass());
		init();
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteGoIbiboDataProvider")
	public void testGoIbibo(Hashtable<String,String> table) throws Exception{
		APPLICATION_LOG.debug("Executing Login scenarion for Go Ibibo");
		validateRunmodes("TestGoIbibo", Constants.SECOND_SUITE, table.get("Runmode"));
		navigate(table.get("Browsertype"),table.get("URL"));
		List<WebElement> radioButtons = driver.findElements(By.xpath(prop.getProperty("TripWay_radiogroup_xpath")));
		System.out.println("Total radio "+radioButtons.size());
		if(radioButtons.size()>1){
			for(int i=0;i<radioButtons.size();i++){
				String tripway = radioButtons.get(i).getAttribute("id");
				if(tripway.contains((table.get("Trip_Way").replace(" ", "").toLowerCase())))
				radioButtons.get(i).click();
			}	
		}
		
		enterText("sourceFrom_xpath", table.get("Source"));
		enterText("DestTo_xpath", table.get("Destination"));
		
	}
}
