/*
 * Copyright (c) 2012 Suren Rodrigo
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 *  
 *             
 */

package com.selenium.repo.core;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;

/**
 * Implement a instance of the WebDriver interface and abstracts all the methods provided by the driver
 * @author Suren Rodrigo
 * @version 1.1
 */
public class BrowserDriver implements WebDriver {

    private static final int MAX_TIME_OUT_FOR_ELEMENT = 20;
    private WebDriver driver;
    private Selenium selenium;
    public static final String MAX_TIME_OUT = "1000000000";

    public BrowserDriver(WebDriver driver, String baseURL) {
        this.driver = driver;
        this.selenium = new WebDriverBackedSelenium(driver, baseURL);
    }

    public Selenium getSelenium() {
        return selenium;
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public WebElement findElement(By arg0) {
        return driver.findElement(arg0);
    }

    @Override
    public List<WebElement> findElements(By arg0) {
        return driver.findElements(arg0);
    }

    @Override
    public void get(String arg0) {
        selenium.open(arg0);
        selenium.waitForPageToLoad(MAX_TIME_OUT);

    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public void quit() {
        selenium.stop();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    public BaseElement waitForElementToLoad(final BaseElement element) {
        (new WebDriverWait(driver, MAX_TIME_OUT_FOR_ELEMENT)).until(new ExpectedCondition<WebElement>() {

            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(element.getElementXPath()));
            }
        });
        return element;
    }
}
