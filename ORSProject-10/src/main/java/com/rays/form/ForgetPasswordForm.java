package com.rays.form;

import javax.validation.constraints.NotEmpty;

import com.rays.common.BaseForm;

public class ForgetPasswordForm extends BaseForm {

	@NotEmpty(message = "Login Id is required")
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}