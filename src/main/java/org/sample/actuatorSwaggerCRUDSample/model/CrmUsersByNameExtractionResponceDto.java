package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.List;

public class CrmUsersByNameExtractionResponceDto {
    private List<CrmUser> crmUserList;

    public CrmUsersByNameExtractionResponceDto(List<CrmUser> crmUserList) {
        this.crmUserList = crmUserList;
    }

    public List<CrmUser> getCrmUserList() {
        return crmUserList;
    }

    public void setCrmUserList(List<CrmUser> crmUserList) {
        this.crmUserList = crmUserList;
    }
}
