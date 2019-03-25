package com.bookkeeper.domain.category;

import static com.bookkeeper.app.AppConstants.DEFAULT_CATEGORY_CREDIT_NAME;
import static com.bookkeeper.app.AppConstants.DEFAULT_CATEGORY_DEBIT_NAME;
import static com.bookkeeper.app.AppConstants.DEFAULT_CATEGORY_ROOT_NAME;
import static com.bookkeeper.type.EntryType.CREDIT;
import static com.bookkeeper.type.EntryType.DEBIT;
import static com.bookkeeper.type.TreeNode.buildTreeRoot;
import static java.util.Collections.singletonList;

import com.bookkeeper.exceptions.BookkeeperException;
import com.bookkeeper.domain.entry.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  public Category getRootCategory() {
    var categories = categoryRepository.findAll();
    return buildTreeRoot(categories).orElseGet(this::createDefaultCategory);
  }

  @Transactional
  protected Category createDefaultCategory() {

    var root = CategoryGroup.groupBuilder()
        .name(DEFAULT_CATEGORY_ROOT_NAME)
        .build();

    var expenses = CategoryGroup.groupBuilder()
        .name(DEFAULT_CATEGORY_CREDIT_NAME)
        .type(CREDIT)
        .parent(root)
        .build();

    var income = CategoryGroup.groupBuilder()
        .name(DEFAULT_CATEGORY_DEBIT_NAME)
        .type(DEBIT)
        .parent(root)
        .build();

    root.addChild(expenses);
    root.addChild(income);

    save(root);
    save(expenses);
    save(income);

    return root;
  }
}
