package com.bookkeeper.ui.common;

import com.bookkeeper.common.entity.TreeNode;

import java.util.Collection;


public record TreeItemRecord<T extends TreeNode<T>>(T value, String name) implements TreeNode<T> {

  @Override
  public String toString() {
    return name;
  }

  @Override
  public T getParent() {
    return value.getParent();
  }

  @Override
  public void setParent(T parent) {
    //NO-OP
  }

  @Override
  public Collection<T> getChildren() {
    return value.getChildren();
  }

  @Override
  public boolean isLeaf() {
    return value.isLeaf();
  }
}
