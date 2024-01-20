package com.bookkeeper.ui.model;

import com.bookkeeper.common.entity.TreeNode;

import java.util.Objects;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TreeNodeItem<T extends TreeNode<?>> extends TreeItem<T> {

  private static final Image FOLDER_COLLAPSE_IMAGE = new Image("/images/folder_col.png");
  private static final Image FOLDER_EXPAND_IMAGE = new Image("/images/folder_exp.png");

  public TreeNodeItem(T item) {

    super(item);

    if (!item.isLeaf()) {
      setGraphic(new ImageView(FOLDER_COLLAPSE_IMAGE));
    }

    addEventHandler(TreeItem.branchExpandedEvent(), handler -> {

      var source = (TreeItem<?>) handler.getSource();

      if(!source.isLeaf() && source.isExpanded()) {

        var imageView = (ImageView) source.getGraphic();

        imageView.setImage(FOLDER_EXPAND_IMAGE);
      }
    });

    addEventHandler(TreeItem.branchCollapsedEvent(), handler -> {

      var source = (TreeItem<?>) handler.getSource();

      if(!source.isLeaf() && !source.isExpanded()) {

        var imageView = (ImageView) source.getGraphic();

        imageView.setImage(FOLDER_COLLAPSE_IMAGE);
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

    if (!(obj instanceof TreeNodeItem<?> other)) {
      return false;
    }

    if (this.getValue() == null || other.getValue() == null) {
      return false;
    }

    return Objects.equals(this.getValue(), other.getValue());
  }
}
