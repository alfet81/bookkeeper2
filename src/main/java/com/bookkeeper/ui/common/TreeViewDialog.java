package com.bookkeeper.ui.common;

import static javafx.scene.control.ButtonType.CANCEL;
import static javafx.scene.control.ButtonType.FINISH;

import com.bookkeeper.type.TreeNode;
import com.bookkeeper.ui.model.TreeNodeItem;

import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TreeViewDialog<T extends TreeNode<T>> extends Dialog<T> {

  private TreeView<T> treeView;

  public TreeViewDialog(String title, TreeItem<T> treeRoot) {

    setTitle(title);

    treeView = new TreeView<>(treeRoot);

    getDialogPane().setContent(buildContentPane());

    getDialogPane().getButtonTypes().addAll(CANCEL, FINISH);

    setResultConverter();
  }

  public void setSelectedItem(T item) {
    treeView.getSelectionModel().select(new TreeNodeItem<>(item));
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
}
