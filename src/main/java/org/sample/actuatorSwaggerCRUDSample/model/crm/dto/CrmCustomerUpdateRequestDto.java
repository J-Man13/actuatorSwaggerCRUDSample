package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import javax.validation.constraints.NotBlank;

public class CrmCustomerUpdateRequestDto {
    @NotBlank(message = "CRM_CUSTOMER_UPDATE_REQUEST_DTO_ID_IS_BLANK")
    private String id;
    private  String name;
    private  String surname;

    public CrmCustomerUpdateRequestDto() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
