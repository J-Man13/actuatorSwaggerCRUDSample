package org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm;

import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmUserMySqlRepository extends CrudRepository<UserEntity, Long> {
}
