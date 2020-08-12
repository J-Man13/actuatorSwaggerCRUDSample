package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmClientAdditionResponceDto {
    private CrmClient crmClient;

    public CrmClientAdditionResponceDto(CrmClient crmClient) {
        this.crmClient = crmClient;
    }

    public CrmClient getCrmClient() {
        return crmClient;
    }

    public void setCrmClient(CrmClient crmClient) {
        this.crmClient = crmClient;
    }
}
