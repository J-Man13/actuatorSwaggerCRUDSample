package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


public class CrmUserAdditionRequestDto {
    @NotBlank(message = "CRM_USER_ADDITION_REQUEST_DTO_FIRST_NAME_IS_BLANK")
    private String firstName;
    @NotBlank(message = "CRM_USER_ADDITION_REQUEST_DTO_LAST_NAME_IS_BLANK")
    private String lastName;
    @NotBlank(message = "CRM_USER_ADDITION_REQUEST_DTO_LOGIN_IS_BLANK")
    private String login;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$",message = "CRM_USER_ADDITION_REQUEST_DTO_PASSWORD_REGEX_VIOLATION")
    private String password;

    public CrmUserAdditionRequestDto() {

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
