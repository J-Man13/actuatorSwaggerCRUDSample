package org.sample.actuatorSwaggerCRUDSample.model.crm.business;

import java.time.LocalDateTime;

public class CrmUser {
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private String cryptedPassword;
    private LocalDateTime registrationDate;

    public CrmUser() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
