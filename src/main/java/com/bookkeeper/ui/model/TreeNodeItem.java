package com.bookkeeper.ui.model;

import com.bookkeeper.type.TreeNode;

import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TreeNodeItem<T extends TreeNode> extends TreeItem<T> {

  static Image folderCollapseImage = new Image("/images/folder_col.png");
  static Image folderExpandImage = new Image("/images/folder_exp.png");

  private T item;

  public TreeNodeItem(T item) {
    super(item);
    this.item = item;

    if (!item.isLeaf()) {
      setGraphic(new ImageView(folderCollapseImage));
    }

    addEventHandler(TreeItem.branchExpandedEvent(), handler -> {

      TreeNodeItem source = (TreeNodeItem) handler.getSource();

      if(!source.isLeaf() && source.isExpanded()) {

        ImageView iv = (ImageView) source.getGraphic();

        iv.setImage(folderExpandImage);

      }
    });

    addEventHandler(TreeItem.branchCollapsedEvent(), handler -> {

      TreeNodeItem source = (TreeNodeItem) handler.getSource();

      if(!source.isLeaf() && !source.isExpanded()) {

        ImageView iv = (ImageView) source.getGraphic();

        iv.setImage(folderCollapseImage);
      }
    });
  }

  @Override
  public boolean isLeaf() {
    return item.isLeaf();
  }
}
