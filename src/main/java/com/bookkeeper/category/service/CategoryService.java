package com.bookkeeper.category.service;

import com.bookkeeper.category.jpa.CategoryRepository;
import com.bookkeeper.category.mapper.CategoryCyclicGraphContext;
import com.bookkeeper.category.mapper.CategoryMapper;
import com.bookkeeper.category.model.Category;
import com.bookkeeper.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;


  public List<Category> getAllCategories() {
    var categoryEntities = categoryRepository.findAll();
    return categoryMapper.toModel(categoryEntities, new CategoryCyclicGraphContext());
  }


  public Category getCategory(Long id) {
    var entity = findCategory(id);
    return categoryMapper.toModel(entity, new CategoryCyclicGraphContext());
  }


  private com.bookkeeper.category.jpa.Category findCategory(Long id) {
    return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found for id: " + id));
  }


  public Category save(Category category) {
    com.bookkeeper.category.jpa.Category categoryEntity;
    com.bookkeeper.category.jpa.Category parentCategoryEntity;

    if (category.getId() != null) {
      categoryEntity = findCategory(category.getId());
    } else {
      categoryEntity = categoryMapper.toEntity(category);
    }

    if (category.getParent() != null) {
      var parentCategory = category.getParent();
      if (parentCategory.getId() != null) {
        parentCategoryEntity = findCategory(parentCategory.getId());
      } else {
        parentCategoryEntity = categoryMapper.toEntity(parentCategory);
        parentCategoryEntity = categoryRepository.save(parentCategoryEntity);
      }
      categoryEntity.setParent(parentCategoryEntity);
    }
    var savedCategoryEntity = categoryRepository.save(categoryEntity);
    return categoryMapper.toModel(savedCategoryEntity, new CategoryCyclicGraphContext());
  }


  public void delete(Category category) {
    var entity = findCategory(category.getId());
    categoryRepository.delete(entity);
  }
}
