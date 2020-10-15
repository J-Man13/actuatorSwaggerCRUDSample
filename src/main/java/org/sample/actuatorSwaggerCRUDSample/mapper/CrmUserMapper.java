package org.sample.actuatorSwaggerCRUDSample.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserEntity;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrmUserMapper {
    CrmUser crmUserEntityToCrmUser(CrmUserEntity crmUserEntity);
    CrmUserEntity crmUserToCrmUserEntity(CrmUser crmUser);
}
