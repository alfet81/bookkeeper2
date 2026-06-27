package com.bookkeeper.common.gui;

import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;
import static javafx.scene.control.SelectionMode.SINGLE;

import com.bookkeeper.common.model.types.TreeNode;

import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TreeViewDialog<N extends TreeNode<?, N>> extends Dialog<N> {

  private final TreeView<N> treeView;


  public TreeViewDialog(String title, TreeItem<N> treeRoot) {
    setTitle(title);
    treeView = new TreeView<>(treeRoot);
    treeView.setShowRoot(true);
    treeView.getSelectionModel().setSelectionMode(SINGLE);
    getDialogPane().setContent(buildContentPane());
    getDialogPane().getButtonTypes().addAll(CANCEL, FINISH);
    setResultConverter();
    expandTreeView(treeRoot);
  }


  public TreeViewDialog(String title, TreeItem<N> treeRoot, boolean showRoot) {
    this(title, treeRoot);
    treeView.setShowRoot(showRoot);
  }


  public void setSelectedItem(TreeItem<N> item) {
    treeView.getSelectionModel().select(item);
  }


  public void setSelectedItem(String itemName) {
    setSelectedItem(treeView.getRoot(), itemName);
  }


  private void setSelectedItem(TreeItem<N> item, String itemName) {
    if (item.isLeaf() && item.getValue() != null && item.getValue().toString().equals(itemName)) {
      setSelectedItem(item);
    } else {
      item.getChildren().forEach(child -> setSelectedItem(child, itemName));
    }
  }


  private Pane buildContentPane() {
    return new AnchorPane(new ScrollPane(treeView));
  }


  private void setResultConverter() {
    setResultConverter(buttonType -> {
      if (buttonType == FINISH) {
        return treeView.getSelectionModel().getSelectedItem().getValue();
      }
      return null;
    });
  }


  public void expandTreeView(TreeItem<N> item) {
    if (item != null && !item.isLeaf()) {
      item.setExpanded(true);
      item.getChildren().forEach(this::expandTreeView);
    }
  }
}
