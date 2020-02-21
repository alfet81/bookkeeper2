package com.bookkeeper.ui.controls;

import com.bookkeeper.exceptions.BookkeeperException;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;

public class TreeViewComboBox<T> extends HBox {

  @FXML
  private TextField textField;

  private Popup popup = new Popup();

  public TreeViewComboBox() {
    initFxml();
    initPopup();
  }

  private void initFxml() {
    var fxmlLoader = new FXMLLoader(getFxmlUrl());
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (Exception e) {
      throw new BookkeeperException(e);
    }
  }

  private void initPopup() {
    var scrollPane = new ScrollPane(new TreeView<T>());
    popup.getContent().add(scrollPane);
  }

  @FXML
  protected void showPopup() {

//let's get coordinates of application Window
    double windowX = textField.getScene().getWindow().getX();
    double windowY = textField.getScene().getWindow().getY();
    double sceneX = textField.getScene().getX();
    double sceneY = textField.getScene().getY();

//let's convert coordinates of TextField into coordinates relative for Scene
    Bounds localBounds = textField.localToScene(textField.getBoundsInLocal());
    popup.setX(windowX + sceneX + localBounds.getMinX());
    popup.setY(windowY + sceneY + localBounds.getMaxY());

//+10 adds a little space horizontally between textField and Popup
    popup.show(this, windowX + sceneX + localBounds.getMinX(), windowY + sceneY + localBounds.getMinY() + localBounds.getHeight());
   // popup.show(this, x + textField.getLayoutX(), y + textField.getLayoutY());
  }

  private URL getFxmlUrl() {
    return getClass().getResource("/com/bookkeeper/ui/fxml/TreeViewComboBox.fxml");
  }
}
