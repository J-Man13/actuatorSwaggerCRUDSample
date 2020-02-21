package org.sample.actuatorSwaggerCRUDSample.model;

public class CrmUserExtractionResponceDto {
    private CrmUserExtractionDto crmUserExtractionDto;

    public CrmUserExtractionResponceDto(CrmUserExtractionDto crmUserExtractionDto) {
        this.crmUserExtractionDto = crmUserExtractionDto;
    }

    public CrmUserExtractionDto getCrmUserExtractionDto() {
        return crmUserExtractionDto;
    }

    public void setCrmUserExtractionDto(CrmUserExtractionDto crmUserExtractionDto) {
        this.crmUserExtractionDto = crmUserExtractionDto;
    }
}
