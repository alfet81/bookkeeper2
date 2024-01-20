package com.bookkeeper.account.ui;

import com.bookkeeper.account.entity.Account;
import com.bookkeeper.ui.common.TreeItemRecord;
import com.bookkeeper.ui.common.TreeViewDialog;

import javafx.scene.control.TreeItem;

public class AccountSelectorDialog extends TreeViewDialog<TreeItemRecord<Account>> {

  public AccountSelectorDialog(String title, TreeItem<TreeItemRecord<Account>> treeRoot) {
    super(title, treeRoot);
  }
}
