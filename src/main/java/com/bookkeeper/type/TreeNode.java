package com.bookkeeper.type;

import static com.bookkeeper.utils.MiscUtils.asOptional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TreeNode<T extends TreeNode<T>> {

  T getParent();

  void setParent(T parent);

  Collection<T> getChildren();

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

  static <T extends TreeNode<T>> Optional<T> buildTreeRoot(List<T> items) {

    T root = null;

    for (var item : items) {
      if (item.getParent() != null) {
        item.getParent().addChild(item);
      } else {
        root = item;
      }
    }

    return asOptional(root);
  }
}
