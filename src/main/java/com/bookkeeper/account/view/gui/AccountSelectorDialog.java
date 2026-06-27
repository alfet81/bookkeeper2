package com.bookkeeper.account.view.gui;

import com.bookkeeper.account.view.model.AccountTreeNodeItem;
import com.bookkeeper.account.view.model.AccountTreeViewItem;
import com.bookkeeper.common.gui.TreeViewDialog;

public class AccountSelectorDialog extends TreeViewDialog<AccountTreeViewItem> {

  public AccountSelectorDialog(String title, AccountTreeNodeItem treeRoot) {
    super(title, treeRoot);
  }
}
