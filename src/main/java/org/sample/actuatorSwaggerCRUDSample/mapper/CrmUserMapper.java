package org.sample.actuatorSwaggerCRUDSample.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserDao;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserExtractionDto;
import org.sample.actuatorSwaggerCRUDSample.model.CrmUserUpdateRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.CrmUserMongoDocument;

import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrmUserMapper {
    CrmUserDao crmUserAdditionRequestDtoToCrmUserDao(CrmUserAdditionRequestDto crmUserAdditionRequestDto);
    CrmUserMongoDocument crmUserDaoToCrmUserMongoDocument(CrmUserDao crmUserDao);
    CrmUserDao crmUserMongoDocumentToCrmUserDao(CrmUserMongoDocument crmUserMongoDocument);
    List<CrmUserDao> crmUserMongoDocumentListToCrmUserDaoList(List<CrmUserMongoDocument> crmUserMongoDocument);
    CrmUserDao updateCrmUserDaoByCrmUserUpdateRequestDto(@MappingTarget CrmUserDao deliveryAddress, CrmUserUpdateRequestDto crmUserUpdateRequestDto);
}
