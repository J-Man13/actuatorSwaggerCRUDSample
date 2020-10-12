package org.sample.actuatorSwaggerCRUDSample.service;

import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmCustomer;

import java.util.List;

public interface ICrmCustomerService {
    CrmCustomer update(CrmCustomer crmCustomer);
    CrmCustomer save(CrmCustomer crmCustomer);
    CrmCustomer findById(String id);
    List<CrmCustomer> findByName(String name);
}
