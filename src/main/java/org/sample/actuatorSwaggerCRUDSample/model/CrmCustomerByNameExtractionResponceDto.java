package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.List;

public class CrmCustomerByNameExtractionResponceDto {
    private List<CrmCustomer> crmCustomerList;

    public CrmCustomerByNameExtractionResponceDto(List<CrmCustomer> crmCustomerList) {
        this.crmCustomerList = crmCustomerList;
    }

    public List<CrmCustomer> getCrmCustomerList() {
        return crmCustomerList;
    }

    public void setCrmCustomerList(List<CrmCustomer> crmCustomerList) {
        this.crmCustomerList = crmCustomerList;
    }
}
