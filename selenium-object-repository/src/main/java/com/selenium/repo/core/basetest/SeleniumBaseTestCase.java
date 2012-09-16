/*
 * Copyright (c) 2012 Suren Rodrigo
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 *  
 *             
 */

/*
 * Last Committed Details
 * $Id$
 */
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
