package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmClientExtractionResponceDto {
    private CrmClient crmClient;


    public CrmClientExtractionResponceDto(CrmClient crmClient) {
        this.crmClient = crmClient;
    }

    public CrmClient getCrmClient() {
        return crmClient;
    }

    public void setCrmClient(CrmClient crmClient) {
        this.crmClient = crmClient;
    }
}
