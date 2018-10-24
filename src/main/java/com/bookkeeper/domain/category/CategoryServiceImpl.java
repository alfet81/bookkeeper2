package com.bookkeeper.domain.category;

import static java.util.Collections.singletonList;

import com.bookkeeper.core.exceptions.BookkeeperException;
import com.bookkeeper.domain.entry.EntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private EntryRepository entryRepository;

  @Override
  @Transactional
  public void save(Category category) {
    categoryRepository.save(category);
  }

  @Override
  @Transactional
  public void delete(Category category) {

    boolean isFolder = !category.isLeaf();
    boolean isNotEmpty = !category.getChildren().isEmpty();

    if (isFolder && isNotEmpty) {
      throw new BookkeeperException("Can't delete folder. Delete its content first.");
    }

    if (!isFolder) {
      boolean hasEntries = !entryRepository.findByCategoryIn(singletonList(category)).isEmpty();

      if (hasEntries) {
        throw new BookkeeperException("Can't delete category. Delete its entries first.");
      }
    }

    categoryRepository.delete(category);
  }

  @Override
  public Optional<Category> getRootCategory() {
    return categoryRepository.findAll().parallelStream()
        .filter(category -> category.getParent() == null)
        .findFirst();
  }
}
