package com.example.demo.core_service.mapper;

import com.example.demo.core_service.dto.CreateSneakerRequest;
import com.example.demo.core_service.entity.Sneaker;
import com.example.demo.core_service.dto.UpdateSneakerRequest;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SneakerMapper {
    Sneaker toEntity(CreateSneakerRequest createSneakerRequest);

    CreateSneakerRequest toCreateSneakerDto(Sneaker sneaker);

    Sneaker toEntity(UpdateSneakerRequest updateSneakerRequest);

    UpdateSneakerRequest toUpdateSneakerRequest(Sneaker sneaker);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sneaker partialUpdate(UpdateSneakerRequest sneakerRequest, @MappingTarget Sneaker sneaker);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Sneaker updateEntity(Sneaker sneaker, UpdateSneakerRequest updateSneakerRequest);
}
