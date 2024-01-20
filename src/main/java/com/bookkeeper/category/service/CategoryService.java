package com.bookkeeper.category.service;

import static java.util.Collections.singletonList;

import com.bookkeeper.category.entity.Category;
import com.bookkeeper.category.repo.CategoryRepository;
import com.bookkeeper.entry.repo.EntryRepository;
import com.bookkeeper.exceptions.ApplicationException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final EntryRepository entryRepository;

  public void save(Category category) {
    categoryRepository.save(category);
  }

  public void delete(Category category) {

    boolean isFolder = !category.isLeaf();
    boolean isNotEmpty = !category.getChildren().isEmpty();

    if (isFolder && isNotEmpty) {
      throw new ApplicationException("Can't delete folder. Delete its content first.");
    }

    if (!isFolder) {
      boolean hasEntries = !entryRepository.findByCategoryIn(singletonList(category)).isEmpty();

      if (hasEntries) {
        throw new ApplicationException("Can't delete category. Delete its entries first.");
      }
    }

    categoryRepository.delete(category);
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }
}
