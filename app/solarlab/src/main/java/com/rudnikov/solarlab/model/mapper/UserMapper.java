package com.rudnikov.solarlab.model.mapper;

import com.rudnikov.solarlab.entity.UserEntity;
import com.rudnikov.solarlab.model.UserModel;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {}, componentModel = "spring")
public interface UserMapper {

    UserModel castUserEntityToModel(UserEntity userEntity, @Context CycleAvoidingMappingContext context);
    UserEntity castUserModelToEntity(UserModel userModel, @Context CycleAvoidingMappingContext context);
    List<UserModel> castUserEntityCollectionToModelList(Collection<UserEntity> userEntities, @Context CycleAvoidingMappingContext context);
    List<UserEntity> castUserModelCollectionToEntityList(Collection<UserModel> userModels, @Context CycleAvoidingMappingContext context);

}