package com.bookkeeper.category.view.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.bookkeeper.category.model.Category;
import com.bookkeeper.category.view.model.CategoryTreeViewItem;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = SPRING)
public interface CategoryTreeViewItemMapper {

  @Mapping(target = "value", source = ".")
  CategoryTreeViewItem toCategoryTreeViewItem(Category category,
      @Context CategoryTreeViewItemCyclicGraphContext context);

  List<CategoryTreeViewItem> toCategoryTreeViewItems(List<Category> categories,
      @Context CategoryTreeViewItemCyclicGraphContext context);
}
