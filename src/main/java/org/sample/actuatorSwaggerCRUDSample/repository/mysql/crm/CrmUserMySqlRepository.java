package org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserMySqlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmUserMySqlRepository extends CrudRepository<CrmUserMySqlEntity, Long> {
}
