package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;

public interface ICrmUserService {
    CrmUser findUserByLogin(String login);
}
