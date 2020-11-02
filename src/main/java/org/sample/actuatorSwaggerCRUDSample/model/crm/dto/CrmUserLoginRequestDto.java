package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import javax.validation.constraints.NotBlank;

public class CrmUserLoginRequestDto {
    @NotBlank(message = "CRM_USER_LOGIN_REQUEST_DTO_LOGIN_IS_BLANK")
    private String login;

    @NotBlank(message = "CRM_USER_LOGIN_REQUEST_DTO_PASSWORD_IS_BLANK")
    private String password;

    public CrmUserLoginRequestDto() {
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
