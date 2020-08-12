package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmCustomerUpdateRequestDto {
    public String name;
    public String surname;

    public CrmCustomerUpdateRequestDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
