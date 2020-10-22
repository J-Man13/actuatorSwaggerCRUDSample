package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;



import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CrmCustomerAdditionRequestDto implements Serializable {
    @NotBlank(message = "CRM_CUSTOMER_ADDITION_REQUEST_DTO_NAME_IS_BLANK")
    private String name;

    @NotBlank(message = "CRM_CUSTOMER_ADDITION_REQUEST_DTO_SURNAME_IS_BLANK")
    private String surname;

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
