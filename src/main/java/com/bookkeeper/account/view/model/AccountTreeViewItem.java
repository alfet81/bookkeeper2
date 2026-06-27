package com.bookkeeper.account.view.model;

import com.bookkeeper.account.model.Account;
import com.bookkeeper.common.model.types.TreeNode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class AccountTreeViewItem implements TreeNode<Account, AccountTreeViewItem> {

  private final List<AccountTreeViewItem> children = new ArrayList<>();

  @Getter
  private final String name;
  private final Account value;

  private AccountTreeViewItem parent;


  @Override
  public String toString() {
    return name;
  }


  @Override
  public Account getValue() {
    return value;
  }


  @Override
  public AccountTreeViewItem getParent() {
    return parent;
  }


  @Override
  public void setParent(AccountTreeViewItem parent) {
    this.parent = parent;
  }


  @Override
  public Collection<AccountTreeViewItem> getChildren() {
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
    if (!(otherTreeNode instanceof AccountTreeViewItem otherTreeViewItem)) {
      return false;
    }
    return Objects.equals(value, otherTreeViewItem.value);
  }


  @Override
  public int hashCode() {
    return value != null ? value.hashCode() : Objects.hashCode(name);
  }
}
