package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmUserAdditionResponceDto {
    private CrmUserDao crmUserDao;

    public CrmUserAdditionResponceDto(CrmUserDao crmUserDao) {
        this.crmUserDao = crmUserDao;
    }

    public CrmUserDao getCrmUserDao() {
        return crmUserDao;
    }

    public void setCrmUserDao(CrmUserDao crmUserDao) {
        this.crmUserDao = crmUserDao;
    }
}
