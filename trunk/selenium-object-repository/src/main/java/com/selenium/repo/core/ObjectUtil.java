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

import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Suren Rodrigo
 * @version 1.1
 * @param <T>
 *            - Class to be obtained from repository
 * 
 */
public final class ObjectUtil<T> {

	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"ObjectRepo.xml");

	private ObjectUtil() {

	}

	public static <T> T getObject(Class t) throws Exception {
		String className = t.getName();
		Properties prop = new Properties();
		prop.load(ObjectUtil.class
				.getResourceAsStream("/keyclassmap.properties"));
		String beanKey = prop.getProperty(className);
		return (T) context.getBean(beanKey);
	}

}
