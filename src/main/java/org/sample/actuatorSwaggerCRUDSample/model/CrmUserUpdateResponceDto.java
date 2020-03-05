package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmUserUpdateResponceDto {
    private CrmUserDao crmUserDao;

    public CrmUserUpdateResponceDto(CrmUserDao crmUserDao) {
        this.crmUserDao = crmUserDao;
    }

    public CrmUserDao getCrmUserDao() {
        return crmUserDao;
    }

    public void setCrmUserDao(CrmUserDao crmUserDao) {
        this.crmUserDao = crmUserDao;
    }
}
