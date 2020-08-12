package org.sample.actuatorSwaggerCRUDSample.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.CrmClient;
import org.sample.actuatorSwaggerCRUDSample.model.CrmClientAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.CrmClientUpdateRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.document.CrmClientMongoDocument;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrmClientMapper {
    CrmClient crmUserAdditionRequestDtoToCrmUser(CrmClientAdditionRequestDto crmClientAdditionRequestDto);
    CrmClientMongoDocument crmUserToCrmUserMongoDocument(CrmClient crmClient);
    CrmClient crmUserMongoDocumentToCrmUser(CrmClientMongoDocument crmClientMongoDocument);
    List<CrmClient> crmUserMongoDocumentListToCrmUserList(List<CrmClientMongoDocument> crmClientMongoDocument);
    CrmClient updateCrmUserByCrmUserUpdateRequestDto(@MappingTarget CrmClient deliveryAddress, CrmClientUpdateRequestDto crmClientUpdateRequestDto);
}
