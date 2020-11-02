package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;

public interface ICrmUserService {
    CrmUser save(CrmUser crmUser,String unencryptedPassword);
    String loginWithTokenInReturn(String login,String password);
}
