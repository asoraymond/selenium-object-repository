package com.selenium.repo.core.basetest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;

import com.selenium.repo.core.BrowserDriver;
import com.selenium.repo.core.ObjectUtil;
import com.selenium.repo.core.SeleniumProvider;
import com.thoughtworks.selenium.Selenium;

public class SeleniumBaseTestCase {

	static ApplicationContext context = null;
	private static SeleniumProvider seleniumProvider;
	protected static Selenium selenium;
	protected static BrowserDriver driver;

	@BeforeClass
	public static void setupBeforeClass() throws Exception {

		seleniumProvider =ObjectUtil.getObject(SeleniumProvider.class);
		seleniumProvider.openUrl();
		selenium = seleniumProvider.getSelenium();
		driver = seleniumProvider.getBrowserDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		driver.quit();
		
	}

}
