package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.CrmClient;

import java.util.List;

public interface ICrmClientService {
    CrmClient update(CrmClient crmClient);
    CrmClient save(CrmClient crmClient);
    CrmClient findById(String id);
    List<CrmClient> findByName(String name);
}
