package com.bookkeeper.category.view.model;

import com.bookkeeper.category.model.Category;
import com.bookkeeper.common.model.types.TreeNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CategoryTreeViewItem implements TreeNode<Category, CategoryTreeViewItem> {

  private final List<CategoryTreeViewItem> children = new ArrayList<>();

  @Getter
  private final String name;
  private final Category value;

  private CategoryTreeViewItem parent;


  @Override
  public String toString() {
    return name;
  }


  @Override
  public Category getValue() {
    return value;
  }


  @Override
  public CategoryTreeViewItem getParent() {
    return parent;
  }


  @Override
  public void setParent(CategoryTreeViewItem parent) {
    this.parent = parent;
  }


  @Override
  public Collection<CategoryTreeViewItem> getChildren() {
    return children;
  }


  @Override
  public boolean isLeaf() {
    return children.isEmpty();
  }


  @Override
  public boolean equals(Object otherTreeNode) {
    if (this == otherTreeNode) {
      return true;
    }
    if (!(otherTreeNode instanceof CategoryTreeViewItem otherTreeViewItem)) {
      return false;
    }
    return Objects.equals(value, otherTreeViewItem.value);
  }


  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : Objects.hashCode(name);
  }
}
