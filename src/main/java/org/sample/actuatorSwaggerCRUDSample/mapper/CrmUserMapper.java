package org.sample.actuatorSwaggerCRUDSample.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserUpdateRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrmUserMapper {
    CrmUser crmUserAdditionRequestDtoToCrmUser(CrmUserAdditionRequestDto crmUserAdditionRequestDto);
    CrmUserMongoDocument crmUserToCrmUserMongoDocument(CrmUser crmUser);
    CrmUser crmUserMongoDocumentToCrmUser(CrmUserMongoDocument crmUserMongoDocument);
    List<CrmUser> crmUserMongoDocumentListToCrmUserList(List<CrmUserMongoDocument> crmUserMongoDocument);
    CrmUser updateCrmUserByCrmUserUpdateRequestDto(@MappingTarget CrmUser deliveryAddress, CrmUserUpdateRequestDto crmUserUpdateRequestDto);
}
