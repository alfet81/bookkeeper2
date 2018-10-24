package com.bookkeeper.domain.category;

import com.bookkeeper.domain.category.Category;

import java.util.Optional;

public interface CategoryService {
  Optional<Category> getRootCategory();
  void save(Category category);
  void delete(Category category);
}
