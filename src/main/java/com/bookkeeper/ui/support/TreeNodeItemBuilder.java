package com.bookkeeper.ui.support;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

import com.bookkeeper.common.entity.TreeNode;
import com.bookkeeper.ui.model.TreeNodeItem;

import java.util.Collection;
import java.util.List;
import javafx.scene.control.TreeItem;

public class TreeNodeItemBuilder<T extends TreeNode<T>> {

  private T rootNode;

  public TreeNodeItemBuilder(T rootNode) {
    this.rootNode = requireNonNull(rootNode);
  }

  public TreeItem<T> build() {
    return traverse(rootNode);
  }

  private static <T extends TreeNode<T>> TreeItem<T> traverse(T node) {

    TreeItem<T> treeItem = new TreeNodeItem<>(node);

    Collection<T> children = (node.getChildren() != null ? node.getChildren() : emptyList());

    List<TreeItem<T>> subItems = children.stream().map(TreeNodeItemBuilder::traverse).collect(toList());

    treeItem.getChildren().addAll(subItems);

    return treeItem;
  }
}
