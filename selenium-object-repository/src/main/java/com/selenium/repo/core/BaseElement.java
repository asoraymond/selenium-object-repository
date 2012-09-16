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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import com.thoughtworks.selenium.Selenium;

/**
 * Base element for selenium objects, I.e. all objects will extend from this
 * class
 * 
 * @author 99xcsro
 * @version 1.1
 * 
 */
public class BaseElement implements WebElement {

	private String elementXPath;
	private Selenium selenium;
	private BrowserDriver driver;
	private SeleniumProvider provider;

	public BaseElement() {

	}

	public WebElement getElement() {
		return provider.getDriver().findElement(By.xpath(elementXPath));
	}

	public void setElementXPath(String elementXPath) {
		this.elementXPath = elementXPath;
	}

	public String getElementXPath() {
		return elementXPath;
	}

	protected By getByObject() {
		return By.xpath(elementXPath);
	}

	@Override
	public void clear() {

	}

	@Override
	public void click() {

	}

	@Override
	public WebElement findElement(By arg0) {
		return null;
	}

	@Override
	public List<WebElement> findElements(By arg0) {
		return null;
	}

	@Override
	public String getAttribute(String arg0) {
		return null;
	}

	@Override
	public String getCssValue(String arg0) {
		return null;
	}

	@Override
	public Point getLocation() {
		return null;
	}

	@Override
	public Dimension getSize() {
		return null;
	}

	@Override
	public String getTagName() {
		return null;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public boolean isDisplayed() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void sendKeys(CharSequence... arg0) {

	}

	@Override
	public void submit() {

	}

	public Selenium getSelenium() {
		return selenium;
	}

	public void rightClick() {
		if (provider.getSelenium() != null) {
			provider.getSelenium().mouseDownRightAt(this.elementXPath, "");
			provider.getSelenium().mouseUpRightAt(this.elementXPath, "");
		}
	}

	public void seleniumClick() {
		provider.getSelenium().clickAt(elementXPath, "");
	}

	public BrowserDriver getDriver() {
		return driver;
	}

	public void setProvider(SeleniumProvider provider) {
		this.provider = provider;
		this.driver = provider.getBrowserDriver();
		this.selenium = provider.getSelenium();
	}

	public SeleniumProvider getProvider() {
		return provider;
	}

}
