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
package com.selenium.repo.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.selenium.repo.core.Constants;

/**
 * @author Suren Rodrigo
 * @version 1.1
 */
public class ObjectGenerator {

	private static final String NEWLINE_METHOD_END = ";\r\n}\r\n";
	private Map<String, String> classList;
	private String projectBaseDir;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ObjectGenerator.class);

	public ObjectGenerator() {

	}

	public ObjectGenerator(String projectBaseDir)
			throws ParserConfigurationException, SAXException, IOException {
		this.projectBaseDir = projectBaseDir;

	}

	/**
	 * Generate the selenium objects after analyzing the context file
	 * 
	 * @throws Exception
	 */
	public void generateObjects() throws Exception {

		LOGGER.info("Class generation process initialized");
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document objectDefDoc = docBuilder.parse(new File(projectBaseDir
				+ Constants.OBJECT_DEF_FILE));
		classList = new HashMap<String, String>();
		LOGGER.info("Accessing bean list for analysis");
		NodeList beanList = objectDefDoc.getElementsByTagName("bean");
		LOGGER.info("Found {} beans for analysis", beanList.getLength());
		Properties prop = new Properties();

		for (int i = 0; i < beanList.getLength(); i++) {
			Node node = beanList.item(i);
			LOGGER.info("Node Type : " + node.getNodeType());
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element beanClass = (Element) node;
				String classToGenrate = beanClass.getAttribute("class");
				String className = classToGenrate.replaceFirst(
						Constants.GENERATED_OBJECT_PACKAGE_NAME + ".", "");

				String classId = beanClass.getAttribute("id");
				prop.setProperty(classToGenrate, classId);
				if (!classList.containsKey(beanClass.getAttribute("id"))) {
					classList.put(beanClass.getAttribute("id"), className);
				}
				if (classToGenrate
						.startsWith(Constants.GENERATED_OBJECT_PACKAGE_NAME
								+ ".")) {

					LOGGER.info("Attempting to generate class {} ", className);
					String classFileName = className + ".java";
					File fileToGenerate = new File(projectBaseDir
							+ Constants.REPO_PACKAGE_PATH + "/" + classFileName);
					LOGGER.info("Check if class {} already exists", className);
					if (!fileToGenerate.exists()) {
						LOGGER.info(
								"Class {} dosen't exist, proceeding with generation process",
								className);
						List<String> importStatment = new ArrayList<String>();
						StringBuffer propertyDef = new StringBuffer();
						StringBuffer methodDef = new StringBuffer();

						importStatment
								.add("import com.selenium.repo.core.SeleniumObject;\r\n");

						NodeList propertyList = beanClass
								.getElementsByTagName("property");
						for (int j = 0; j < propertyList.getLength(); j++) {
							Element property = (Element) propertyList.item(j);
							travaseProperty(importStatment, propertyDef,
									methodDef, property);
						}
						StringBuffer sourceCode = new StringBuffer();
						StringBuffer importList = new StringBuffer();
						for (String tempImport : importStatment) {
							importList.append(tempImport);
						}
						sourceCode.append("package "
								+ Constants.GENERATED_OBJECT_PACKAGE_NAME
								+ ";\r\n\r\n");
						sourceCode.append(importList.toString());
						sourceCode.append("\r\n\r\n");
						sourceCode.append("public class " + className
								+ " extends SeleniumObject {\r\n");
						sourceCode.append(propertyDef.toString());
						sourceCode.append("\r\n");
						sourceCode.append("public " + className
								+ "(){\r\n\tsuper();\r\n}\r\n");
						sourceCode.append(methodDef.toString());
						sourceCode.append("\r\n\r\n}");

						// write to the source file

						writeSourceFile(className, fileToGenerate, sourceCode);
					} else {
						LOGGER.info(
								"Class {} already exists, skipping generation process ",
								className);
					}

				}
			}
		}

		LOGGER.info("Generating key, class properties map for easy classs retrival");
		generateKeyClassMapFile(prop);

	}

	private void generateKeyClassMapFile(Properties prop) throws Exception {
		String keyClassMapFileName = PropertyUtil.readValue(new File(
				projectBaseDir + "/src/main/resources/config.properties"),
				"main.key.class.map.file");
		String keyClassMapFile = projectBaseDir + "/" + keyClassMapFileName;
		FileOutputStream fileStream = null;
		try {
			fileStream = new FileOutputStream(keyClassMapFile);
			prop.store(fileStream, null);
		} finally {
			if (fileStream != null) {
				fileStream.close();
			}
		}
	}

	private void writeSourceFile(String className, File fileToGenerate,
			StringBuffer sourceCode) throws Exception {
		BufferedWriter bufferWriter = null;
		try {
			if (fileToGenerate.createNewFile()) {
				FileWriter fileWriter = new FileWriter(fileToGenerate);
				bufferWriter = new BufferedWriter(fileWriter);
				bufferWriter.write(sourceCode.toString());
			}
		} finally {
			if (bufferWriter != null) {
				bufferWriter.close();
			}
		}
		LOGGER.info("Class {} generated sucessfully", className);
	}

	/**
	 * Travase a given property and build the class
	 * 
	 * @param importStatment
	 *            - import statements required for the class
	 * @param propertyDef
	 *            - property definitions
	 * @param methodDef
	 *            - method definitions
	 * @param property
	 *            - property that being analyzed
	 */
	private void travaseProperty(List<String> importStatment,
			StringBuffer propertyDef, StringBuffer methodDef, Element property) {
		String propertyName = property.getAttribute("name");
		String methodAppendString = createNameToAppendToMethod(propertyName);
		String setAditonalCode = "";
		if (property.hasAttribute("value")) {
			propertyDef.append("private String " + propertyName + ";\r\n");
			methodDef.append("public String get" + methodAppendString
					+ "(){\r\n\treturn " + propertyName + NEWLINE_METHOD_END);
			methodDef.append("public void set" + methodAppendString
					+ "(String " + propertyName + "){\r\n\tthis."
					+ propertyName + " = " + propertyName);

			if (propertyName.equals("elementXPath")) {
				methodDef
						.append(";\r\n\tsuper.setElementXPath(this.elementXPath)");
			}
			methodDef.append(NEWLINE_METHOD_END);
		} else if (property.hasAttribute("ref")) {
			String refClassName = classList.get(property.getAttribute("ref"));
			String importString = "";

			if (refClassName
					.equalsIgnoreCase("com.selenium.repo.core.SeleniumProvider")) {
				importString = "import " + refClassName + ";\r\n";
				refClassName = refClassName.replaceFirst(
						"com.selenium.repo.core.", "");
				setAditonalCode = ";\r\n\tsuper.setProvider(this.provider)";
			} else {

				importString = "import com.selenium.repo.objects."
						+ refClassName + ";\r\n";
			}
			if (!importStatment.contains(importString)) {
				importStatment.add(importString);
			}
			propertyDef.append("private " + refClassName + " " + propertyName
					+ ";\r\n");
			methodDef.append("public " + refClassName + " get"
					+ methodAppendString + "(){\r\n\treturn " + propertyName
					+ NEWLINE_METHOD_END);
			methodDef.append("public void set" + methodAppendString + "("
					+ refClassName + " " + propertyName + "){\r\n\tthis."
					+ propertyName + " = " + propertyName + setAditonalCode
					+ NEWLINE_METHOD_END);
		}
	}

	private String createNameToAppendToMethod(String propertyName) {
		char firstCharacter = propertyName.charAt(0);
		String nameWithoutFirstChar = propertyName.substring(1);
		String firstCharString = String.valueOf(firstCharacter).toUpperCase();
		return firstCharString + nameWithoutFirstChar;
	}

	public static void main(String[] args) {
		try {
			ObjectGenerator objectGen = new ObjectGenerator(args[0]);
			objectGen.generateObjectDefContext();

			objectGen.generateObjects();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
	}

	/**
	 * convert the object definiton file to a standard spring context file
	 * 
	 * @throws Exception
	 */
	private void generateObjectDefContext() throws Exception {

		LOGGER.info("Initilizing context file analysis");
		File objectDeffile = new File(System.getProperty("main.object.def"));
		File xsltFile = new File(projectBaseDir
				+ "/src/main/resources/objects.xslt");
		File outputFile = new File(projectBaseDir
				+ "/"
				+ PropertyUtil.readValue(new File(projectBaseDir
						+ "/src/main/resources/config.properties"),
						"main.object.def.context"));
		LOGGER.info("Object definition file : {} ", objectDeffile);
		LOGGER.info("Transformation file : {} ", xsltFile);
		LOGGER.info("Generated context file : {} ", outputFile);

		LOGGER.info("Transforming Objects and Generating Context file");

		XSLTTransformer.transform(objectDeffile, xsltFile, outputFile);
	}
}
