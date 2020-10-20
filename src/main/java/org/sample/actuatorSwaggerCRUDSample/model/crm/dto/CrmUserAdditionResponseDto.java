package org.sample.actuatorSwaggerCRUDSample.model.crm.dto;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;

public class CrmUserAdditionResponseDto {
    private CrmUser crmUser;

    public CrmUserAdditionResponseDto(CrmUser crmUser) {
        this.crmUser = crmUser;
    }

    public CrmUser getCrmUser() {
        return crmUser;
    }

    public void setCrmUser(CrmUser crmUser) {
        this.crmUser = crmUser;
    }
}
