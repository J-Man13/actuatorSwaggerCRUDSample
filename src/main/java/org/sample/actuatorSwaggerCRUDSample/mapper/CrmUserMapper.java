package org.sample.actuatorSwaggerCRUDSample.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.crm.business.CrmUserRole;
import org.sample.actuatorSwaggerCRUDSample.model.crm.dto.CrmUserAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserEntity;
import org.sample.actuatorSwaggerCRUDSample.model.mysql.crm.entity.CrmUserRoleEntity;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrmUserMapper {
    CrmUser crmUserEntityToCrmUser(CrmUserEntity crmUserEntity);
    CrmUser crmUserAdditionRequestDtoToCrmUser(CrmUserAdditionRequestDto crmUserAdditionRequestDto);
    CrmUserEntity crmUserToCrmUserEntity(CrmUser crmUser);
    CrmUserRoleEntity crmUserRoleToCrmUserRoleEntity(CrmUserRole crmUserRole);
    CrmUserRole crmUserRoleEntityToCrmUserRole(CrmUserRoleEntity crmUserRoleEntity);
}
