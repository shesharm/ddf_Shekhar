package com.testing.mySampleProgram.ebay;

import java.util.Hashtable;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testing.mySampleProgram.TestBase;
import com.testing.mySampleProgram.Utils.Constants;
import com.testing.mySampleProgram.Utils.ErrorUtil;
import com.testing.mySampleProgram.Utils.TestDataProvider;

public class TestLoginEbay extends TestBase{
	
	@BeforeTest
	public void initLogs(){
		initLogs(this.getClass());
		init();
	}
	
	@Test(dataProviderClass=TestDataProvider.class,dataProvider="suiteEbayDataProvider")
	public void testloginEbay(Hashtable<String,String> table){
		
		APPLICATION_LOG.debug("Executing Login scenarion for Ebay - login_Ebay");
		validateRunmodes("TestLoginEbay", Constants.FIRST_SUITE, table.get("Runmode"));
		try{
			
			doLogin(prop.getProperty("browserType"), table.get("URL"), table.get("Username"), table.get("Password"));
		}catch(Throwable t){
			System.out.println(t.getMessage());
			ErrorUtil.addVerificationFailure(t);
		}
		
		
	}
	
	@AfterTest
	public void quit(){
		driver.quit();
	}

}
