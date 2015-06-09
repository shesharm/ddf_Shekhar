package com.testing.mySampleProgram.Utils;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

import com.testing.mySampleProgram.TestBase;


public class TestDataProvider {
	


	@DataProvider(name="suiteEbayDataProvider")
	public static Object[][] getDataSuiteEbay(Method m){
		TestBase.init();
		System.out.println(TestBase.prop.getProperty("xlsFileLocation"));
		System.out.println(Constants.FIRST_SUITE);
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.FIRST_SUITE+".xlsx");
		return Utility.getData(m.getName(), xls1);
		
	}
	
	@DataProvider(name="suiteGoIbiboDataProvider")
	public static Object[][] getDataSuiteB(Method m){
		TestBase.init();
		Xls_Reader xls1 = new Xls_Reader(TestBase.prop.getProperty("xlsFileLocation")+Constants.SECOND_SUITE+".xlsx");

		return Utility.getData(m.getName(), xls1);
	}
	

}
