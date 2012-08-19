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

import java.util.Map;

/**
 * @author Suren Rodrigo
 * @version 1.1
 */
public class CommonPageObject {

	private Map<String, BaseElement> objectElementMap;
	private String objectName;

	public void setObjectElementMap(Map<String, BaseElement> objectElementMap) {
		this.objectElementMap = objectElementMap;
	}

	public Map<String, BaseElement> getObjectElementMap() {
		return objectElementMap;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	public CommonPageObject getInstance() {
		return this;
	}

}
