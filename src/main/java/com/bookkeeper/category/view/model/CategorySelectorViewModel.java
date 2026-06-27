package com.bookkeeper.category.view.model;

import static com.bookkeeper.common.utils.Constants.CATEGORY_ROOT_NAME;

import com.bookkeeper.category.model.Category;
import com.bookkeeper.category.service.CategoryService;
import com.bookkeeper.category.view.mapper.CategoryTreeViewItemCyclicGraphContext;
import com.bookkeeper.category.view.mapper.CategoryTreeViewItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CategorySelectorViewModel {

  private final CategoryService categoryService;
  private final CategoryTreeViewItemMapper categoryTreeViewItemMapper;

  private final ObjectProperty<Category> selectedCategory = new SimpleObjectProperty<>(this, "selectedCategory");


  public ObjectProperty<Category> selectedCategoryProperty() {
    return selectedCategory;
  }


  public Category getSelectedCategory() {
    return selectedCategory.get();
  }


  public void setSelectedCategory(Category category) {
    selectedCategory.set(category);
  }


  public CategoryTreeNodeItem getCategoryTree() {
    var rootItem = new CategoryTreeNodeItem(new CategoryTreeViewItem(CATEGORY_ROOT_NAME, null));
    var categoryTreeViewItems = getCategoryTreeViewItems();
    var categoryRootTree = categoryTreeViewItems.stream().map(CategoryTreeNodeItem::new).toList();
    rootItem.getChildren().setAll(categoryRootTree);
    return rootItem;
  }


  private List<CategoryTreeViewItem> getCategoryTreeViewItems() {
    var categories = categoryService.getAllCategories();
    var items = mapCategories(categories);
    for (var item : items) {
      if (item.getParent() != null) {
        item.getParent().addChild(item);
      }
    }
    return items.stream().filter(item -> Objects.isNull(item.getParent())).toList();
  }


  private List<CategoryTreeViewItem> mapCategories(List<Category> categories) {
    return categoryTreeViewItemMapper.toCategoryTreeViewItems(categories, new CategoryTreeViewItemCyclicGraphContext());
  }
}
