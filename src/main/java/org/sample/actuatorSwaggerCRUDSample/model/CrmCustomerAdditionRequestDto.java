package org.sample.actuatorSwaggerCRUDSample.model;



import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CrmCustomerAdditionRequestDto implements Serializable {
    @NotBlank
    private String name;

    @NotBlank
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
