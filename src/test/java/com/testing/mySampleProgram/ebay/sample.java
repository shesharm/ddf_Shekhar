package com.testing.mySampleProgram.ebay;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class sample {

public static WebDriver driver;
	
	@Test
		public void runit(){
		System.out.println("my test ");
		driver = new FirefoxDriver();
		}

}
