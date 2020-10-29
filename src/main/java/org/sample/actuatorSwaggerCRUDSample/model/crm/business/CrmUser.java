package org.sample.actuatorSwaggerCRUDSample.model.crm.business;

import java.time.LocalDateTime;
import java.util.List;

public class CrmUser {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String cryptedPassword;
    private LocalDateTime registrationDate;

    private List<CrmUserRole> roles;

    public CrmUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCryptedPassword() {
        return cryptedPassword;
    }

    public void setCryptedPassword(String cryptedPassword) {
        this.cryptedPassword = cryptedPassword;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<CrmUserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<CrmUserRole> roles) {
        this.roles = roles;
    }
}
