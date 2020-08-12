package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmCustomerExtractionResponceDto {
    private CrmCustomer crmCustomer;


    public CrmCustomerExtractionResponceDto(CrmCustomer crmCustomer) {
        this.crmCustomer = crmCustomer;
    }

    public CrmCustomer getCrmCustomer() {
        return crmCustomer;
    }

    public void setCrmCustomer(CrmCustomer crmCustomer) {
        this.crmCustomer = crmCustomer;
    }
}
