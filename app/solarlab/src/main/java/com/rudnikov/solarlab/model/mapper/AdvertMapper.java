package com.rudnikov.solarlab.model.mapper;

import com.rudnikov.solarlab.entity.AdvertEntity;
import com.rudnikov.solarlab.model.AdvertModel;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {},componentModel = "spring")
public interface AdvertMapper {

    AdvertModel castAdvertEntityToModel(AdvertEntity advertEntity, @Context CycleAvoidingMappingContext context);
    AdvertEntity castAdvertModelToEntity(AdvertModel advertModel, @Context CycleAvoidingMappingContext context);
    List<AdvertModel> castAdvertEntityCollectionToModelList(Collection<AdvertEntity> advertEntities, @Context CycleAvoidingMappingContext context);
    List<AdvertEntity> castAdvertModelCollectionToEntityList(Collection<AdvertModel> advertModels, @Context CycleAvoidingMappingContext context);

}