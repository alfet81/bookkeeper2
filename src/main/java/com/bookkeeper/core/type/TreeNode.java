package com.bookkeeper.core.type;

import java.util.ArrayList;
import java.util.List;

public interface TreeNode<T extends TreeNode<T>> {

  T getParent();

  void setParent(T parent);

  List<T> getChildren();

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
