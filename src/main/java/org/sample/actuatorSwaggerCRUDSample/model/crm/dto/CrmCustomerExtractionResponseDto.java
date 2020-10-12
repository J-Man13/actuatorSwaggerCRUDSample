package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmCustomer;

public class CrmCustomerExtractionResponseDto {
    private CrmCustomer crmCustomer;


    public CrmCustomerExtractionResponseDto(CrmCustomer crmCustomer) {
        this.crmCustomer = crmCustomer;
    }

    public CrmCustomer getCrmCustomer() {
        return crmCustomer;
    }

    public void setCrmCustomer(CrmCustomer crmCustomer) {
        this.crmCustomer = crmCustomer;
    }
}
