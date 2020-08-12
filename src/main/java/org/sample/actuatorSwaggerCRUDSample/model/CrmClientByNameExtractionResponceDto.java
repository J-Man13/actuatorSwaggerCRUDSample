package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.List;

public class CrmClientByNameExtractionResponceDto {
    private List<CrmClient> crmClientList;

    public CrmClientByNameExtractionResponceDto(List<CrmClient> crmClientList) {
        this.crmClientList = crmClientList;
    }

    public List<CrmClient> getCrmClientList() {
        return crmClientList;
    }

    public void setCrmClientList(List<CrmClient> crmClientList) {
        this.crmClientList = crmClientList;
    }
}
