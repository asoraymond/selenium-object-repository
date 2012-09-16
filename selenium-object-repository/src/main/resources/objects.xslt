<?xml version='1.0' encoding='UTF-8'?>
<!-- /* * Copyright (c) 2012 Suren Rodrigo * * /
	Permission is hereby granted, 
	free of charge, to any person obtaining a copy of this software and associated 
	documentation files (the "Software"), to deal in the Software without restriction, 
	including without limitation the rights to use, copy, modify, merge, publish, 
	distribute, sublicense, and/or sell copies of the Software, and to permit 
	persons to whom the Software is furnished to do so, subject to the following 
	conditions: * The above copyright notice and this permission notice shall 
	be included in all copies or substantial portions of the Software. * THE 
	SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
	INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR 
	A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR 
	COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
	IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION 
	WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. * * */
	
	/*
 * Last Committed Details
 * $Id:$
 */
	 -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context">

	<xsl:variable name="package.path">com.selenium.repo.objects</xsl:variable>
	<xsl:variable name="grid.url">
		<xsl:value-of select="/sel-objects/configurations/grid-url" />
	</xsl:variable>
	<xsl:variable name="target.browser.driver">
		<xsl:value-of select="/sel-objects/configurations/browser-driver" />
	</xsl:variable>
	<xsl:variable name="test.server.url">
		<xsl:value-of select="/sel-objects/configurations/test-url" />
	</xsl:variable>

	<xsl:variable name="key.map.class.path">
		<xsl:value-of select="keyclassmap.properties" />
	</xsl:variable>

	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />

	<xsl:template match="/">
		<beans xmlns="http://www.springframework.org/schema/beans"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
			xsi:schemaLocation="http://www.springframework.org/schema/beans 
						http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
						http://www.springframework.org/schema/context
						http://www.springframework.org/schema/context/spring-context-3.0.xsd">

			<context:property-placeholder location="classpath:config.properties" />

			<xsl:element name="bean">
				<xsl:attribute name="id">driver</xsl:attribute>
				<xsl:attribute name="class">org.openqa.selenium.ie.<xsl:value-of
					select="$target.browser.driver" />
		</xsl:attribute>
			</xsl:element>
			<bean id="seleniumCoreProperties"
				class="org.springframework.beans.factory.config.PropertiesFactoryBean">
				<property name="properties">
					<props>
						<prop key="locations">${main.key.class.map.file}</prop>
					</props>
				</property>
			</bean>
			<xsl:choose>
				<xsl:when test="$grid.url = ''">
					<bean id="seleniumProvider" class="com.selenium.repo.core.SeleniumProvider">
						<constructor-arg ref="driver" />
						<xsl:element name="constructor-arg">
							<xsl:attribute name="value">
					<xsl:value-of select="$test.server.url" />
				</xsl:attribute>
						</xsl:element>
					</bean>
				</xsl:when>
				<xsl:otherwise>
					<bean id="seleniumProvider" class="com.selenium.repo.core.SeleniumProvider">
						<xsl:element name="constructor-arg">
							<xsl:attribute name="value">
					<xsl:value-of select="$grid.url" />
				</xsl:attribute>
						</xsl:element>
						<xsl:element name="constructor-arg">
							<xsl:attribute name="value">
					<xsl:value-of select="$test.server.url" />
				</xsl:attribute>
						</xsl:element>
					</bean>
				</xsl:otherwise>
			</xsl:choose>
			<xsl:apply-templates />
		</beans>
	</xsl:template>
	
	<xsl:template match="configurations"></xsl:template>

	<xsl:template match="object">
		<xsl:element name="bean">
			<xsl:attribute name="id">
					<xsl:value-of select="@id"></xsl:value-of></xsl:attribute>
			<xsl:attribute name="class">
					<xsl:value-of select="$package.path" />.<xsl:value-of select="@type" />
				</xsl:attribute>
			<xsl:apply-templates />
			<property name="provider" ref="seleniumProvider" />
		</xsl:element>
	</xsl:template>

	<xsl:template match="object/location">
		<xsl:element name="property">
			<xsl:attribute name="name">elementXPath</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="text()" /></xsl:attribute>
		</xsl:element>
	</xsl:template>

	<xsl:template match="object/component">
		<xsl:element name="property">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
			<xsl:attribute name="ref"><xsl:value-of select="@ref" /></xsl:attribute>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>