package com.bookkeeper.ui.model;

import com.bookkeeper.type.TreeNode;

import java.util.Objects;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TreeNodeItem<T extends TreeNode> extends TreeItem<T> {

  private static final Image FOLDER_COLLAPSE_IMAGE = new Image("/images/folder_col.png");
  private static final Image FOLDER_EXPAND_IMAGE = new Image("/images/folder_exp.png");

  public TreeNodeItem(T item) {

    super(item);

    if (!item.isLeaf()) {
      setGraphic(new ImageView(FOLDER_COLLAPSE_IMAGE));
    }

    addEventHandler(TreeItem.branchExpandedEvent(), handler -> {

      TreeNodeItem source = (TreeNodeItem) handler.getSource();

      if(!source.isLeaf() && source.isExpanded()) {

        ImageView iv = (ImageView) source.getGraphic();

        iv.setImage(FOLDER_EXPAND_IMAGE);

      }
    });

    addEventHandler(TreeItem.branchCollapsedEvent(), handler -> {

      TreeNodeItem source = (TreeNodeItem) handler.getSource();

      if(!source.isLeaf() && !source.isExpanded()) {

        ImageView iv = (ImageView) source.getGraphic();

        iv.setImage(FOLDER_COLLAPSE_IMAGE);
      }
    });
  }

  @Override
  public boolean isLeaf() {
    return getValue().isLeaf();
  }

  @Override
  public int hashCode() {
    return getValue() != null ? getValue().hashCode() : super.hashCode();
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof TreeNodeItem)) {
      return false;
    }

    TreeNodeItem other = (TreeNodeItem) obj;

    if (this.getValue() == null || other.getValue() == null) {
      return false;
    }

    return Objects.equals(this.getValue(), other.getValue());
  }
}
