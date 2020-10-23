package org.sample.actuatorSwaggerCRUDSample.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CommonMapper {
    CommonResponseDTO cloneCommonResponseDTO(CommonResponseDTO commonResponseDTO);
}
