package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.CrmUserDao;

public interface ICrmUserService {
    CrmUserDao save(CrmUserDao crmUserDao);
    CrmUserDao findById(String id);
}
