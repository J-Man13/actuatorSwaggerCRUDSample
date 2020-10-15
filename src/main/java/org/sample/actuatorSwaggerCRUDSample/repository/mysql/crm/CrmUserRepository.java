package org.sample.actuatorSwaggerCRUDSample.repository.mysql.crm;


import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrmUserRepository extends CrudRepository<CrmUserEntity, Long> {
    Optional<CrmUserEntity> findByLogin(String login);
}
