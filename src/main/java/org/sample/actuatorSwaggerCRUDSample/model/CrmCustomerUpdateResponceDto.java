package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmCustomerUpdateResponceDto {
    private CrmCustomer crmCustomer;

    public CrmCustomerUpdateResponceDto(CrmCustomer crmCustomer) {
        this.crmCustomer = crmCustomer;
    }

    public CrmCustomer getCrmCustomer() {
        return crmCustomer;
    }

    public void setCrmCustomer(CrmCustomer crmCustomer) {
        this.crmCustomer = crmCustomer;
    }
}
