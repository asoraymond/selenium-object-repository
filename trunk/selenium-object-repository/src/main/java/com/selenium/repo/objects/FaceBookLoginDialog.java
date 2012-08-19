package com.selenium.repo.objects;

import com.selenium.repo.core.BaseElement;
import com.selenium.repo.objects.SeleniumTextBox;
import com.selenium.repo.objects.SeleniumButton;


public class FaceBookLoginDialog extends BaseElement {
private SeleniumTextBox userName;
private SeleniumTextBox password;
private SeleniumButton loginButton;

public FaceBookLoginDialog(){
	super();
}
public SeleniumTextBox getUserName(){
	return userName;
}
public void setUserName(SeleniumTextBox userName){
	this.userName = userName;
}
public SeleniumTextBox getPassword(){
	return password;
}
public void setPassword(SeleniumTextBox password){
	this.password = password;
}
public SeleniumButton getLoginButton(){
	return loginButton;
}
public void setLoginButton(SeleniumButton loginButton){
	this.loginButton = loginButton;
}


}