package org.sample.actuatorSwaggerCRUDSample.mapper;

import org.mapstruct.Mapper;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUser;
import org.sample.actuatorSwaggerCRUDSample.model.UserAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;

@Mapper(componentModel = "spring")
public interface CrmUserMapper {
    CrmUser userAdditionRequestDtoToCrmUser(UserAdditionRequestDto userAdditionRequestDto);
    CrmUserMongoDocument crmUserToCrmUserMongoDocument(CrmUser crmUser);
    CrmUser UserMongoDocumentToCrmUser(CrmUserMongoDocument crmUserMongoDocument);
}
