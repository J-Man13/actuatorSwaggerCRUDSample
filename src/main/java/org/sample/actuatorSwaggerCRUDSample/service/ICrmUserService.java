package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.CrmUser;

import java.util.List;

public interface ICrmUserService {
    CrmUser update(CrmUser crmUser);
    CrmUser save(CrmUser crmUser);
    CrmUser findById(String id);
    List<CrmUser> findByName(String name);
}
