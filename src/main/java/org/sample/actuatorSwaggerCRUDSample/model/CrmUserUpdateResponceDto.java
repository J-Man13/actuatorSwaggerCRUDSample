package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmUserUpdateResponceDto {
    private CrmUser crmUser;

    public CrmUserUpdateResponceDto(CrmUser crmUser) {
        this.crmUser = crmUser;
    }

    public CrmUser getCrmUser() {
        return crmUser;
    }

    public void setCrmUser(CrmUser crmUser) {
        this.crmUser = crmUser;
    }
}
