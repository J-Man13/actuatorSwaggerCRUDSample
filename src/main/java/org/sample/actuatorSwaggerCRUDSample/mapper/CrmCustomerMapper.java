package org.sample.actuatorSwaggerCRUDSample.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.CrmCustomer;
import org.sample.actuatorSwaggerCRUDSample.model.CrmCustomerAdditionRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.CrmCustomerUpdateRequestDto;
import org.sample.actuatorSwaggerCRUDSample.model.mongo.crm.document.CrmCustomerMongoDocument;

import java.util.List;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CrmCustomerMapper {
    CrmCustomer crmCustomerAdditionRequestDtoToCrmCustomer(CrmCustomerAdditionRequestDto crmCustomerAdditionRequestDto);
    CrmCustomerMongoDocument crmCustomerToCrmCustomerMongoDocument(CrmCustomer crmCustomer);
    CrmCustomer crmCustomerMongoDocumentToCrmCustomer(CrmCustomerMongoDocument crmCustomerMongoDocument);
    List<CrmCustomer> crmCustomerMongoDocumentListToCrmCustomerList(List<CrmCustomerMongoDocument> crmCustomerMongoDocument);
    CrmCustomer crmCustomerUpdateRequestDtoToCrmCustomer(CrmCustomerUpdateRequestDto crmCustomerUpdateRequestDto);
    CrmCustomerMongoDocument updateCrmCustomerMongoDocumentByCrmCustomer(@MappingTarget CrmCustomerMongoDocument crmCustomerMongoDocument, CrmCustomer crmCustomer);
}
