package com.bookkeeper.common.entity;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface TreeNode<T extends TreeNode<T>> {

  static <T extends TreeNode<T>> void buildTreeRoot(List<T> items, T root) {
    for (var item : items) {
      if (item.getParent() != null) {
        item.getParent().addChild(item);
      } else {
        root.addChild(item);
      }
    }
  }

  T getParent();

  void setParent(T parent);

  default Collection<T> getChildren() {
    return emptyList();
  }

  default void addChild(T child) {

    child.setParent((T) this);

    getChildren().add(child);
  }

  default void removeChild(T child) {

    getChildren().remove(child);

    child.setParent(null);
  }

  default boolean isLeaf() {
    return true;
  }

  default void removeAllChildren() {

    getChildren().forEach(c -> c.setParent(null));

    getChildren().clear();
  }

  default List<T> collectLeafChildren() {

    var results = new ArrayList<T>();

    if (isLeaf()) {
      results.add((T) this);
    } else {
      getChildren().forEach(child -> results.addAll(child.collectLeafChildren()));
    }

    return results;
  }
}
