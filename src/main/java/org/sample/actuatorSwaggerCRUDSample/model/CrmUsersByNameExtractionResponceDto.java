package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.List;

public class CrmUsersByNameExtractionResponceDto {
    private List<CrmUserDao> crmUserDaoList;

    public CrmUsersByNameExtractionResponceDto(List<CrmUserDao> crmUserDaoList) {
        this.crmUserDaoList = crmUserDaoList;
    }

    public List<CrmUserDao> getCrmUserDaoList() {
        return crmUserDaoList;
    }

    public void setCrmUserDaoList(List<CrmUserDao> crmUserDaoList) {
        this.crmUserDaoList = crmUserDaoList;
    }
}
