package com.bookkeeper.domain.category;

import static java.util.Collections.singletonList;

import com.bookkeeper.exceptions.ApplicationException;
import com.bookkeeper.domain.entry.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private EntryRepository entryRepository;

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
