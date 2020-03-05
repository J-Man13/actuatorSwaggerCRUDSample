package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.CrmUserDao;

import java.util.List;

public interface ICrmUserService {
    CrmUserDao update(CrmUserDao crmUserDao);
    CrmUserDao save(CrmUserDao crmUserDao);
    CrmUserDao findById(String id);
    List<CrmUserDao> findByName(String name);
}
