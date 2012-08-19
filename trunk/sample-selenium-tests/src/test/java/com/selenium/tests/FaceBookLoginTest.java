package com.selenium.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.selenium.repo.core.ObjectUtil;
import com.selenium.repo.core.basetest.SeleniumBaseTestCase;
import com.selenium.repo.objects.FaceBookLoginDialog;
import com.selenium.repo.objects.SeleniumPanel;
public class FaceBookLoginTest extends SeleniumBaseTestCase{

    FaceBookLoginDialog faceBookLogin;
	@Test
	public void testLogin() throws Exception {
		faceBookLogin = ObjectUtil.getObject(FaceBookLoginDialog.class);
		faceBookLogin.getUserName().sendKeys("surenwork@gmail.com");
		faceBookLogin.getPassword().sendKeys("Sdsksklskd");
		faceBookLogin.getLoginButton().click();
		SeleniumPanel errorPanel = ObjectUtil.getObject(SeleniumPanel.class);
		driver.waitForElementToLoad(errorPanel);
		assertTrue(selenium.isTextPresent("Please re-enter your password"));
		
	}

}
