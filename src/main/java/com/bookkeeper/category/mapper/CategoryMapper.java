package com.bookkeeper.category.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.bookkeeper.category.model.Category;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = SPRING)
public interface CategoryMapper {

  @Mapping(target = "parent", ignore = true)
  com.bookkeeper.category.jpa.Category toEntity(Category category);

  List<com.bookkeeper.category.jpa.Category> toEntities(List<Category> categories);

  Category toModel(com.bookkeeper.category.jpa.Category categoryEntity, @Context CategoryCyclicGraphContext context);

  List<Category> toModel(List<com.bookkeeper.category.jpa.Category> categoryEntities,
      @Context CategoryCyclicGraphContext context);
}
