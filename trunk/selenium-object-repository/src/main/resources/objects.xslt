<?xml version='1.0' encoding='windows-1252'?>
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<!-- Project: CORENA IETP -->
<!-- $Id: ExtractTerms_S1000DIssue4.0.xsl 104344 2012-04-05 18:23:30Z map 
	$ -->
<!-- Copyright: (c) 2002-2012, CORENA NORGE AS. All rights reserved. -->
<!-- CORENA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. -->
<!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

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
			<xsl:for-each select="sel-objects/object">
				<xsl:element name="bean">
					<xsl:attribute name="id">
					<xsl:value-of select="@id"></xsl:value-of>
				</xsl:attribute>
					<xsl:attribute name="class">
					<xsl:value-of select="$package.path" />.<xsl:value-of select="@type" />
				</xsl:attribute>
					<xsl:choose>
						<xsl:when test="location">
							<xsl:element name="property">
								<xsl:attribute name="name">elementXPath</xsl:attribute>
								<xsl:attribute name="value"><xsl:value-of
									select="location" /></xsl:attribute>
							</xsl:element>
							<property name="provider" ref="seleniumProvider" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:for-each select="component">
								<xsl:element name="property">
									<xsl:attribute name="name">
									<xsl:value-of select="@name" />
								</xsl:attribute>
									<xsl:attribute name="ref">
									<xsl:value-of select="@ref" />
								</xsl:attribute>
								</xsl:element>
							</xsl:for-each>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:element>
			</xsl:for-each>
		</beans>
	</xsl:template>
</xsl:stylesheet>