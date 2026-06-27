package com.bookkeeper.common.gui;

import static com.bookkeeper.utils.ImageUtils.createImage;

import com.bookkeeper.common.model.types.TreeNode;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class TreeNodeItem<N extends TreeNode<?, N>> extends TreeItem<N> {

  private static final Image FOLDER_COLLAPSE_IMAGE = createImage("/images/folder_col.png");
  private static final Image FOLDER_EXPAND_IMAGE = createImage("/images/folder_exp.png");

  private boolean firstTimeGettingChildren = true;


  public TreeNodeItem(N treeNode) {
    super(treeNode);

    if (!treeNode.isLeaf()) {
      setGraphic(new ImageView(FOLDER_COLLAPSE_IMAGE));
    }

    addEventHandler(TreeItem.branchExpandedEvent(), handler -> {
      var source = handler.getSource();
      if (!source.isLeaf() && source.isExpanded()) {
        var imageView = (ImageView) source.getGraphic();
        if (imageView != null) {
          imageView.setImage(FOLDER_EXPAND_IMAGE);
        }
      }
    });

    addEventHandler(TreeItem.branchCollapsedEvent(), handler -> {
      var source = handler.getSource();
      if (!source.isLeaf() && !source.isExpanded()) {
        var imageView = (ImageView) source.getGraphic();
        if (imageView != null) {
          imageView.setImage(FOLDER_COLLAPSE_IMAGE);
        }
      }
    });
  }


  @Override
  public boolean isLeaf() {
    return getChildren().isEmpty();
  }


  @Override
  public ObservableList<TreeItem<N>> getChildren() {
    if (firstTimeGettingChildren) {
      firstTimeGettingChildren = false;
      super.getChildren().setAll(buildChildren(this));
    }
    return super.getChildren();
  }


  private ObservableList<TreeItem<N>> buildChildren(TreeItem<N> parentTreeItem) {
    N value = parentTreeItem.getValue();
    if (value != null && !value.isLeaf()) {
      var children = value.getChildren();
      if (!children.isEmpty()) {
        ObservableList<TreeItem<N>> result = FXCollections.observableArrayList();
        for (N child : children) {
          result.add(new TreeNodeItem<>(child));
        }
        return result;
      }
    }
    return FXCollections.emptyObservableList();
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

