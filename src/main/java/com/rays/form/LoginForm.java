package com.rays.form;

import com.rays.common.BaseForm;

import javax.validation.constraints.NotEmpty;

public class LoginForm extends BaseForm {

    @NotEmpty(message = "Login Id can not be empty")
    private String loginId;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
