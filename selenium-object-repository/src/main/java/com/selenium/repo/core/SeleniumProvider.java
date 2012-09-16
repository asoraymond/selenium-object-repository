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
package com.selenium.repo.core;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Suren Rodrigo
 * @version 1.1
 */
public class SeleniumProvider {

	private WebDriver driver;
	private String baseURL;
	private Selenium selenium;
	private BrowserDriver browserDriver;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SeleniumProvider.class);

	public SeleniumProvider(WebDriver driver, String baseURL) {
		this.driver = driver;
		this.baseURL = baseURL;
		browserDriver = new BrowserDriver(driver, baseURL);
		this.selenium = browserDriver.getSelenium();
	}

	public SeleniumProvider(String gridUrl, String baseUrl) {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		this.baseURL = baseUrl;
		try {
			this.driver = new RemoteWebDriver(new URL(gridUrl), capabilities);
		} catch (MalformedURLException e) {
			LOGGER.error(e.getMessage());
		}

		browserDriver = new BrowserDriver(driver, this.baseURL);
		this.selenium = browserDriver.getSelenium();
	}

	public String getBaseURL() {
		return baseURL;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public Selenium getSelenium() {
		return selenium;
	}

	public BrowserDriver getBrowserDriver() {
		return browserDriver;
	}

	public void openUrl() {
		browserDriver.get(baseURL);
	}

}
