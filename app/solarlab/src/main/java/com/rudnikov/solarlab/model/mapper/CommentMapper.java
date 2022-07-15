package com.rudnikov.solarlab.model.mapper;

import com.rudnikov.solarlab.entity.CommentEntity;
import com.rudnikov.solarlab.model.CommentModel;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(uses = {},componentModel = "spring")
public interface CommentMapper {

    CommentModel castCommentEntityToModel(CommentEntity commentEntity, @Context CycleAvoidingMappingContext context);
    CommentEntity castCommentModelToEntity(CommentModel commentModel, @Context CycleAvoidingMappingContext context);
    List<CommentModel> castCommentEntityCollectionToModelList(Collection<CommentEntity> commentEntities, @Context CycleAvoidingMappingContext context);
    List<CommentEntity> castCommentModelCollectionToEntityList(Collection<CommentModel> commentModels, @Context CycleAvoidingMappingContext context);

}