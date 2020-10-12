package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmCustomer;

import java.util.List;

public class CrmCustomerByNameExtractionResponseDto {
    private List<CrmCustomer> crmCustomerList;

    public CrmCustomerByNameExtractionResponseDto(List<CrmCustomer> crmCustomerList) {
        this.crmCustomerList = crmCustomerList;
    }

    public List<CrmCustomer> getCrmCustomerList() {
        return crmCustomerList;
    }

    public void setCrmCustomerList(List<CrmCustomer> crmCustomerList) {
        this.crmCustomerList = crmCustomerList;
    }
}
