package com.bookkeeper.common.model.types;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Interface to represent a tree node.
 *
 * @param <T> Type of value stored in a tree node.
 * @param <N> Self-referential type boundary that captures the implementing type.
 */
public interface TreeNode<T, N extends TreeNode<T, N>> {

  T getValue();

  N getParent();

  void setParent(N parent);

  default Collection<N> getChildren() {
    return emptyList();
  }

  default void addChild(N child) {
    child.setParent((N) this);
    getChildren().add(child);
  }

  default void removeChild(N child) {
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

  default List<N> collectLeafChildren() {
    var result = new ArrayList<N>();
    if (isLeaf()) {
      result.add((N) this);
    } else {
      getChildren().forEach(child -> result.addAll(child.collectLeafChildren()));
    }
    return result;
  }
}
