/*
 * Copyright (c) 2012 Suren Rodrigo
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 *  
 *             
 */

package com.selenium.repo.generator;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;

public final class XSLTTransformer {

	private XSLTTransformer() {

	}

	public static DOMSource createDomSource(File sourceFile,
			DocumentBuilderFactory factory) throws Exception {
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(sourceFile);

		return new DOMSource(document);
	}

	public static void transform(Source domSource, Source styleSource,
			Result resultStream, Map<String, Object> parameters)
			throws Exception {

		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer;
		if (styleSource != null) {
			transformer = tFactory.newTransformer(styleSource);
		} else {
			transformer = tFactory.newTransformer();
		}

		if (parameters != null) {
			for (Entry<String, Object> parameter : parameters.entrySet()) {
				transformer.setParameter(parameter.getKey(),
						parameter.getValue());
			}
		}

		transformer.transform(domSource, resultStream);
	}

	public static void transform(File domFile, File styleSheet, File resultFile)
			throws Exception {
		DOMSource domSource = createDomSource(domFile);
		StreamSource stylesource = new StreamSource(styleSheet);
		StreamResult resultStream = new StreamResult(resultFile);

		transform(domSource, stylesource, resultStream, null);
	}

	private static DOMSource createDomSource(File sourceFile) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		return createDomSource(sourceFile, factory);
	}

}
