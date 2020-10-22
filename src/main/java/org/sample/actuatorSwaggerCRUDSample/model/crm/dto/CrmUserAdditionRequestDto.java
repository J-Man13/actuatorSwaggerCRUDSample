package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class CrmUserAdditionRequestDto {
    @NotBlank(message = "CRM_USER_ADDITION_REQUEST_DTO_FIRSTNAME_IS_BLANK")
    private String firstname;
    @NotBlank(message = "CRM_USER_ADDITION_REQUEST_DTO_LASTNAME_IS_BLANK")
    private String lastname;
    @NotBlank(message = "CRM_USER_ADDITION_REQUEST_DTO_LOGIN_IS_BLANK")
    private String login;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$",message = "CRM_USER_ADDITION_REQUEST_DTO_PASSWORD_REGEX_VIOLATION")
    private String password;

    public CrmUserAdditionRequestDto() {

    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
