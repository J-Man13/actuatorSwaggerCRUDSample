package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmUserAdditionResponceDto {
    private CrmUser crmUser;

    public CrmUserAdditionResponceDto(CrmUser crmUser) {
        this.crmUser = crmUser;
    }

    public CrmUser getCrmUser() {
        return crmUser;
    }

    public void setCrmUser(CrmUser crmUser) {
        this.crmUser = crmUser;
    }
}
