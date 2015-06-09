package com.testing.mySampleProgram.ebay;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class RoughWork {
	
	@Test
	public void ebay(){
		
		WebDriver driver = new FirefoxDriver();
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://ebay.com");
		WebElement cat = driver.findElement(By.xpath(".//*[@id='gh-cat']"));
		Select s = new Select(cat);
		s.selectByVisibleText("Books");
		driver.findElement(By.xpath(".//*[@id='gh-btn']")).click();
		
		
	}

}
