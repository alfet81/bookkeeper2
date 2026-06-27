package com.bookkeeper.category.view.gui;

import com.bookkeeper.category.view.model.CategoryTreeNodeItem;
import com.bookkeeper.category.view.model.CategoryTreeViewItem;
import com.bookkeeper.common.gui.TreeViewDialog;

public class CategorySelectorDialog extends TreeViewDialog<CategoryTreeViewItem> {

  public CategorySelectorDialog(String title, CategoryTreeNodeItem treeRoot) {
    super(title, treeRoot);
  }
}
